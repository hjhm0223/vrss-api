package com.doublep.vrssapi.model.request;

import com.doublep.vrssapi.model.api.Route;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ShipVoyageRiskMapRequest {

    @NotEmpty
    private String shipId;

    private Route route;
    private Double longitude;
    private Double latitude;

}
