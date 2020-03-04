package life.good.goodlife.model.monobonk;

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
        String flag = getFlagByCurrencyCode(currencyCodeA);
        if (flag != null) {
            return flag + "    " + String.format("%.2f", rateBuy) + "         " + String.format("%.2f", rateSell);
        } else
            return "";
    }

    private String getFlagByCurrencyCode(int code) {
        String result;
        switch (code) {
            case 980: result = "\uD83C\uDDFA\uD83C\uDDE6";
            break;
            case 840: result = "\uD83C\uDDFA\uD83C\uDDF8";
            break;
            case 978: result = "\uD83C\uDDEA\uD83C\uDDFA";
            break;
            case 643: result = "\uD83C\uDDF7\uD83C\uDDFA";
            break;
            case 985: result = "\uD83C\uDDF5\uD83C\uDDF1";
            break;
            default: result = null;
        }
        return result;
    }
}
