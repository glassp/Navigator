package geoJson;

public class GeoJsonBuilder {

    private StringBuilder constructGeo;

    public static String LINE_STRING = "LineString";
    public static String POINT = "MultiPoint";
    private boolean endFlag = false;

    public GeoJsonBuilder(String type) {
        constructGeo = new StringBuilder();
        constructGeo.append("{\r\n").
                append("    \"type\": \"Feature\",\r\n")
                .append("    \"properties\": {\r\n")
                .append("        \"name\": \"Dijkstra Navigator GeoJson\"\r\n")
                .append("        },\r\n")
                .append("    \"geometry\": {\r\n")
                .append("    \"type\": \"").append(type).append("\",\r\n")
                .append("        \"coordinates\": [\r\n");
    }

    public static String run(double[] latitudes, double[] longitudes, String type) {
        GeoJsonBuilder builder = new GeoJsonBuilder(type);
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
        if (endFlag)
            constructGeo.append(",\r\n");
        endFlag = true;
        constructGeo.append("      [").append(lng).append(", ").append(lat).append("]");
    }

    private void appendFileEnd() {
        constructGeo.append("\n        ]\r\n").append("    }\r\n").append("}");
    }

    public String build() {
        appendFileEnd();
        return constructGeo.toString();
    }
}

