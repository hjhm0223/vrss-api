package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.ShipPastRoute;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShipPastRouteMapper {
    void insertShipPastRoute(ShipPastRoute shipPastRoute);
}
