package com.doublep.vrssapi.service;

import com.doublep.vrssapi.advisor.exception.CustomJsonProcessingException;
import com.doublep.vrssapi.mapper.ShipPredictionRouteMapper;
import com.doublep.vrssapi.model.ShipPredictionRoute;
import com.doublep.vrssapi.model.api.request.ShipRouteRequest;
import com.doublep.vrssapi.model.request.ShipPredictRouteRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.doublep.vrssapi.constant.PredictionRouteType.MTN;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShipPredictionRouteService {

    private final ShipPredictionRouteMapper shipPredictionRouteMapper;
    private final ShipService shipService;
    private final SvApiService svApiService;
    private final ObjectMapper objectMapper;

    @Transactional
    public ShipPredictionRoute getShipPredictTrackList(ShipPredictRouteRequest shipPredictRouteRequest) {
        var requestTime = LocalDateTime.now();
        var shipId = shipPredictRouteRequest.getShipId();
        if (shipPredictRouteRequest.getDestinationLatitude() == null || shipPredictRouteRequest.getDestinationLongitude() == null) {
            var lastPort = svApiService.getShipPortCall(shipPredictRouteRequest.getShipId());
            shipPredictRouteRequest.setDestinationLatitude(lastPort.getLatitude());
            shipPredictRouteRequest.setDestinationLongitude(lastPort.getLongitude());
        }
        var ship = shipService.getShip(shipId);

        var request = ShipRouteRequest.builder()
                .shipTypeBySize(!isEmpty(ship) ? ship.getShipTypeSize() : "CONTAINER06")
                .startDateTime(LocalDateTime.now())
                .wayPoints(Arrays.asList(ShipRouteRequest.Point.builder()
                        .latitude(shipPredictRouteRequest.getLatitude())
                        .longitude(shipPredictRouteRequest.getLongitude())
                        .build(), ShipRouteRequest.Point.builder()
                        .latitude(shipPredictRouteRequest.getDestinationLatitude())
                        .longitude(shipPredictRouteRequest.getDestinationLongitude())
                        .build()))
                .build();
        var route = svApiService.getShipRoute(request);
        var features = route.getFeatures();
        var properties = features.get(0).getProperties();
        var planPoints = properties.getPlanPoints();
//        planPoints.sort(Comparator.comparing(Route.PlanPoint::getDateTIme));
        try {
            var predictionRoute = ShipPredictionRoute.builder()
                    .shipId(shipId)
                    .requestTime(requestTime)
                    .predictionRouteType(MTN)
                    .standardPredictionTime(requestTime)
                    .startLongitude(planPoints.get(0).getLongitude())
                    .startLatitude(planPoints.get(0).getLatitude())
                    .arrivalLongitude(planPoints.get(planPoints.size() - 1).getLongitude())
                    .arrivalLatitude(planPoints.get(planPoints.size() - 1).getLatitude())
                    .rpType(null)
                    .rpRequirementSecond((double) Duration.between(requestTime, LocalDateTime.now()).getSeconds())
                    .routeDistance(properties.getDistance())
                    .routeRequirementSecond((double) Duration.between(properties.getStartDt(), properties.getArvlDateTime()).getSeconds())
                    .route(objectMapper.writeValueAsString(features.get(0).getGeometry()))
                    .build();
            insertShipPredictionRoute(predictionRoute);

            return predictionRoute;
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred ::: {}", route);
            throw new CustomJsonProcessingException();
        }
    }

    private void insertShipPredictionRoute(ShipPredictionRoute shipPredictionRoute) {
        shipPredictionRouteMapper.insertShipPredictionRoute(shipPredictionRoute);
    }
}
