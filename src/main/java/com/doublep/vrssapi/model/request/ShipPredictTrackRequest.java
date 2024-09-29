package com.doublep.vrssapi.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShipPredictTrackRequest {

    private Boolean debug;
    private Integer maxSpeed;
    private Option options;
    private String routeMethod;
    private String shipTypeBySize;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime startDateTime;

    private List<Point> wayPoints;

    @Data
    public static class Option {
        private Integer eca;
        private Integer hra;
        private Integer jwc;
        private Integer cyclone;
        private List<String> avoid;
        private String speedType;
        private Integer frequencyPercentage;
        private Integer validAreaAngle;
        private Integer startNodeCount;
    }

    @Data
    public static class Point {
        private Double latitude;
        private Double longitude;
        private String name;
        private Integer no;
        private Integer portStayHours;
        private Integer seaStayHours;
    }
}
