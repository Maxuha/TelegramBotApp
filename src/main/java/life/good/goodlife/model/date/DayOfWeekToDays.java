package life.good.goodlife.model.date;

import java.time.DayOfWeek;

public class DayOfWeekToDays {
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
}
