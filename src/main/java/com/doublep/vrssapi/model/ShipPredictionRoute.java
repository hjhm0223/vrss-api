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
public class ShipPredictionRoute {
    private String shipId;
    private LocalDateTime requestTime;
    private String predictionRouteType;
    private LocalDateTime standardPredictionTime;
    private Double startLongitude;
    private Double startLatitude;
    private Double arrivalLongitude;
    private Double arrivalLatitude;
    private String rpType;
    private Double rpRequirementSecond;
    private Double routeDistance;
    private Double routeRequirementSecond;
    private Object route;
}
