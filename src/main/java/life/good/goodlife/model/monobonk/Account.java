package life.good.goodlife.model.monobonk;

import life.good.goodlife.statics.CurrencyCode;

public class Account {
    private String id;
    private int currencyCode;
    private String cashbackType;
    private int balance;
    private int creditLimit;
    private String[] maskedPan;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCashbackType() {
        return cashbackType;
    }

    public void setCashbackType(String cashbackType) {
        this.cashbackType = cashbackType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String[] getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String[] maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String[] balanceCount = String.valueOf(balance / 100.0).split(",");
        Balance balance = new Balance(Integer.parseInt(balanceCount[0]), Integer.parseInt(balanceCount[1]), CurrencyCode.getCurrencyNameByCurrencyCode(currencyCode));
        return "Баланс: " + balance.toString();
    }
}
