package com.doublep.vrssapi.controller;

import com.doublep.vrssapi.model.Route;
import com.doublep.vrssapi.model.Ship;
import com.doublep.vrssapi.model.Position;
import com.doublep.vrssapi.model.request.ShipPredictTrackRequest;
import com.doublep.vrssapi.model.request.ShipTrackRequest;
import com.doublep.vrssapi.model.response.ListResponse;
import com.doublep.vrssapi.service.ShipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ships")
@RequiredArgsConstructor
public class ShipController {

    private final ShipService shipService;

    @GetMapping
    public ResponseEntity<ListResponse<Ship>> getShipList() {
        return ResponseEntity.ok(shipService.getShipList());
    }

    @GetMapping("/{imoNo}")
    public ResponseEntity<Ship> getShipInfo(@PathVariable String imoNo) {
        return ResponseEntity.ok(shipService.getShipInfo(imoNo));
    }

    @GetMapping("/{imoNo}/past-track")
    public ResponseEntity<ListResponse<Position>> getShipPastTrackList(@PathVariable String imoNo,
                                                                       @Valid ShipTrackRequest shipTrackRequest) {
        shipTrackRequest.setImoNo(imoNo);
        return ResponseEntity.ok(shipService.getShipPastTrackList(shipTrackRequest));
    }

    @PostMapping("/predict-track")
    public ResponseEntity<Route> getShipPredictTrackList(@RequestBody ShipPredictTrackRequest shipPredictTrackRequest) {
        return ResponseEntity.ok(shipService.getShipPredictTrackList(shipPredictTrackRequest));
    }

}
