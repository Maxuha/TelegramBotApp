package life.good.goodlife.service.map;

import com.google.gson.Gson;
import life.good.goodlife.model.map.GeoCode;
import life.good.goodlife.model.map.GeoCodeMain;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeoCodeService {
    private String token;

    public GeoCodeService() {
        token = System.getenv().get("google_map_token");
    }

    public String getInfoPlace(String place) {
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/x-www-form-urlencoded");
        String data = Request.get("https://maps.googleapis.com/maps/api/geocode/json?address=" + place + "&key=" + token + "&language=ru", headers);
        Gson gson = new Gson();
        GeoCodeMain geoCode = gson.fromJson(data, GeoCodeMain.class);
        return place + ", " + geoCode;
    }
}
