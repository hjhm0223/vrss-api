package com.doublep.vrssapi.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ship {

    private String shipId;
    private String shipSourceType;
    private String imoNo;
    private String mmsi;
    private String vpassId;
    private String fisherboatNo;
    private String vtsId;
    private String callSign;
    private String shipNm;
    private String shipAliasNm;
    private String shipType;
    private String shipTypeSize;
    private String shipTypeCargo;
    private String shipImage;
    private Integer built;
    private Integer dwt;
    private String hullType;
    private Integer gt;
    private String destination;
    private String eta;
    private String shipStatusType;
    private String builtBy;
    private String builtAt;
    private Double loa;
    private Double depthM;
    private Double maxDraughtM;
    private String engineBuiltBy;
    private String designedBy;
    private Double serviceSpeed;
    private LocalDateTime updtDt;
    private LocalDateTime registDt;
    private String registerId;
    private String useYn;
    private String validShipCode;
    private String beneficialOwner;
    private String commercialOperator;
    private String registeredOwner;
    private String technicalManager;
    private String thirdPartyOperator;
    private String nominalOwner;
    private String ismManager;
    private Double breadth;
    private Integer teuCapacity;
    private Integer liquidCapacity;

}
