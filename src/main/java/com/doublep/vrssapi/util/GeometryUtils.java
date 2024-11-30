package com.doublep.vrssapi.util;

import com.doublep.vrssapi.advisor.exception.CustomJsonProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeometryUtils {

    private final ObjectMapper objectMapper0;
    private static ObjectMapper objectMapper;
    private static final double EARTH_RADIUS_KM = 6371.0;

    @PostConstruct
    public void init() {
        objectMapper = this.objectMapper0;
    }

    public static String toLineString(String geoData) {
        var lineStringCoordinates = getCoordinates(geoData);
        return buildLineStringGeoJson(lineStringCoordinates);
    }

    public static List<List<Double>> getCoordinates(String geoData) {
        try {
            // Step 1: Parse GeoJSON
            var rootNode = objectMapper.readTree(geoData);

            var coordinates = rootNode.get("coordinates");
            var lineStringCoordinates = new ArrayList<List<Double>>();

            // Step 2: Extract all coordinates from MultiPolygon
            for (var polygonNode : coordinates) {
                for (var ringNode : polygonNode) {
                    for (var coordNode : ringNode) {
                        double lon = coordNode.get(0).asDouble();
                        double lat = coordNode.get(1).asDouble();
                        var point = new ArrayList<Double>();
                        point.add(lon);
                        point.add(lat);
                        lineStringCoordinates.add(point);
                    }
                }
            }

            return lineStringCoordinates;
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred ::: {}", geoData);
            throw new CustomJsonProcessingException();
        }
    }

    public static double calculateTotalDistance(String geoData) {
        var coordinates = getCoordinates(geoData);
        var coordinateList = coordinates.stream()
                .map(coordinate -> coordinate.stream()
                        .mapToDouble(Double::doubleValue)
                        .toArray())
                .toList();
        double totalDistance = 0.0;

        for (int i = 0; i < coordinates.size() - 1; i++) {
            double[] point1 = coordinateList.get(i);
            double[] point2 = coordinateList.get(i + 1);
            totalDistance += calculateHaversineDistance(point1, point2);
        }

        return totalDistance;
    }

    private static String buildLineStringGeoJson(List<List<Double>> coordinates) {
        StringBuilder geoJsonBuilder = new StringBuilder();
        geoJsonBuilder.append("{");
        geoJsonBuilder.append("\"type\": \"LineString\",");
        geoJsonBuilder.append("\"coordinates\": [");

        for (int i = 0; i < coordinates.size(); i++) {
            List<Double> coord = coordinates.get(i);
            geoJsonBuilder.append("[").append(coord.get(0)).append(",").append(coord.get(1)).append("]");
            if (i < coordinates.size() - 1) {
                geoJsonBuilder.append(",");
            }
        }

        geoJsonBuilder.append("]");
        geoJsonBuilder.append("}");
        return geoJsonBuilder.toString();
    }

    public static double calculateHaversineDistance(double[] point1, double[] point2) {
        double lat1 = Math.toRadians(point1[1]);
        double lon1 = Math.toRadians(point1[0]);
        double lat2 = Math.toRadians(point2[1]);
        double lon2 = Math.toRadians(point2[0]);

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
