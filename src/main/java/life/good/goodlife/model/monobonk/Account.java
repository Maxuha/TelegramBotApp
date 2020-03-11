package life.good.goodlife.model.monobonk;

import life.good.goodlife.model.bank.CurrencyCodeFactory;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "monobank_account")
public class Account extends MaskedPan {
    @Id
    private String id;

    @Column(name = "currency_code")
    private Integer currencyCode;

    @Column(name = "cashback_type")
    private String cashbackType;

    @Transient
    private Integer balance;

    @Transient
    private Integer creditLimit;

    @Type(type = "string-array")
    @Column(
            name = "masked_pan",
            columnDefinition = "text[]"
    )
    private String[] maskedPan;

    @Column
    private String type;

    @Column(name = "client_id")
    private String clientId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCashbackType() {
        return cashbackType;
    }

    public void setCashbackType(String cashbackType) {
        this.cashbackType = cashbackType;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
