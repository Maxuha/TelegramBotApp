package life.good.goodlife.service.weather;

public interface WeatherService {
    String weatherByCity(String city);
    String weather(Float lat, Float lng, Long userId);
    String[] weatherFiveByCity(String city);
}
