package life.good.goodlife.model.monobonk;

import javax.persistence.*;

@Entity
@Table(name = "monobank_statement")
public class Statement {
    @Id
    private String id;

    @Column
    private Long time;

    @Column
    private String description;

    @Column
    private String comment;

    @Column
    private Integer mcc;

    @Column
    private Integer amount;

    @Column(name = "operation_amount")
    private Integer operationAmount;

    @Column(name = "currency_code")
    private Integer currencyCode;

    @Column(name = "commission_rate")
    private Integer commissionRate;

    @Column(name = "cashback_amount")
    private Integer cashbackAmount;

    @Column
    private Integer balance;

    @Column
    private Boolean hold;

    @Transient
    private Balance balanceCount;

    @Column(name = "account_id")
    private String accountId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMcc() {
        return mcc;
    }

    public void setMcc(Integer mcc) {
        this.mcc = mcc;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(Integer operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Integer getCashbackAmount() {
        return cashbackAmount;
    }

    public void setCashbackAmount(Integer cashbackAmount) {
        this.cashbackAmount = cashbackAmount;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Boolean isHold() {
        return hold;
    }

    public void setHold(Boolean hold) {
        this.hold = hold;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Balance getBalanceCount() {
        return balanceCount;
    }

    public void setBalanceCount(Balance balanceCount) {
        this.balanceCount = balanceCount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", mcc=" + mcc +
                ", amount=" + amount +
                ", operationAmount=" + operationAmount +
                ", currencyCode=" + currencyCode +
                ", commissionRate=" + commissionRate +
                ", cashbackAmount=" + cashbackAmount +
                ", balance=" + balance +
                ", hold=" + hold +
                ", balanceCount=" + balanceCount +
                '}';
    }
}
