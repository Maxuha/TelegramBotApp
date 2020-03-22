package life.good.goodlife.service.monobank;

import life.good.goodlife.model.monobonk.Currency;

public interface CurrencyService {
    String currency();
    void updateCurrency();
    Currency[] getCurrency();
}
