package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.bank.CurrencyCodeFactory;
import life.good.goodlife.model.monobonk.Currency;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Override
    public String currency(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        String data = Request.get("https://api.monobank.ua/bank/currency", headers);
        Gson gson = new Gson();
        Currency[] currencies = gson.fromJson(data, Currency[].class);
        StringBuilder result = new StringBuilder("<b>Курс валют</b>\n            Покупка     Продажа\n");
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
}
