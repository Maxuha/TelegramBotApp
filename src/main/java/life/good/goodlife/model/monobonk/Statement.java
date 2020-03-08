package life.good.goodlife.model.monobonk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column
    private Integer operationAmount;

    @Column
    private Integer currencyCode;

    @Column
    private Integer commissionRate;

    @Column
    private Integer cashbackAmount;

    @Column
    private Integer balance;

    @Column
    private Boolean hold;

    private Balance balanceCount;

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
