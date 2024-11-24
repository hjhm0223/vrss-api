package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.Ship;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShipMapper {

    int upsertShip(Ship ship);

}
