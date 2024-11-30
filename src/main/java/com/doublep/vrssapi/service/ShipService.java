package com.doublep.vrssapi.service;


import com.doublep.vrssapi.constant.AisSource;
import com.doublep.vrssapi.mapper.ShipMapper;
import com.doublep.vrssapi.model.AisShipData;
import com.doublep.vrssapi.model.Ship;
import com.doublep.vrssapi.model.api.request.ShipInfoRequest;
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

import static com.doublep.vrssapi.constant.SvProcessStatus.*;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipService {

    private final ShipMapper shipMapper;
    private final SvApiService svApiService;
    private final SqlSessionTemplate batchSqlSessionTemplate;

    public Ship getShip(String shipId) {
        return shipMapper.selectShip(shipId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchUpsertShip(List<Ship> shipList) {
        for (var ship : shipList) {
            batchSqlSessionTemplate.insert("upsertShip", ship);
        }
    }

    public void loopUpsertShip(List<Ship> shipList) {
        for (var ship : shipList) {
            try {
                shipMapper.upsertShip(ship);
            } catch (DuplicateKeyException e) {
                log.error("[UpsertShip] Skipped duplicate key shipData. shipId: {}", ship.getShipId());
            } catch (Exception e) {
                log.error("[UpsertShip(] Failed while bulk upserting shipData. shipId: {}", ship.getShipId());
            }
        }
    }

    public void bulkUpsertShip(List<AisShipData> aisShipDataList) {
        try {
            // 중복 제거 (기준: mmsiProcess)
            var uniqueMmsiShipMap = aisShipDataList.stream()
                    .map(AisShipData::buildShipList)
                    .collect(Collectors.toMap(
                            Ship::getMmsi,
                            ship -> ship,
                            (existing, replacement) -> replacement
                    ));
            var uniqueMmsiShipList = new ArrayList<>(uniqueMmsiShipMap.values());
            shipMapper.bulkUpsertShip(uniqueMmsiShipList);
        } catch (DuplicateKeyException e) {
            log.error("[UpsertShip] Skipped duplicate key shipData. {}", e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("[UpsertShip(] Failed while bulk upserting shipData. {}", e.getLocalizedMessage());
        }
    }

    public void checkUnregisteredShipList() {
        var unregisteredShipList = shipMapper.getUnregisteredShipList();
        for (var ship : unregisteredShipList) {
            var mmsi = ship.getMmsi();
            var imoNo = ship.getImoNo();
            if (!hasText(mmsi) && !hasText(imoNo)) {
                ship.setSvProcessStatus(REGISTRATION_NOT_REQUIRED);
            } else {
                var request = ShipInfoRequest.builder()
                        .keyword(hasText(imoNo) ? imoNo : mmsi)
                        .aisSource(AisSource.CLASS_ALL)
                        .build();
                var shipInfo = svApiService.getShipInfo(request);
                if (isEmpty(shipInfo)) {
                    ship.setSvProcessStatus(REGISTRATION_NO_DATA_ERROR);
                } else {
                    svApiService.saveShipToFleet(shipInfo.getShipId());
                    ship.setSvProcessStatus(REGISTERED);
                    ship.setSvShipId(shipInfo.getShipId());
                }
            }
            shipMapper.upsertShip(ship);
        }
    }
}
