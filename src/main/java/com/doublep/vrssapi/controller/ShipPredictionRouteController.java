package com.doublep.vrssapi.controller;

import com.doublep.vrssapi.model.api.Route;
import com.doublep.vrssapi.model.request.ShipPredictRouteRequest;
import com.doublep.vrssapi.service.ShipPredictionRouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ship-prediction-routes")
@RequiredArgsConstructor
public class ShipPredictionRouteController {

    private final ShipPredictionRouteService shipPredictionRouteService;

    @GetMapping
    public ResponseEntity<Route> getShipPredictTrackList(@Valid ShipPredictRouteRequest shipPredictRouteRequest) {
        return ResponseEntity.ok(shipPredictionRouteService.getShipPredictTrackList(shipPredictRouteRequest));
    }

}
