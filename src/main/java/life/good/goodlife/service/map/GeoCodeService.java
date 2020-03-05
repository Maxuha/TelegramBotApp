package life.good.goodlife.service.map;

import com.google.gson.Gson;
import life.good.goodlife.model.map.GeoCode;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GeoCodeService {
    private String token;

    public GeoCodeService() {
        token = System.getenv().get("google_map_token");
    }

    public String getInfoPlace(String place) {
        String data = Request.get("https://maps.googleapis.com/maps/api/geocode/json?address=" + place + "&key=" + token + "&language=ru");
        System.out.println(data);
        Gson gson = new Gson();
        GeoCode[] geoCode = gson.fromJson(data, GeoCode[].class);
        return place + ", " + Arrays.toString(geoCode);
    }
}
