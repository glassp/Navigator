package geoJson;

public class GeoJsonBuilder {

    private StringBuilder constructGeo;

    public GeoJsonBuilder() {
        constructGeo = new StringBuilder();
        constructGeo.append("{\r\n").append("\"type\": \"Feature\",\r\n").append("\"geometry\": {\r\n")
                .append("    \"type\": \"LineString\",\r\n").append("    \"coordinates\": [\r\n");
    }

    public static String run(double[] latitudes, double[] longitudes) {
        GeoJsonBuilder builder = new GeoJsonBuilder();
        try {
            for (int i = 0; i < latitudes.length; i++) {
                builder.addGeo(latitudes[i], longitudes[i]);
            }
        } catch (IndexOutOfBoundsException e) {
            //ignored
        }
        return builder.build();
    }

    public void addGeo(double lat, double lng) {
        constructGeo.append("[").append(lng).append(", ").append(lat).append("]");
    }

    private void appendFileEnd() {
        constructGeo.append("\n]\r\n").append("}\r\n").append("}");
    }

    public String build() {
        appendFileEnd();
        return constructGeo.toString();
    }
}

