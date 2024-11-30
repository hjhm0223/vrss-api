package com.doublep.vrssapi.model.api;

import com.doublep.vrssapi.model.api.Port;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShipPortCall {

    private String allProcessYn;
    private String arrivalAnchId;
    private String arrivalBerthId;
    private Port arrivalPort;
    private Double atSeaDistance;
    private Double atSeaHour;
    private String ata;
    private String atb;
    private String atd;
    private Port departurePort;
    private Double ecaDistance;
    private Double inPortDistance;
    private Double inPortHour;
    private String portCallProcessYn;

}
