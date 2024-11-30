package com.doublep.vrssapi.model.api;

import com.doublep.vrssapi.model.Ship;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShipInfo {

    private Ship ship;
    private List<Position> trackData;
    private Port port;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}
