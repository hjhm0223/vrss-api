package com.doublep.vrssapi.service;

import com.doublep.vrssapi.model.api.Port;
import com.doublep.vrssapi.model.api.Route;
import com.doublep.vrssapi.model.api.ShipInfo;
import com.doublep.vrssapi.model.api.request.FleetRequest;
import com.doublep.vrssapi.model.api.request.ShipInfoRequest;
import com.doublep.vrssapi.model.api.request.ShipPortCallRequest;
import com.doublep.vrssapi.model.api.request.ShipRouteRequest;
import com.doublep.vrssapi.model.api.response.ShipInfoResponse;
import com.doublep.vrssapi.model.api.response.ShipPortCallResponse;
import com.doublep.vrssapi.model.api.response.SvmpApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.doublep.vrssapi.constant.SvmpUrlConstant.*;
import static com.doublep.vrssapi.util.MultiValueMapConverter.convert;

@Slf4j
@Service
@RequiredArgsConstructor
public class SvApiService {

    private final RestClient restClient;

    public ShipInfoResponse getShipInfo(ShipInfoRequest request) {
        var response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(SVMP_API_SHIP)
                        .queryParams(convert(request))
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<SvmpApiResponse<List<ShipInfoResponse>>>() {});

        return Objects.requireNonNull(response).getResponse().get(0);
    }

    public void saveShipToFleet(String shipId) {
        var request = new FleetRequest();
        request.setShipIds(shipId);
        restClient.post()
                .uri(uriBuilder -> uriBuilder.path(SVMP_API_FLEET)
                        .queryParams(convert(request))
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<SvmpApiResponse<Void>>() {});
    }

    public ShipInfo getShipPastTrackFromLastPort(String shipId) {
        var response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(SVMP_API_SHIP_LAST_PAST_TRACK)
                        .build(shipId))
                .retrieve()
                .body(new ParameterizedTypeReference<SvmpApiResponse<ShipInfo>>() {});
        return response.getResponse();
    }

    public Route getShipRoute(ShipRouteRequest request) {
        var response = restClient.post()
                .uri(SVMP_API_SHIP_ROUTE)
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<SvmpApiResponse<Route>>() {});
        return response.getResponse();
    }

    public Port getShipPortCall(String shipId) {
        var now = LocalDateTime.now();
        var shipPortCallRequest = ShipPortCallRequest.builder()
                .startDateTime(now.minusDays(30))
                .endDateTime(now.plusDays(30))
                .build();
        var response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(SVMP_API_SHIP_PORT_CALL)
                        .queryParams(convert(shipPortCallRequest))
                        .build(shipId))
                .retrieve()
                .body(new ParameterizedTypeReference<SvmpApiResponse<ShipPortCallResponse>>() {});

        var portCall = response.getResponse();
        var lastPort = portCall.getPortCalls().get(0).getDeparturePort();
        return lastPort;
    }
}
