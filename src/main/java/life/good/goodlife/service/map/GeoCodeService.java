package life.good.goodlife.service.map;

import com.google.gson.Gson;
import life.good.goodlife.model.map.GeoCode;
import life.good.goodlife.model.map.GeoCodeMain;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        try {
            place = URLEncoder.encode(place, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String data = Request.get("https://maps.googleapis.com/maps/api/geocode/json?address=" + place + "&key=" + token
                + "&language=ru");
        Gson gson = new Gson();
        GeoCodeMain geoCode = gson.fromJson(data, GeoCodeMain.class);
        return geoCode.toString();
    }
}
