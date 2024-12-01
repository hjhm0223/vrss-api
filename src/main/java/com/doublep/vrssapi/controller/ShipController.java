package com.doublep.vrssapi.controller;

import com.doublep.vrssapi.model.request.CommonRequest;
import com.doublep.vrssapi.model.response.ListResponse;
import com.doublep.vrssapi.model.response.ShipPositionResponse;
import com.doublep.vrssapi.service.ShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ships")
@RequiredArgsConstructor
public class ShipController {

    private final ShipService shipService;

    @GetMapping("/snapshot")
    public ResponseEntity<ListResponse<ShipPositionResponse>> getShipList(CommonRequest request) {
        return ResponseEntity.ok(shipService.getShipList(request));
    }
}
