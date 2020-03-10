package life.good.goodlife.model.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayOfWeekToDaysFactory {
    public static DAYS getDaysByDayOfWeek(DayOfWeek dayOfWeek) {
        DAYS days;
        switch (dayOfWeek) {
            case MONDAY: days = DAYS.Пн;
                break;
            case TUESDAY: days = DAYS.Вт;
                break;
            case WEDNESDAY: days = DAYS.Ср;
                break;
            case THURSDAY: days = DAYS.Чт;
                break;
            case FRIDAY: days = DAYS.Пт;
                break;
            case SATURDAY: days = DAYS.Сб;
                break;
            case SUNDAY: days = DAYS.Вс;
                break;
            default: days = null;
        }
        return days;
    }

    public static DAYS getDaysByDate(LocalDate date) {
        DAYS days = null;
        if (LocalDate.now().equals(date)) {
            days = DAYS.Сегодня;
        } else if (LocalDate.now().minusDays(1).equals(date)) {
            days = DAYS.Вчера;
        } else if (LocalDate.now().minusDays(2).equals(date)) {
            days = DAYS.Позавчера;
        }
        return days;
    }
}
