package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.ShipPredictionRoute;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShipPredictionRouteMapper {
    void insertShipPredictionRoute(ShipPredictionRoute shipPredictionRoute);
}
