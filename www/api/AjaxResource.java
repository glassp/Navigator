import server.api.main.ApiResource;

import java.util.HashMap;
import java.util.Map;

public class AjaxResource extends ApiResource {

    @Override
    public String run(String... args) {
        try {
            if (args.length < 2)
                return null;
            Map<String, String> params = extractParams(args);
            int start = Integer.parseInt(params.get("start"));
            int dest = Integer.parseInt(params.get("dest"));

            //TODO: run query(start,dest) on graph and output route in */bin/out/route.out
            //TODO: use GeoJsonBuilder on */bin/out/route.out
            //TODO: output geo.json in webroot/api/.generated
            //TODO: return Path to geo.json

            return "PATH_TO_GEO_JSON";
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> extractParams(String... param) {
        String[] sArr = param[0].split("&");
        HashMap<String, String> params = new HashMap<>();
        for (String s : sArr) {
            String[] tmp = s.split("=");
            params.put(tmp[0], tmp[1]);
        }
        return params;
    }
}
