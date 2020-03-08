package life.good.goodlife.service.map;

import com.google.gson.Gson;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.map.GeoCodeMain;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GeoCodeServiceImpl implements GeoCodeService {
    private String token;

    public GeoCodeServiceImpl() {
        token = System.getenv().get("google_map_token");
    }

    @Override
    public GeoCodeMain getInfoPlace(String place) {
        try {
            place = URLEncoder.encode(place, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String data = Request.get("https://maps.googleapis.com/maps/api/geocode/json?address=" + place + "&key=" + token
                + "&language=ru");
        Gson gson = new Gson();

        return gson.fromJson(data, GeoCodeMain.class);
    }
}
