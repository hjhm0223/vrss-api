package com.doublep.vrssapi.controller;

import com.doublep.vrssapi.model.ShipVoyageRiskMap;
import com.doublep.vrssapi.model.request.ShipVoyageRiskMapRequest;
import com.doublep.vrssapi.model.response.ListResponse;
import com.doublep.vrssapi.service.ShipVoyageRiskMapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ship-voyage-risk-map")
@RequiredArgsConstructor
public class ShipVoyageRiskMapController {

    private final ShipVoyageRiskMapService shipVoyageRiskMapService;

    @GetMapping
    public ResponseEntity<ListResponse<ShipVoyageRiskMap>> getShipVoyageRiskMap(@Valid ShipVoyageRiskMapRequest request
            , @RequestBody ShipVoyageRiskMapRequest route) {
        request.setRoute(route.getRoute());
        return ResponseEntity.ok(shipVoyageRiskMapService.getShipVoyageRiskMap(request));
    }

}
