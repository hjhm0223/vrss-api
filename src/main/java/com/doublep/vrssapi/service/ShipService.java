package com.doublep.vrssapi.service;

import com.doublep.vrssapi.advisor.exception.CustomSvmpException;
import com.doublep.vrssapi.mapper.ShipMapper;
import com.doublep.vrssapi.model.Route;
import com.doublep.vrssapi.model.Ship;
import com.doublep.vrssapi.model.ShipInfo;
import com.doublep.vrssapi.model.Position;
import com.doublep.vrssapi.model.request.ShipPredictTrackRequest;
import com.doublep.vrssapi.model.request.ShipTrackRequest;
import com.doublep.vrssapi.model.response.ListResponse;
import com.doublep.vrssapi.model.response.SvmpApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.doublep.vrssapi.constant.SvmpUrlConstant.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipService {

    private final ShipMapper shipMapper;
    private final RestClient restClient;

    public ListResponse<Ship> getShipList() {
        var list = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(SVMP_API_SHIP_POSITION)
                        .queryParam("lowerLeftLatitude", 25.00)
                        .queryParam("upperRightLatitude", 45.00)
                        .queryParam("lowerLeftLongitude", 120.00)
                        .queryParam("upperRightLongitude", 140.00)
                        .build())
                .exchange((request, response) -> {
                    if (response.getStatusCode().isError()) {
                        throw new CustomSvmpException(response.getStatusText());
                    }
                    else {
                        var apiResponse = response.bodyTo(new ParameterizedTypeReference<SvmpApiResponse<List<Ship>>>() {});
                        if (isEmpty(apiResponse)) {
                            throw new CustomSvmpException();
                        }
                        return apiResponse.getResponse();
                    }
                });

        return new ListResponse<>(list, list.size());
    }

    public Ship getShipInfo(String imoNo) {
        var shipList = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(SVMP_API_SHIP)
                        .queryParam("keyword", imoNo)
                        .build())
                .exchange((request, response) -> {
                    if (response.getStatusCode().isError()) {
                        throw new CustomSvmpException(response.getStatusText());
                    }
                    else {
                        var apiResponse = response.bodyTo(new ParameterizedTypeReference<SvmpApiResponse<List<Ship>>>() {});
                        if (isEmpty(apiResponse)) {
                            throw new CustomSvmpException();
                        }
                        return apiResponse.getResponse();
                    }
                });

        return shipList.get(0);
    }

    public ListResponse<Position> getShipPastTrackList(ShipTrackRequest shipTrackRequest) {
        // SV 선박 조회 /search param:imoNo

        // 시스템 내 미등록 선박 체크 -> 미등록 선박일 경우 등록

        // SV Fleet 등록

        // 과거 궤적 조회 (60일)

        // 이력, 현재 상태 적재

        // 포트콜 조회

        // 도착지

        var shipId = getShipInfo(shipTrackRequest.getImoNo()).getShipId();
        var result = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(SVMP_API_SHIP_LAST_PAST_TRACK)
                        .queryParam("startDateTime", shipTrackRequest.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))
                        .queryParam("endDateTime", shipTrackRequest.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))
                        .build(shipId))
                .exchange((request, response) -> {
                    if (response.getStatusCode().isError()) {
                        throw new CustomSvmpException(response.getStatusText());
                    }
                    else {
                        var apiResponse = response.bodyTo(new ParameterizedTypeReference<SvmpApiResponse<ShipInfo>>() {});
                        if (isEmpty(apiResponse)) {
                            throw new CustomSvmpException();
                        }
                        return apiResponse.getResponse();
                    }
                });

        var trackList = result.getTrackData();

        return new ListResponse<>(trackList, trackList.size());
    }

    public Route getShipPredictTrackList(ShipPredictTrackRequest shipPredictTrackRequest) {
        return restClient.post()
                .uri(SVMP_API_SHIP_PREDICT_TRACK)
                .body(shipPredictTrackRequest)
                .exchange((request, response) -> {
                    if (response.getStatusCode().isError()) {
                        throw new CustomSvmpException(response.getStatusText());
                    }
                    else {
                        var apiResponse = response.bodyTo(new ParameterizedTypeReference<SvmpApiResponse<Route>>() {});
                        if (isEmpty(apiResponse)) {
                            throw new CustomSvmpException();
                        }
                        return apiResponse.getResponse();
                    }
                });
    }
}
