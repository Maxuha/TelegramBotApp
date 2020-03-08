package life.good.goodlife.model.date;

import java.time.Month;

public class MonthToMonthFactory {
    public static MONTH getMonthByMonth(Month month) {
        MONTH monthRu;
        switch (month) {
            case JANUARY: monthRu = MONTH.Январь;
                break;
            case FEBRUARY: monthRu = MONTH.Февраль;
                break;
            case MARCH: monthRu = MONTH.Март;
                break;
            case APRIL: monthRu = MONTH.Апрель;
                break;
            case MAY: monthRu = MONTH.Май;
                break;
            case JUNE: monthRu = MONTH.Июнь;
                break;
            case JULY: monthRu = MONTH.Июль;
                break;
            case AUGUST: monthRu = MONTH.Август;
                break;
            case SEPTEMBER: monthRu = MONTH.Сентябрь;
                break;
            case OCTOBER: monthRu = MONTH.Октябрь;
                break;
            case NOVEMBER: monthRu = MONTH.Ноябрь;
                break;
            case DECEMBER: monthRu = MONTH.Декабрь;
                break;
            default: monthRu = null;
        }
        return monthRu;
    }
}
