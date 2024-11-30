package com.doublep.vrssapi.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipVoyageRiskMap {

    private String shipId;
    private LocalDateTime requestTime;
    private String predictionRouteType;
    private Integer zoneOrder;
    private LocalDateTime zoneEntryTime;
    private LocalDateTime zoneEndTime;
    private Double longitude;
    private Double latitude;
    private String voyageRiskMapId;
    private Double longitudeLength;
    private Double latitudeLength;
    private Double windSpeed;
    private Double windDirection;
    private Double waveHeight;
    private Double waveDirection;
    private Double relativeHumidity;
    private Double totalColumnWaterVapor;
    private Double skinTemperature;
    private Double voyageRiskLevel;
    private Double tidalCurrentSpeed;
    private Double tidalCurrentDirection;
    private Double typhoonLongitude;
    private Double typhoonLatitude;
    private Double typhoonGrade;

}
