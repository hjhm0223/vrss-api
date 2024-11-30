package com.doublep.vrssapi.service;

import com.doublep.vrssapi.mapper.ShipVoyageRiskMapMapper;
import com.doublep.vrssapi.model.ShipVoyageRiskMap;
import com.doublep.vrssapi.model.request.ShipVoyageRiskMapRequest;
import com.doublep.vrssapi.model.response.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.doublep.vrssapi.constant.PredictionRouteType.MTN;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShipVoyageRiskMapService {

    private final ShipVoyageRiskMapMapper shipVoyageRiskMapMapper;
    private final VoyageRiskMapLastService voyageRiskMapLastService;

    @Transactional
    public ListResponse<ShipVoyageRiskMap> getShipVoyageRiskMap(ShipVoyageRiskMapRequest request) {
        var route = request.getRoute();
//        var coordinates = route.getFeatures().get(0).getGeometry().getCoordinates();
        var voyageRiskMapList = voyageRiskMapLastService.selectVoyageRiskMapLastList(null);
        voyageRiskMapList.forEach(shipVoyageRiskMap -> {
            shipVoyageRiskMap.setShipId(request.getShipId());
            shipVoyageRiskMap.setRequestTime(LocalDateTime.now());
            shipVoyageRiskMap.setPredictionRouteType(MTN);
        });
        // TODO 선박예측경로 + 항해위헙최종 intersection
        insertShipVoyageRiskMapList(voyageRiskMapList);

        return new ListResponse<>(voyageRiskMapList, voyageRiskMapList.size());
    }

    private void insertShipVoyageRiskMapList(List<ShipVoyageRiskMap> voyageRiskMapList) {
        for (var voyageRiskMap : voyageRiskMapList) {
            shipVoyageRiskMapMapper.insertShipVoyageRiskMap(voyageRiskMap);
        }
    }
}
