package com.doublep.vrssapi.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Route {
    private String type;
    private List<Feature> features;

    @Data
    public static class Feature {
        private String type;
        private Geometry geometry;
        private Property properties;
    }

    @Data
    public static class Geometry {
        private String type;
        private List<List<Double>> coordinates;
    }

    @Data
    public static class Property {
        private Double distanceECA;
        private Double hours;
        private Boolean ECA;
        private Double distance;
        private Integer startNo;
        private Integer arvlNo;
        private Double maxSpeed;
        private Double seaStay;
        private Double distanceHRA;
        private Double coursHours;
        private Double portStay;
        private Boolean HRA;
        private LocalDateTime arvlDateTime;
        private LocalDateTime startDt;
        private List<PlanPoint> planPoints;
    }

    @Data
    public static class PlanPoint {
        private LocalDateTime dateTIme;
        private Double latitude;
        private Double speed;
        private Double longitude;
    }
}
