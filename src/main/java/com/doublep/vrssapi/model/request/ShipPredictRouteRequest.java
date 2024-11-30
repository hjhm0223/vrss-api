package com.doublep.vrssapi.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShipPredictRouteRequest {

    @NotEmpty
    private String shipId;

    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;

    private Double destinationLongitude;
    private Double destinationLatitude;

}
