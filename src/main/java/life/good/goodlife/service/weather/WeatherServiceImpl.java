package life.good.goodlife.service.weather;

import com.google.gson.Gson;
import life.good.goodlife.component.LocationComponent;
import life.good.goodlife.model.map.LocationType;
import life.good.goodlife.model.weather.CurrentWeather;
import life.good.goodlife.model.weather.ForecastWeather;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {
    private String token;
    private final LocationComponent locationComponent;
    public WeatherServiceImpl(LocationComponent locationComponent) {
        this.locationComponent = locationComponent;
        token = System.getenv().get("weather_openweather_token");
    }

    @Override
    public String weatherByCity(String city) {
        String data = Request.get("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + token + "&lang=ru");
        Gson gson = new Gson();
        CurrentWeather currentWeather = gson.fromJson(data, CurrentWeather.class);
        return currentWeather.toString();
    }

    @Override
    public String weather(Float lat, Float lng, Long userId) {
        String data = Request.get("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng + "&appid=" + token + "&lang=ru");
        locationComponent.createNewLocation(userId, lat, lng, LocationType.CURRENT);
        Gson gson = new Gson();
        CurrentWeather currentWeather = gson.fromJson(data, CurrentWeather.class);
        return currentWeather.toString();
    }

    @Override
    public String[] weatherFiveByCity(String city) {
        String data = Request.get("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + token + "&lang=ru");
        Gson gson = new Gson();
        ForecastWeather forecastWeather = gson.fromJson(data, ForecastWeather.class);
        data = forecastWeather.toString();
        return data.split("::");
    }
}
