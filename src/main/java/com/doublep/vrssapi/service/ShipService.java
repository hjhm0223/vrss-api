package com.doublep.vrssapi.service;


import com.doublep.vrssapi.mapper.ShipMapper;
import com.doublep.vrssapi.model.Ship;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipService {

    private final ShipMapper shipMapper;

    public int upsertShip(Ship ship) {
        return shipMapper.upsertShip(ship);
    }

}
