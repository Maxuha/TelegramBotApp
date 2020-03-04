package life.good.goodlife.model.monobonk;

import life.good.goodlife.statics.CurrencyCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Statement {
    private String id;
    private long time;
    private String description;
    private String comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        float value = operationAmount / 100.0f;
        String[] values = String.format("%.2f", value).split("\\.");
        Balance operationAmountCount = new Balance(values[0], values[1],
                CurrencyCode.getSymbolByCurrencyCode(currencyCode));
        value = commissionRate / 100.0f;
        values = String.format("%.2f", value).split("\\.");
        Balance commissionRateCount = new Balance(values[0], values[1],
                CurrencyCode.getSymbolByCurrencyCode(currencyCode));
        value = balance / 100.0f;
        values = String.format("%.2f", value).split("\\.");
        Balance balanceCount = new Balance(values[0], values[1],
                CurrencyCode.getSymbolByCurrencyCode(currencyCode));
        value = cashbackAmount / 100.0f;
        values = String.format("%.2f", value).split("\\.");
        Balance cashbackAmountCount = new Balance(values[0], values[1],
                CurrencyCode.getSymbolByCurrencyCode(currencyCode));
        return description + "\n" + comment + '\n'
                + LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.ofHours(2)).format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", new Locale("ru"))) + "\n"
                + operationAmountCount + "\nКомиссия: " + commissionRateCount + "\nОстаток: " + balanceCount + "\nКешбек: " + cashbackAmountCount;
    }
}
