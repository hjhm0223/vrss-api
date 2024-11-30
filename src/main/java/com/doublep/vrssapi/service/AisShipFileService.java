package com.doublep.vrssapi.service;

import com.doublep.vrssapi.model.AisFileCollectHist;
import com.doublep.vrssapi.model.AisShipData;
import com.doublep.vrssapi.model.request.CommonRequest;
import com.doublep.vrssapi.model.Ship;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.doublep.vrssapi.model.AisShipData.buildAisShipData;

@Slf4j
@Service
@RequiredArgsConstructor
public class AisShipFileService {

    @Value("${file.path}")
    private String filePath;
    private final AisFileCollectHistService aisFileCollectHistService;
    private final AisShipDataLastService aisShipDataLastService;
    private final AisShipDataHistService aisShipDataHistService;
    private final ShipService shipService;
    private final Cache aisFileCollectHistCache;

    @PostConstruct
    public void warmAisFileCollectHistCache() {
        var total = aisFileCollectHistService.countNotFailedAisFileCollectHistList();
        var offset = 0;
        log.debug("[ErrorCorrection] initializing {} error-correction cache", total);

        if (total > 0) {
            while (offset < total) {
                var request = CommonRequest.builder()
                        .limit(1000)
                        .offset(offset)
                        .build();
                var aisFileCollectHistList = aisFileCollectHistService.getNotFailedAisFileCollectHistList(request);
                aisFileCollectHistList.forEach(aisFileCollectHist -> aisFileCollectHistCache.put(aisFileCollectHist.getFileName(), aisFileCollectHist));
                offset += aisFileCollectHistList.size();
            }
        }
    }

    /**
     * 폴더에서 새로운 파일 확인 및 처리
     */
    public void checkForNewFilesAndProcess() {
        var folderPath = Paths.get(filePath);
        var fileName = "";
        var aisFileCollectHist = new AisFileCollectHist();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath, "*.csv")) {
            for (Path file : directoryStream) {
                fileName = String.valueOf(file.getFileName());

                if (aisFileCollectHistCache.get(fileName) == null) {
                    aisFileCollectHist.setFileName(fileName);
                    var startTimestamp = LocalDateTime.now();
                    aisFileCollectHist.setStartTimestamp(startTimestamp);
                    aisFileCollectHist.setStatus("start");
                    aisFileCollectHistService.insertAisFileCollectHistList(aisFileCollectHist);
                    aisFileCollectHistCache.put(fileName, aisFileCollectHist);
                    log.info("[FileReadService] Processing new AIS file: " + file);

                    var totalCount = parseAndSaveAisShipData(file);

                    log.info("total count ::: {}", totalCount);
                    var endTimestamp = LocalDateTime.now();
                    aisFileCollectHist.setTotalCount(totalCount);
                    aisFileCollectHist.setStatus("end");
                    aisFileCollectHist.setEndTimestamp(endTimestamp);
                    aisFileCollectHist.setDuration(Duration.between(startTimestamp, endTimestamp).getSeconds());
                    aisFileCollectHistService.updateAisFileCollectHist(aisFileCollectHist);
                    aisFileCollectHistCache.put(fileName, aisFileCollectHist);
                }
            }
        } catch (Exception e) {
            aisFileCollectHist.setStatus("failed");
            aisFileCollectHistService.updateAisFileCollectHist(aisFileCollectHist);
            aisFileCollectHistCache.evict(fileName);
            log.error("[FileReadService] Failed processing new AIS file.");
        }
    }

    /**
     * CSV 파일을 파싱하고 DB 저장
     *
     * @param filePath CSV 파일 경로
     */
    private int parseAndSaveAisShipData(Path filePath) {
        var dataList = new ArrayList<AisShipData>();
        var totalCount = 0;

        try (var reader = new FileReader(filePath.toFile());
             var csvParser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {

            for (var aisShipDataRecord : csvParser) {
                dataList.add(buildAisShipData(aisShipDataRecord));
                if (dataList.size() >= 100) {
                    totalCount += saveAisShipData(dataList);
                    dataList.clear();
                }
            }

            if (!dataList.isEmpty()) {
                totalCount += saveAisShipData(dataList);
            }
        } catch (Exception e) {
            log.error("[FileReadService] Failed parsing AIS file. {}", e.getLocalizedMessage());
        }

        return totalCount;
    }

    private int saveAisShipData(List<AisShipData> aisShipDataList) {
        // Upsert AisShipDataLast
        var uniqueAisShipData = getUniqueAisShipData(aisShipDataList);
        try {
            aisShipDataLastService.batchUpsertAisShipDataLast(uniqueAisShipData);
        } catch (Exception e) {
            aisShipDataLastService.loopUpsertAisShipDataLast(uniqueAisShipData);
        }
        // Insert AisShipDataHist
        try {
            aisShipDataHistService.batchInsertAisShipDataHist(aisShipDataList);
        } catch (Exception e) {
            aisShipDataHistService.loopInsertAisShipDataHist(aisShipDataList);
        }
        // Insert Ship
        var uniqueShipList = getUniqueShipList(aisShipDataList);
        try {
            shipService.batchUpsertShip(uniqueShipList);
        } catch (Exception e) {
            shipService.loopUpsertShip(uniqueShipList);
        }
        return aisShipDataList.size();
    }

    private List<AisShipData> getUniqueAisShipData(List<AisShipData> aisShipDataList) {
        // 중복 제거 (기준: mmsiProcess)
        var uniqueMmsiProcessShipMap = aisShipDataList.stream()
                .collect(Collectors.toMap(
                        AisShipData::getMmsiProcess,
                        ship -> ship,
                        (existing, replacement) -> replacement
                ));
        return new ArrayList<>(uniqueMmsiProcessShipMap.values());
    }

    private List<Ship> getUniqueShipList(List<AisShipData> aisShipDataList) {
        // 중복 제거 (기준: mmsi)
        var uniqueMmsiShipMap = aisShipDataList.stream()
                .map(AisShipData::buildShipList)
                .collect(Collectors.toMap(
                        Ship::getMmsi,
                        ship -> ship,
                        (existing, replacement) -> replacement
                ));
        return new ArrayList<>(uniqueMmsiShipMap.values());
    }
}