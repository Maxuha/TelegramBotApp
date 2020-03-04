package life.good.goodlife.model.monobonk;

import life.good.goodlife.statics.CurrencyCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Statement {
    private String id;
    private long time;
    private String description;
    private int mcc;
    private int amount;
    private int operationAmount;
    private int currencyCode;
    private int commissionRate;
    private int cashbackAmount;
    private int balance;
    private boolean hold;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(int operationAmount) {
        this.operationAmount = operationAmount;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(int commissionRate) {
        this.commissionRate = commissionRate;
    }

    public int getCashbackAmount() {
        return cashbackAmount;
    }

    public void setCashbackAmount(int cashbackAmount) {
        this.cashbackAmount = cashbackAmount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    @Override
    public String toString() {
        float value = operationAmount / 100.0f;
        String[] values = String.valueOf(value).split("\\.");
        Balance operationAmountCount = new Balance(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
                CurrencyCode.getSymbolByCurrencyCode(currencyCode));
        value = commissionRate / 100.0f;
        values = String.valueOf(value).split("\\.");
        Balance commissionRateCount = new Balance(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
                CurrencyCode.getSymbolByCurrencyCode(currencyCode));
        value = balance / 100.0f;
        values = String.valueOf(value).split("\\.");
        Balance balanceCount = new Balance(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
                CurrencyCode.getSymbolByCurrencyCode(currencyCode));
        value = cashbackAmount / 100.0f;
        values = String.valueOf(value).split("\\.");
        Balance cashbackAmountCount = new Balance(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
                CurrencyCode.getSymbolByCurrencyCode(currencyCode));

        return description + "\n"
                + LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")) + "\n"
                + operationAmountCount + "\nКомиссия: " + commissionRateCount + "\nОстаток: " + balanceCount + "\n" + cashbackAmountCount;
    }
}
