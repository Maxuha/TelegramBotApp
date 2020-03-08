package life.good.goodlife.service.weather;

import com.pengrad.telegrambot.model.Location;

public interface WeatherService {
    String weatherByCity(String city);
    String weather(Location location, Long userId);
    String[] weatherFiveByCity(String city);
}
