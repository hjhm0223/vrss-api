package com.doublep.vrssapi.model.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipRouteRequest {

    @Builder.Default
    private Boolean debug = false;
    @Builder.Default
    private Integer maxSpeed = 17;
    @Builder.Default
    private Option options = Option.builder().build();
    @Builder.Default
    private String routeMethod = "distance";
    private String shipTypeBySize;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime startDateTime;

    private List<Point> wayPoints;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        @Builder.Default
        private Integer eca = 2;
        @Builder.Default
        private Integer hra = 2;
        @Builder.Default
        private Integer jwc = 2;
        @Builder.Default
        private Integer cyclone = 2;
        private List<String> avoid;
        @Builder.Default
        private String speedType = "average";
        @Builder.Default
        private Integer frequencyPercentage = 50;
        @Builder.Default
        private Integer validAreaAngle = 0;
        @Builder.Default
        private Integer startNodeCount = 5;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Point {
        private Double latitude;
        private Double longitude;
        private String name;
        private Integer no;
        private Integer portStayHours;
        private Integer seaStayHours;
    }
}
