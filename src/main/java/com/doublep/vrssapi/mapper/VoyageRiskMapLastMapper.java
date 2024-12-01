package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.ShipVoyageRiskMap;
import com.doublep.vrssapi.model.request.ShipVoyageRiskMapRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoyageRiskMapLastMapper {
    List<ShipVoyageRiskMap> selectVoyageRiskMapLastList(ShipVoyageRiskMapRequest request);
}
