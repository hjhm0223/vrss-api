package com.doublep.vrssapi.model.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Position {

    private String imoNo;
    private String mmsi;
    private String deviceType;
    private String callsSign;
    private String shipName;
    private Integer aisShipType;
    private Integer aisDimA;
    private Integer aisDimB;
    private Integer aisDimC;
    private Integer aisDimD;
    private String aisEta;
    private String aisDestination;
    private LocalDateTime staticDateTime;
    private LocalDateTime timestamp;
    private String aisClass;
    private String nvgStatus;
    private Double rateOfTurn;
    private Double speedOverGround;
    private Integer positionAccuracy;
    private Double longitude;
    private Double latitude;
    private Double courseOverGround;
    private Integer trueHeading;
    private Integer elapsed;
    private Integer utcSecond;
    private Integer dte;
    private String specialManeuverIndicator;
    private String raimFlag;
    private Integer staticMessageNo;
    private Integer locationMessageNo;
    private Double aisMaxDraught;

}
