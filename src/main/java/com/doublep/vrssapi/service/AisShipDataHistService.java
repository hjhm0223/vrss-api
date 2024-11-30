package com.doublep.vrssapi.service;

import com.doublep.vrssapi.mapper.AisShipDataHistMapper;
import com.doublep.vrssapi.model.AisShipData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AisShipDataHistService {

    private final AisShipDataHistMapper aisShipDataHistMapper;
    private final SqlSessionTemplate batchSqlSessionTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchInsertAisShipDataHist(List<AisShipData> aisShipDataList) {
        for (var aisShipData : aisShipDataList) {
            batchSqlSessionTemplate.insert("insertAisShipDataHist", aisShipData);
        }
    }

    public void loopInsertAisShipDataHist(List<AisShipData> aisShipDataList) {
        for (var aisShipData : aisShipDataList) {
            try {
                aisShipDataHistMapper.insertAisShipDataHist(aisShipData);
            } catch (DuplicateKeyException e) {
                log.error("[InsertAisShipDataHist] Skipped duplicate key shipData. mmsiProcess:{} timestamp:{}", aisShipData.getMmsiProcess(), aisShipData.getTimestamp());
            } catch (Exception e) {
                log.error("[InsertAisShipDataHist] Failed while inserting shipData. mmsiProcess:{} timestamp:{}", aisShipData.getMmsiProcess(), aisShipData.getTimestamp());
            }
        }
    }

    public void bulkInsertAisShipDataHist(List<AisShipData> aisShipDataList) {
        try {
            aisShipDataHistMapper.bulkInsertAisShipDataHist(aisShipDataList);
        } catch (DuplicateKeyException e) {
            log.error("[InsertAisShipDataHist] Skipped duplicate key shipData. {}", e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("[InsertAisShipDataHist] Failed while bulk inserting shipData. {}", e.getLocalizedMessage());
        }
    }

}
