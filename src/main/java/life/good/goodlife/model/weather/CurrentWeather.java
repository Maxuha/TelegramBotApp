package life.good.goodlife.model.weather;

import com.google.gson.Gson;
import life.good.goodlife.model.declension.Declension;
import life.good.goodlife.statics.ParseCountry;
import life.good.goodlife.statics.Request;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CurrentWeather {
    private Coord coord;
    private Weather[] weather;
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private String dt;
    private Sys sys;
    private Integer timezone;
    private Integer id;
    private String name;
    private Integer cod;
    private String dt_txt;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String data = Request.get("https://ws3.morpher.ru/russian/declension?s=" + name + "&format=json");
        String city = gson.fromJson(data, Declension.class).getП();
        int hour = timezone / 3600;
        return "Погода в " + city + ", " + ParseCountry.getNameCountryByCode(sys.getCountry()) + "\n" +
                "Сейчас " + LocalDateTime.now(ZoneOffset.ofHours(hour)).format(DateTimeFormatter.ofPattern("HH:mm")) + "\n" +
                (Math.round(main.getTemp() - 273.15) > 0 ? "+" : "") + Math.round(main.getTemp() - 273.15) + "° " +
                getCodeEmoji(weather[0].getIcon()) + " " + weather[0].getDescription() +
                "\nОщущается как: " + (Math.round(main.getFeels_like() - 273.15) > 0 ? "+" : "") + Math.round(main.getFeels_like() - 273.15)
                + "°\n" + "🌬" + wind.getSpeed() + " м/c " + "💧" + main.getHumidity() + "%\n" +
                "Восход: " + LocalDateTime.ofEpochSecond(sys.getSunrise(), 0, ZoneOffset.ofHours(hour)).format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                "\nЗакат: " + LocalDateTime.ofEpochSecond(sys.getSunset(), 0, ZoneOffset.ofHours(hour)).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getCodeEmoji(String path) {
        path = path.substring(0, path.length()-1);
        String emojiCode = "";
        switch (path) {
            case "01": emojiCode = "☀️";
            break;
            case "02": emojiCode = "\uD83C\uDF24";
            break;
            case "03":
            case "04":
                emojiCode = "☁️";
            break;
            case "09": emojiCode = "\uD83C\uDF27";
            break;
            case "10": emojiCode = "\uD83C\uDF26";
            break;
            case "11": emojiCode = "⛈️";
            break;
            case "13": emojiCode = "\uD83C\uDF28";
            break;
            case "50": emojiCode = "\uD83C\uDF2B️";
            break;
            default: emojiCode = "\uD83E\uDD37\u200D♂️";
        }
        return emojiCode;
    }
}
