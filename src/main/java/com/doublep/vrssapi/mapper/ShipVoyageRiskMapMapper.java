package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.ShipVoyageRiskMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShipVoyageRiskMapMapper {
    void insertShipVoyageRiskMap(ShipVoyageRiskMap shipVoyageRiskMap);
}
