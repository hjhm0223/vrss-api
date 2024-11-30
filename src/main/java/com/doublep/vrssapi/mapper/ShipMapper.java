package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.Ship;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShipMapper {

    Ship selectShip(String shipId);
    void upsertShip(Ship ship);
    void bulkUpsertShip(List<Ship> ship);
    List<Ship> getUnregisteredShipList();

}
