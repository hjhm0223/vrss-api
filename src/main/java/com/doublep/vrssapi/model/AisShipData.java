package com.doublep.vrssapi.model;

import lombok.Data;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.doublep.vrssapi.constant.ShipSourceType.AIS;

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

    public static AisShipData buildAisShipData(CSVRecord aisShipDataRecord) {
        var aisShipData = new AisShipData();
        aisShipData.setTimestamp(parseDateTime(aisShipDataRecord.get("timestamp")));
        aisShipData.setMmsiProcess(aisShipDataRecord.get("mmsi_process"));
        aisShipData.setMmsi(aisShipDataRecord.get("mmsi"));
        aisShipData.setImoNo(aisShipDataRecord.get("imo_no"));
        aisShipData.setMmsiOrgin(aisShipDataRecord.get("mmsi_orgin"));
        aisShipData.setImoNoOrgin(aisShipDataRecord.get("imo_no_orgin"));
        aisShipData.setPositionPrcssSttusTy(aisShipDataRecord.get("position_prcss_sttus_ty"));
        aisShipData.setStaticPrcssSttusTy(aisShipDataRecord.get("static_prcss_sttus_ty"));
        aisShipData.setMappingPrcssSttusTy(aisShipDataRecord.get("mapping_prcss_sttus_ty"));
        aisShipData.setClstrTotal(parseInteger(aisShipDataRecord.get("clstr_total")));
        aisShipData.setAisVer(parseInteger(aisShipDataRecord.get("ais_ver")));
        aisShipData.setCallSign(aisShipDataRecord.get("call_sign"));
        aisShipData.setShipNm(aisShipDataRecord.get("ship_nm"));
        aisShipData.setShipType(parseInteger(aisShipDataRecord.get("ship_type")));
        aisShipData.setShipDimA(parseInteger(aisShipDataRecord.get("ship_dim_a")));
        aisShipData.setShipDimB(parseInteger(aisShipDataRecord.get("ship_dim_b")));
        aisShipData.setShipDimC(parseInteger(aisShipDataRecord.get("ship_dim_c")));
        aisShipData.setShipDimD(parseInteger(aisShipDataRecord.get("ship_dim_d")));
        aisShipData.setFixingDeviceSe(parseInteger(aisShipDataRecord.get("fixing_device_se")));
        aisShipData.setEta(aisShipDataRecord.get("eta"));
        aisShipData.setMaxDraught(parseInteger(aisShipDataRecord.get("max_draught")));
        aisShipData.setDestination(aisShipDataRecord.get("destination"));
        aisShipData.setStaticRcvDt(parseDateTime(aisShipDataRecord.get("static_rcv_dt")));
        aisShipData.setLcRcvDt(parseDateTime(aisShipDataRecord.get("lc_rcv_dt")));
        aisShipData.setStaticMessageNo(parseInteger(aisShipDataRecord.get("static_message_no")));
        aisShipData.setLcMessageNo(parseInteger(aisShipDataRecord.get("lc_message_no")));
        aisShipData.setDteFlag(parseBoolean(aisShipDataRecord.get("dte_flag")));
        aisShipData.setAisChannel(aisShipDataRecord.get("ais_channel"));
        aisShipData.setRepeatIdct(parseInteger(aisShipDataRecord.get("repeat_idct")));
        aisShipData.setNvgSttus(parseInteger(aisShipDataRecord.get("nvg_sttus")));
        aisShipData.setRot(parseInteger(aisShipDataRecord.get("rot")));
        aisShipData.setSog(parseDouble(aisShipDataRecord.get("sog")));
        aisShipData.setPosAccrcy(parseInteger(aisShipDataRecord.get("pos_accrcy")));
        aisShipData.setLoLo(parseDouble(aisShipDataRecord.get("lo_lo")));
        aisShipData.setLaLa(parseDouble(aisShipDataRecord.get("la_la")));
        aisShipData.setCog(parseDouble(aisShipDataRecord.get("cog")));
        aisShipData.setHeading(parseInteger(aisShipDataRecord.get("heading")));
        aisShipData.setSpeclManuvIdct(parseInteger(aisShipDataRecord.get("specl_manuv_idct")));
        aisShipData.setRaimFlag(parseBoolean(aisShipDataRecord.get("raim_flag")));
        aisShipData.setCmmncSttus(aisShipDataRecord.get("cmmnc_sttus"));
        aisShipData.setPacketIds(aisShipDataRecord.get("packet_id_s"));
        aisShipData.setAisClass(aisShipDataRecord.get("ais_class"));
        aisShipData.setRcordDt(parseDateTime(aisShipDataRecord.get("rcord_dt")));
        aisShipData.setCretDt(parseDateTime(aisShipDataRecord.get("cret_dt")));
        aisShipData.setClstrCount(parseInteger(aisShipDataRecord.get("clstr_count")));

        return aisShipData;
    }

    private static Integer parseInteger(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double parseDouble(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static LocalDateTime parseDateTime(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 여러 포맷을 위한 리스트
        List<DateTimeFormatter> formatters = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS"), // 마이크로초 포함
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // 밀리초 없을 경우
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        return null;
    }

    private static Boolean parseBoolean(String value) {
        return (value == null || value.isEmpty()) ? null : Boolean.parseBoolean(value);
    }

    public Ship buildShipList() {
        var ship = new Ship();
        var sourceType = AIS;
        ship.setShipId(sourceType+this.getMmsi());
        ship.setShipSourceType(sourceType);
        ship.setImoNo(this.getImoNo());
        ship.setMmsi(this.getMmsi());
        //
        ship.setCallSign(this.getCallSign());
        ship.setShipNm(this.getShipNm());
        //
        ship.setShipType(String.valueOf(this.getShipType()));
        //
        ship.setDestination(this.getDestination());
        ship.setEta(this.getEta());
        //
        ship.setMaxDraughtM(Double.valueOf(this.getMaxDraught()));
        //

        return ship;
    }

}
