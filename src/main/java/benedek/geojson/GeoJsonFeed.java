package benedek.geojson;

import java.util.List;

public class GeoJsonFeed {
    List<Feature> features;

    public static class Feature {
        FeatureProperties properties;
        Geometry geometry;

        public String getPlace() {
            return properties.place;
        }

        public long getTime() {
            return properties.time;
        }

        public double getMagnitude() {
            return properties.mag;
        }
    }

    public static class FeatureProperties {
        double mag;
        String place;
        long time;
    }

    public static class Geometry {
        List<Double> coordinates;
    }

}

