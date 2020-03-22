package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.CurrencyCodeFactory;
import life.good.goodlife.model.monobonk.Currency;
import life.good.goodlife.repos.monobank.CurrencyRepository;
import life.good.goodlife.statics.Request;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public String currency() {
        String data = Request.get("https://api.monobank.ua/bank/currency");
        Gson gson = new Gson();
        Currency[] currencies = gson.fromJson(data, Currency[].class);
        StringBuilder result = new StringBuilder("<b>Курс валют</b>\n\n            Покупка     Продажа\n");
        String flag;
        for (Currency currency : currencies) {
            flag = CurrencyCodeFactory.getFlagByCurrencyCode(currency.getCurrencyCodeA());
            if (flag != null) {
                flag += "    " + String.format("%.2f", currency.getRateBuy()) + "         " + String.format("%.2f", currency.getRateSell());
            } else {
                flag = "";
            }
            result.append(flag).append("\n");
        }
        return result.toString();
    }

    @Scheduled(fixedRate = 300000)
    @Override
    public void updateCurrency() {
        String data = Request.get("https://api.monobank.ua/bank/currency");
        Gson gson = new Gson();
        Currency[] currencies = gson.fromJson(data, Currency[].class);
        Currency tempCurrency;
        String flag;
        for (Currency currency : currencies) {
            flag = CurrencyCodeFactory.getFlagByCurrencyCode(currency.getCurrencyCodeA());
            if (flag != null) {
                tempCurrency = currencyRepository.findFirstByCurrencyCodeAAndCurrencyCodeBOrderByDateAsc(currency.getCurrencyCodeA(), currency.getCurrencyCodeB());
                if (tempCurrency != null) {
                    if (!currency.getDate().equals(tempCurrency.getDate())) {
                        currencyRepository.save(currency);
                    }
                } else {
                    currencyRepository.save(currency);
                }
            }
        }
    }

    @Override
    public Currency[] getCurrency() {
        Currency[] currencies = new Currency[5];
        Integer[] from = new Integer[] { 840, 978, 643, 978, 985 };
        Integer[] to = new Integer[] { 980, 980, 980, 840, 980 };
        for (int i = 0; i < from.length; i++) {
            currencies[i] = currencyRepository.findFirstByCurrencyCodeAAndCurrencyCodeBOrderByDateAsc(from[i], to[i]);
        }
        return currencies;
    }
}
