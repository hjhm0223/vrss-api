package com.doublep.vrssapi.model;

import lombok.Data;

@Data
public class Port {

    private String portId;
    private String portName;
    private String unlocode;
    private String portAlias;
    private String nationCode;
    private Double latitude;
    private Double longitude;
    private String timezone;
    private String geoData;
    private String useYn;
    private Boolean usePortInsight;
    private PortShipType portShipType;

    @Data
    public static class PortShipType {
        private Integer arrivalCount;
        private Double latitude;
        private Double longitude;
        private Integer shipCount;
        private String shipType;
        private String shipTypeBySize;
    }
}
