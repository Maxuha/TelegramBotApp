package life.good.goodlife.model.monobonk;

public class Balance {
    private String main;
    private String cent;
    private String symbol;

    public Balance(String main, String cent, String symbol) {
        this.main = main;
        this.cent = cent;
        this.symbol = symbol;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getCent() {
        return cent;
    }

    public void setCent(String cent) {
        this.cent = cent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return main + "." + cent + " " + symbol;
    }
}
