package life.good.goodlife.model.monobonk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "monobank_currency")
public class Currency {
    @Column
    private Integer currencyCodeA;

    @Column
    private Integer currencyCodeB;

    @Id
    private Integer date;

    @Column
    private Float rateBuy;

    @Column
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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
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
}
