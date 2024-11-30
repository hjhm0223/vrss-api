package com.doublep.vrssapi.controller;

import com.doublep.vrssapi.model.api.Position;
import com.doublep.vrssapi.model.response.ListResponse;
import com.doublep.vrssapi.service.ShipPastRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ship-past-routes")
@RequiredArgsConstructor
public class ShipPastRouteController {

    private final ShipPastRouteService shipPastRouteService;

    @GetMapping("/from-last-port")
    public ResponseEntity<ListResponse<Position>> getShipPastTrackFromLastPort(@RequestParam String shipId) {
        return ResponseEntity.ok(shipPastRouteService.getShipPastTrackFromLastPort(shipId));
    }

}
