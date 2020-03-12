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

    public static Balance getBalanceFactory(Integer balance, Integer currencyCode) {
        String[] balances = new String[2];
        String balanceStr;

        balanceStr = balance.toString();
        if (Math.abs(balance) > 99) {
            balances[0] = balanceStr.substring(balanceStr.length()-2);
            balances[1] = balanceStr.substring(0, balanceStr.length()-2);
        } else {
            if (Math.abs(balance) > 9) {
                balances[0] = balanceStr;
                balances[1] = "0";
            } else {
                balances[0] = "0" + balanceStr;
                balances[1] = "0";
            }
        }
        return new Balance(balances[1], balances[0],
                CurrencyCodeFactory.getSymbolByCurrencyCode(currencyCode));
    }
}
