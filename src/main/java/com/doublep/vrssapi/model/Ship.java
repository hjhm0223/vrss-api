package com.doublep.vrssapi.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ship {

    private String shipId;
    private String imoNo;
    private String mmsi;
    private String callSign;
    private String shipName;
    private String shipAliasName;
    private String shipType;
    private String shipTypeBySize;
    private String shipTypeCargo;
    private String shipImage;
    private Integer built;
    private Integer dwt;
    private String hullType;
    private Integer gt;
    private String destination;
    private LocalDateTime eta;
    private String shipStatusType;
    private String builtBy;
    private String builtAt;
    private Double loa;
    private Double depth;
    private Double maxDraught;
    private String engineBuiltBy;
    private String designedBy;
    private Double serviceSpeed;
    private String nationCode;
    private String useYn;
    private Position position;
    private SiteShip siteShip;
    private Double breath;
    private Integer teuCapacity;
    private Integer liquidCapacity;

    @Data
    public static class SiteShip {

        private String siteId;
        private String shipId;
        private String registerId;
        private LocalDateTime registerDateTIme;

    }

}
