package com.doublep.vrssapi.service;

import com.doublep.vrssapi.advisor.exception.CustomJsonProcessingException;
import com.doublep.vrssapi.mapper.ShipVoyageRiskMapMapper;
import com.doublep.vrssapi.model.ShipVoyageRiskMap;
import com.doublep.vrssapi.model.request.ShipVoyageRiskMapRequest;
import com.doublep.vrssapi.model.response.ListResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @Transactional
    public ListResponse<ShipVoyageRiskMap> getShipVoyageRiskMap(ShipVoyageRiskMapRequest request) {
        setMinMaxCoordinates(request);
        var voyageRiskMapList = voyageRiskMapLastService.selectVoyageRiskMapLastList(request);
        voyageRiskMapList.forEach(shipVoyageRiskMap -> {
            shipVoyageRiskMap.setShipId(request.getShipId());
            shipVoyageRiskMap.setRequestTime(LocalDateTime.now());
            shipVoyageRiskMap.setPredictionRouteType(MTN);
        });
        insertShipVoyageRiskMapList(voyageRiskMapList);

        return new ListResponse<>(voyageRiskMapList, voyageRiskMapList.size());
    }

    private void setMinMaxCoordinates(ShipVoyageRiskMapRequest request) {
        try {
            JsonNode route = objectMapper.readTree(request.getRoute());

            double minLat = Double.MAX_VALUE;
            double maxLat = Double.MIN_VALUE;
            double minLon = Double.MAX_VALUE;
            double maxLon = Double.MIN_VALUE;

            JsonNode coordinatesNode = route.get("coordinates");

            if (coordinatesNode != null && coordinatesNode.isArray()) {
                for (JsonNode coord : coordinatesNode) {
                    double lon = coord.get(0).asDouble();
                    double lat = coord.get(1).asDouble();

                    if (lat < minLat) minLat = lat;
                    if (lat > maxLat) maxLat = lat;
                    if (lon < minLon) minLon = lon;
                    if (lon > maxLon) maxLon = lon;
                }

                request.setMinLatitude(minLat - 0.25);
                request.setMaxLatitude(maxLat + 0.25);
                request.setMinLongitude(minLon - 0.25);
                request.setMaxLongitude(maxLon + 0.25);
            }
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException : {}", e.getLocalizedMessage());
            throw new CustomJsonProcessingException();
        }
    }

    private void insertShipVoyageRiskMapList(List<ShipVoyageRiskMap> voyageRiskMapList) {
        for (var voyageRiskMap : voyageRiskMapList) {
            shipVoyageRiskMapMapper.insertShipVoyageRiskMap(voyageRiskMap);
        }
    }
}
