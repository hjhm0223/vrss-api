package com.doublep.vrssapi.service;

import com.doublep.vrssapi.mapper.ShipPastRouteMapper;
import com.doublep.vrssapi.model.ShipPastRoute;
import com.doublep.vrssapi.model.api.Position;
import com.doublep.vrssapi.model.response.ListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;

import static com.doublep.vrssapi.util.GeometryUtils.calculateTotalDistance;
import static com.doublep.vrssapi.util.GeometryUtils.toLineString;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShipPastRouteService {

    private final ShipPastRouteMapper shipPastRouteMapper;
    private final SvApiService svApiService;
    private final ObjectMapper objectMapper;

    @Transactional
    public ListResponse<Position> getShipPastTrackFromLastPort(String shipId) {
        var pastTrack = svApiService.getShipPastTrackFromLastPort(shipId);
        var ship = pastTrack.getShip();
        var port = pastTrack.getPort();
        var trackList = pastTrack.getTrackData();
        trackList.sort(Comparator.comparing(Position::getTimestamp));
        Position start = trackList.get(0);
        Position end = trackList.get(trackList.size() - 1);

        var requestTime = LocalDateTime.now();
        insertShipPastRoute(ShipPastRoute.builder()
                .shipId(ship.getShipId())
                .requestTime(requestTime)
                .searchBeginTime(pastTrack.getStartDateTime())
                .searchEndTime(pastTrack.getEndDateTime())
                .startTime(pastTrack.getStartDateTime())
                .arrivalTime(pastTrack.getEndDateTime())
                .startLongitude(start.getLongitude())
                .startLatitude(start.getLatitude())
                .arrivalLongitude(end.getLongitude())
                .arrivalLatitude(end.getLatitude())
                .routeDistance(calculateTotalDistance(port.getGeoData()))
                .route(toLineString(port.getGeoData()))
                .build());

        return new ListResponse<>(trackList, trackList.size());
    }

    private void insertShipPastRoute(ShipPastRoute shipPastRoute) {
        shipPastRouteMapper.insertShipPastRoute(shipPastRoute);
    }
}
