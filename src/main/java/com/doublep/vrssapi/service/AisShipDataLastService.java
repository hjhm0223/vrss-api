package com.doublep.vrssapi.service;

import com.doublep.vrssapi.mapper.AisShipDataLastMapper;
import com.doublep.vrssapi.model.AisShipData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AisShipDataLastService {

    private final AisShipDataLastMapper aisShipDataLastMapper;
    private final SqlSessionTemplate batchSqlSessionTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchUpsertAisShipDataLast(List<AisShipData> aisShipDataList) {
        for (var aisShipData : aisShipDataList) {
            batchSqlSessionTemplate.insert("upsertAisShipDataLast", aisShipData);
        }
    }

    public void loopUpsertAisShipDataLast(List<AisShipData> aisShipDataList) {
        for (var aisShipData : aisShipDataList) {
            try {
                aisShipDataLastMapper.upsertAisShipDataLast(aisShipData);
            } catch (DuplicateKeyException e) {
                log.error("[UpsertAisShipDataLast(] Skipped duplicate key shipData. mmsiProcess:{} timestamp:{}", aisShipData.getMmsiProcess(), aisShipData.getTimestamp());
            } catch (Exception e) {
                log.error("[UpsertAisShipDataLast(] Failed while upserting shipData. mmsiProcess:{} timestamp:{}", aisShipData.getMmsiProcess(), aisShipData.getTimestamp());
            }
        }
    }

    public void bulkUpsertAisShipDataLast(List<AisShipData> aisShipDataList) {
        try {
            // 중복 제거 (기준: mmsiProcess)
            var uniqueMmsiProcessShipMap = aisShipDataList.stream()
                    .collect(Collectors.toMap(
                            AisShipData::getMmsiProcess,
                            ship -> ship,
                            (existing, replacement) -> replacement
                    ));
            var uniqueMmsiProcessShipList = new ArrayList<>(uniqueMmsiProcessShipMap.values());
            aisShipDataLastMapper.bulkUpsertAisShipDataLast(uniqueMmsiProcessShipList);
        } catch (DuplicateKeyException e) {
            log.error("[UpsertAisShipDataLast(] Skipped duplicate key shipData. {}", e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("[UpsertAisShipDataLast(] Failed while bulk inserting shipData. {}", e.getLocalizedMessage());
        }
    }

}
