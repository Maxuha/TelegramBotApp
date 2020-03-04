package life.good.goodlife.model.monobonk;

import life.good.goodlife.statics.CurrencyCode;

public class Currency {
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private String date;
    private Float rateBuy;
    private Float rateSell;

    public Integer getCurrencyCodeA() {
        return currencyCodeA;
    }

    public void setCurrencyCodeA(Integer currencyCodeA) {
        this.currencyCodeA = currencyCodeA;
    }

    public Integer getCurrencyCodeB() {
        return currencyCodeB;
    }

    public void setCurrencyCodeB(Integer currencyCodeB) {
        this.currencyCodeB = currencyCodeB;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(Float rateBuy) {
        this.rateBuy = rateBuy;
    }

    public Float getRateSell() {
        return rateSell;
    }

    public void setRateSell(Float rateSell) {
        this.rateSell = rateSell;
    }

    @Override
    public String toString() {
        String flag = CurrencyCode.getFlagByCurrencyCode(currencyCodeA);
        if (flag != null) {
            return flag + "    " + String.format("%.2f", rateBuy) + "         " + String.format("%.2f", rateSell);
        } else
            return "";
    }
}
