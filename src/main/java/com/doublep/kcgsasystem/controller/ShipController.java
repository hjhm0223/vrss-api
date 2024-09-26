package com.doublep.kcgsasystem.controller;

import com.doublep.kcgsasystem.service.ShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ships")
@RequiredArgsConstructor
public class ShipController {

    private final ShipService shipService;

    @GetMapping("/{shipId}")
    public ResponseEntity<Object> getShipInfo(@PathVariable String shipId) {
        return ResponseEntity.ok(shipService.getShipInfo(shipId));
    }

    @GetMapping("/{shipId}/past-track")
    public ResponseEntity<List<Object>> getShipPastTrackList(@PathVariable String shipId) {
        return ResponseEntity.ok(shipService.getShipPastTrackList(shipId));
    }

}
