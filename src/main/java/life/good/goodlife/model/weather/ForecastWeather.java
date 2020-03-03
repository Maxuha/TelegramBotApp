package life.good.goodlife.model.weather;

import com.google.gson.Gson;
import life.good.goodlife.model.date.DAYS;
import life.good.goodlife.model.date.DayOfWeekToDays;
import life.good.goodlife.model.date.MONTH;
import life.good.goodlife.model.date.MonthToMonth;
import life.good.goodlife.model.declension.Declension;
import life.good.goodlife.statics.ParseCountry;
import life.good.goodlife.statics.Request;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ForecastWeather {
    private String cod;
    private Integer message;
    private Integer cnt;
    private CurrentWeather[] list;
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public CurrentWeather[] getList() {
        return list;
    }

    public void setList(CurrentWeather[] list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String data = Request.get("https://ws3.morpher.ru/russian/declension?s=" + city.getName() + "&format=json", city.getName());
        String cityName;
        if (data.charAt(0) == '{') {
            cityName = gson.fromJson(data, Declension.class).getП();
        } else {
            cityName = data;
        }
        return "Погода на 5️⃣ дней в " + cityName + ", " + ParseCountry.getNameCountryByCode(city.getCountry()) + "\n" +
                getDataInDays();
    }

    private String getDataInDays() {
        StringBuilder result = new StringBuilder();
        float min_temp = list[0].getMain().getTemp();
        float max_temp = list[0].getMain().getTemp();
        float avg_wind = list[0].getWind().getSpeed();
        float avg_humidity = list[0].getMain().getHumidity();
        StringBuilder image = new StringBuilder();
        StringBuilder description = new StringBuilder();
        int iteration = 1;
        LocalDateTime dateTime = LocalDateTime.parse(list[0].getDt_txt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime dateTimeCurrent = dateTime;
        MONTH month = null;
        DAYS days = null;
        int daysOfMonth = 0;
        for (int i = 1; i < list.length + 1; i++) {
            if (i < list.length) {
                dateTime = LocalDateTime.parse(list[i].getDt_txt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } else {
                dateTimeCurrent = dateTimeCurrent.plusDays(1);
            }

            if (dateTime.getHour() >= 21 || dateTime.getDayOfMonth() == dateTimeCurrent.getDayOfMonth()) {
                if (min_temp > list[i].getMain().getTemp()) {
                    min_temp = list[i].getMain().getTemp();
                }
                if (max_temp < list[i].getMain().getTemp()) {
                    max_temp = list[i].getMain().getTemp();
                }
                avg_wind += list[i].getWind().getSpeed();
                avg_humidity += list[i].getMain().getHumidity();
                description.append(list[i].getWeather()[0].getDescription()).append(" ");
                image.append(list[i].getWeather()[0].getIcon()).append(" ");
                month = MonthToMonth.getMonthByMonth(dateTime.getMonth());
                days = DayOfWeekToDays.getDaysByDayOfWeek(dateTime.getDayOfWeek());
                daysOfMonth = dateTime.getDayOfMonth();
                iteration++;
            } else {
                assert month != null;
                String monthText = month.name().toLowerCase().trim();
                Gson gson = new Gson();
                String data = Request.get("https://ws3.morpher.ru/russian/declension?s=" + monthText + "&format=json", monthText);
                if (data.charAt(0) == '{') {
                    monthText = gson.fromJson(data, Declension.class).getР();
                } else {
                    monthText = data;
                }
                if (DayOfWeekToDays.getDaysByDayOfWeek(LocalDateTime.now().getDayOfWeek()).equals(days)) {
                    result.append(DAYS.Сегодня).append(", ")
                            .append(daysOfMonth).append(" ").append(monthText).append("\n");
                } else {
                    result.append(days).append(", ")
                            .append(daysOfMonth).append(" ").append(monthText).append("\n");
                }
                avg_wind /= iteration;
                avg_humidity /= iteration;
                int hourZone = city.getTimezone() / 3600;
                result.append("Днём: ").append((Math.round(max_temp - 273.15) > 0 ? "+" : "")).append(Math.round(max_temp - 273.15)).append("°\n")
                        .append("Ночью: ").append((Math.round(min_temp - 273.15) > 0 ? "+" : "")).append(Math.round(min_temp - 273.15)).append("°\n")
                        .append(list[i - 1].getCodeEmoji(max(image.toString().trim()))).append(" ").append(max(description.toString().trim())).append("\n")
                        .append("🌬 ").append(String.format("%.2f", avg_wind)).append(" м/c ")
                        .append("💧 ").append(Math.round(avg_humidity)).append("%").append("\nВосход: ").append(LocalDateTime.ofEpochSecond(city.getSunrise(),
                        0, ZoneOffset.ofHours(hourZone)).format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                        .append("\nЗакат: " + LocalDateTime.ofEpochSecond(city.getSunset(), 0, ZoneOffset.ofHours(hourZone))
                                .format(DateTimeFormatter.ofPattern("HH:mm:ss"))).append("::");
                iteration = 1;
                if (i < list.length) {
                    min_temp = list[i].getMain().getTemp();
                    max_temp = list[i].getMain().getTemp();
                    avg_wind = list[i].getWind().getSpeed();
                    avg_humidity = list[i].getMain().getHumidity();
                    dateTimeCurrent = dateTime;
                }
            }
        }
        return result.toString();
    }

    private String max(String word) {
        String[] words = word.split(" ");
        int index = 0;
        int count = 0;
        int countMax = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (words[i].equals(words[j])) {
                    count++;
                }
            }
            if (countMax < count) {
                countMax = count;
                index = i;
            }
        }
        return words[index];
    }
}
