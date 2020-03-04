package life.good.goodlife.model.monobonk;

public class Balance {
    private int main;
    private int cent;
    private String symbol;

    public Balance(int main, int cent, String symbol) {
        this.main = main;
        this.cent = cent;
        this.symbol = symbol;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public int getCent() {
        return cent;
    }

    public void setCent(int cent) {
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
