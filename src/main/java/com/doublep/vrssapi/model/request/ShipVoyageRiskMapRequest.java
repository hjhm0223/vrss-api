package com.doublep.vrssapi.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ShipVoyageRiskMapRequest {

    @NotEmpty
    private String shipId;

    private String route;
    private Double longitude;
    private Double latitude;
    private Double minLongitude;
    private Double minLatitude;
    private Double maxLongitude;
    private Double maxLatitude;

}
