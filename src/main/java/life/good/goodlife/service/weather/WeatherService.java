package life.good.goodlife.service.weather;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Location;
import life.good.goodlife.model.weather.CurrentWeather;
import life.good.goodlife.model.weather.ForecastWeather;
import life.good.goodlife.statics.Request;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private final Environment environment;
    private String token;

    public WeatherService(Environment environment) {
        token = environment.getProperty("weather.openweather.token");
        this.environment = environment;
    }

    public String weatherByCity(String city) {
        String data = Request.get("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + token + "&lang=ru");
        Gson gson = new Gson();
        CurrentWeather currentWeather = gson.fromJson(data, CurrentWeather.class);
        return currentWeather.toString();
    }

    public String weather(Location location) {
        String data = Request.get("http://api.openweathermap.org/data/2.5/weather?lat=" + location.latitude() + "&lon=" + location.longitude() + "&appid=" + token + "&lang=ru");
        Gson gson = new Gson();
        CurrentWeather currentWeather = gson.fromJson(data, CurrentWeather.class);
        return currentWeather.toString();
    }

    public String[] weatherFiveByCity(String city) {
        String data = Request.get("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + token + "&lang=ru");
        Gson gson = new Gson();
        ForecastWeather forecastWeather = gson.fromJson(data, ForecastWeather.class);
        data = forecastWeather.toString();
        return data.split("::");
    }
}
