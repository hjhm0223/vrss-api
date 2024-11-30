package com.doublep.vrssapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipPastRoute {
    private String shipId;
    private LocalDateTime requestTime;
    private LocalDateTime searchBeginTime;
    private LocalDateTime searchEndTime;
    private LocalDateTime startTime;
    private LocalDateTime arrivalTime;
    private Double startLongitude;
    private Double startLatitude;
    private Double arrivalLongitude;
    private Double arrivalLatitude;
    private Double routeDistance;
    private Object route;
}
