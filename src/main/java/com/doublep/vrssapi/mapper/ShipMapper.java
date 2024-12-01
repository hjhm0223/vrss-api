package com.doublep.vrssapi.mapper;

import com.doublep.vrssapi.model.Ship;
import com.doublep.vrssapi.model.request.CommonRequest;
import com.doublep.vrssapi.model.response.ShipPositionResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShipMapper {

    Ship selectShip(String shipId);
    List<ShipPositionResponse> selectShipListWithPosition(CommonRequest request);
    int countShipList();
    void upsertShip(Ship ship);
    void bulkUpsertShip(List<Ship> ship);
    List<Ship> getUnregisteredShipList();

}
