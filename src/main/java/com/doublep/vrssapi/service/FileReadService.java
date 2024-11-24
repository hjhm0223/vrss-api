package com.doublep.vrssapi.service;

import com.doublep.vrssapi.model.AisFileCollectHist;
import com.doublep.vrssapi.model.AisShipData;
import com.doublep.vrssapi.model.CommonRequest;
import com.doublep.vrssapi.model.Ship;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileReadService {

    @Value("${file.path}")
    private String filePath;
    private final SqlSessionTemplate batchSqlSessionTemplate;
    private final AisFileCollectHistService aisFileCollectHistService;
    private final Cache aisFileCollectHistCache;

    @PostConstruct
    public void warmAisFileCollectHistCache() {
        var total = aisFileCollectHistService.countAisFileCollectHistList();
        var offset = 0;
        log.debug("[ErrorCorrection] initializing {} error-correction cache", total);

        if (total > 0) {
            while (offset < total) {
                var request = CommonRequest.builder()
                        .limit(1000)
                        .offset(offset)
                        .build();
                var aisFileCollectHistList = aisFileCollectHistService.getAisFileCollectHistList(request);
                aisFileCollectHistList.forEach(aisFileCollectHist -> aisFileCollectHistCache.put(aisFileCollectHist.getFileName(), aisFileCollectHist));
                offset += aisFileCollectHistList.size();
            }
        }
    }

    /**
     * 10분마다 실행
     */
    @Scheduled(fixedRate = 600000) // 10분
    public void monitorFolder() {
        log.info("Checking for new files.");
        checkForNewFilesAndProcess();
    }

    /**
     * 폴더에서 새로운 파일 확인 및 처리
     */
    public void checkForNewFilesAndProcess() {
        var folderPath = Paths.get(filePath);
        var fileName = "";
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath, "*.csv")) {
            for (Path file : directoryStream) {
                fileName = String.valueOf(file.getFileName());
                if (aisFileCollectHistCache.get(fileName) == null) {
                    log.info("[FileReadService] Processing new AIS file: " + file);
                    var totalCount = parseAndSaveAisShipData(file);
                    log.info("total count ::: {}", totalCount);
                    var aisFileCollectHist = AisFileCollectHist.builder()
                            .fileName(fileName)
                            .fileCollectTimestamp(LocalDateTime.now())
                            .build();
                    aisFileCollectHistService.insertAisFileCollectHistList(aisFileCollectHist);
                    aisFileCollectHistCache.put(fileName, aisFileCollectHist);
                }
            }
        } catch (Exception e) {
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

                if (dataList.size() >= 1000) {
                    totalCount += saveAisShipData(dataList);
                    dataList.clear();
                }
            }

            if (!dataList.isEmpty()) {
                totalCount += saveAisShipData(dataList);
            }
        } catch (Exception e) {
            log.error("[FileReadService] Failed parsing AIS file.");
        }

        return totalCount;
    }

    private int saveAisShipData(List<AisShipData> aisShipDataList) {
        var totalCount = 0;
        for (var aisShipData : aisShipDataList) {
            batchSqlSessionTemplate.insert("upsertAisShipDataLast", aisShipData);
            batchSqlSessionTemplate.insert("insertAisShipDataHist", aisShipData);
            batchSqlSessionTemplate.insert("upsertShip", buildShip(aisShipData));
            totalCount++;
        }

        return totalCount;
    }

    private AisShipData buildAisShipData(CSVRecord aisShipDataRecord) {
        var aisShipData = new AisShipData();
        aisShipData.setTimestamp(parseDateTime(aisShipDataRecord.get("timestamp")));
        aisShipData.setMmsiProcess(aisShipDataRecord.get("mmsi_process"));
        aisShipData.setMmsi(aisShipDataRecord.get("mmsi"));
        aisShipData.setImoNo(aisShipDataRecord.get("imo_no"));
        aisShipData.setMmsiOrgin(aisShipDataRecord.get("mmsi_orgin"));
        aisShipData.setImoNoOrgin(aisShipDataRecord.get("imo_no_orgin"));
        aisShipData.setPositionPrcssSttusTy(aisShipDataRecord.get("position_prcss_sttus_ty"));
        aisShipData.setStaticPrcssSttusTy(aisShipDataRecord.get("static_prcss_sttus_ty"));
        aisShipData.setMappingPrcssSttusTy(aisShipDataRecord.get("mapping_prcss_sttus_ty"));
        aisShipData.setClstrTotal(parseInteger(aisShipDataRecord.get("clstr_total")));
        aisShipData.setAisVer(parseInteger(aisShipDataRecord.get("ais_ver")));
        aisShipData.setCallSign(aisShipDataRecord.get("call_sign"));
        aisShipData.setShipNm(aisShipDataRecord.get("ship_nm"));
        aisShipData.setShipType(parseInteger(aisShipDataRecord.get("ship_type")));
        aisShipData.setShipDimA(parseInteger(aisShipDataRecord.get("ship_dim_a")));
        aisShipData.setShipDimB(parseInteger(aisShipDataRecord.get("ship_dim_b")));
        aisShipData.setShipDimC(parseInteger(aisShipDataRecord.get("ship_dim_c")));
        aisShipData.setShipDimD(parseInteger(aisShipDataRecord.get("ship_dim_d")));
        aisShipData.setFixingDeviceSe(parseInteger(aisShipDataRecord.get("fixing_device_se")));
        aisShipData.setEta(aisShipDataRecord.get("eta"));
        aisShipData.setMaxDraught(parseInteger(aisShipDataRecord.get("max_draught")));
        aisShipData.setDestination(aisShipDataRecord.get("destination"));
        aisShipData.setStaticRcvDt(parseDateTime(aisShipDataRecord.get("static_rcv_dt")));
        aisShipData.setLcRcvDt(parseDateTime(aisShipDataRecord.get("lc_rcv_dt")));
        aisShipData.setStaticMessageNo(parseInteger(aisShipDataRecord.get("static_message_no")));
        aisShipData.setLcMessageNo(parseInteger(aisShipDataRecord.get("lc_message_no")));
        aisShipData.setDteFlag(parseBoolean(aisShipDataRecord.get("dte_flag")));
        aisShipData.setAisChannel(aisShipDataRecord.get("ais_channel"));
        aisShipData.setRepeatIdct(parseInteger(aisShipDataRecord.get("repeat_idct")));
        aisShipData.setNvgSttus(parseInteger(aisShipDataRecord.get("nvg_sttus")));
        aisShipData.setRot(parseInteger(aisShipDataRecord.get("rot")));
        aisShipData.setSog(parseDouble(aisShipDataRecord.get("sog")));
        aisShipData.setPosAccrcy(parseInteger(aisShipDataRecord.get("pos_accrcy")));
        aisShipData.setLoLo(parseDouble(aisShipDataRecord.get("lo_lo")));
        aisShipData.setLaLa(parseDouble(aisShipDataRecord.get("la_la")));
        aisShipData.setCog(parseDouble(aisShipDataRecord.get("cog")));
        aisShipData.setHeading(parseInteger(aisShipDataRecord.get("heading")));
        aisShipData.setSpeclManuvIdct(parseInteger(aisShipDataRecord.get("specl_manuv_idct")));
        aisShipData.setRaimFlag(parseBoolean(aisShipDataRecord.get("raim_flag")));
        aisShipData.setCmmncSttus(aisShipDataRecord.get("cmmnc_sttus"));
        aisShipData.setPacketIds(aisShipDataRecord.get("packet_id_s"));
        aisShipData.setAisClass(aisShipDataRecord.get("ais_class"));
        aisShipData.setRcordDt(parseDateTime(aisShipDataRecord.get("rcord_dt")));
        aisShipData.setCretDt(parseDateTime(aisShipDataRecord.get("cret_dt")));
        aisShipData.setClstrCount(parseInteger(aisShipDataRecord.get("clstr_count")));

        return aisShipData;
    }

    private Ship buildShip(AisShipData aisShipData) {
        var ship = new Ship();
        ship.setShipId("0100"+aisShipData.getMmsi());
        ship.setShipSourceType("0100");
        ship.setImoNo(aisShipData.getImoNo());
        ship.setMmsi(aisShipData.getMmsi());
        //
        ship.setCallSign(aisShipData.getCallSign());
        ship.setShipNm(aisShipData.getShipNm());
        //
        ship.setShipType(String.valueOf(aisShipData.getShipType()));
        //
        ship.setDestination(aisShipData.getDestination());
        ship.setEta(aisShipData.getEta());
        //
        ship.setMaxDraughtM(Double.valueOf(aisShipData.getMaxDraught()));
        //

        return ship;
    }

    private Integer parseInteger(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDouble(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDateTime parseDateTime(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 여러 포맷을 위한 리스트
        List<DateTimeFormatter> formatters = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS"), // 마이크로초 포함
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // 밀리초 없을 경우
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        return null;
    }

    private Boolean parseBoolean(String value) {
        return (value == null || value.isEmpty()) ? null : Boolean.parseBoolean(value);
    }
}