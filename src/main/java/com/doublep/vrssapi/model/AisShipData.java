package com.doublep.vrssapi.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AisShipData {

    private LocalDateTime timestamp;
    private String mmsiProcess;
    private String mmsi;
    private String imoNo;
    private String mmsiOrgin;
    private String imoNoOrgin;
    private String positionPrcssSttusTy;
    private String staticPrcssSttusTy;
    private String mappingPrcssSttusTy;
    private Integer clstrTotal;
    private LocalDateTime cretDt;
    private LocalDateTime updtDt;
    private Integer aisVer;
    private String callSign;
    private String shipNm;
    private Integer shipType;
    private Integer shipDimA;
    private Integer shipDimB;
    private Integer shipDimC;
    private Integer shipDimD;
    private Integer fixingDeviceSe;
    private String eta;
    private Integer maxDraught;
    private String destination;
    private LocalDateTime staticRcvDt;
    private LocalDateTime lcRcvDt;
    private Integer staticMessageNo;
    private Integer lcMessageNo;
    private Boolean dteFlag;
    private String aisChannel;
    private Integer repeatIdct;
    private Integer nvgSttus;
    private Integer rot;
    private Double sog;
    private Integer posAccrcy;
    private Double loLo;
    private Double laLa;
    private Double cog;
    private Integer heading;
    private Integer speclManuvIdct;
    private Boolean raimFlag;
    private String cmmncSttus;
    private String packetIds;
    private String aisClass;
    private LocalDateTime rcordDt;
    private Integer clstrCount;

}
