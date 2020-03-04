package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Currency;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {
    private String token;

    public CurrencyService() {
        token = System.getenv().get("bank_monobank_token");
    }

    public String currency() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        String data = Request.get("https://api.monobank.ua/bank/currency", headers);
        Gson gson = new Gson();
        Currency[] currencies = gson.fromJson(data, Currency[].class);
        StringBuilder result = new StringBuilder("<b>Курс валют</b>         Покупка     Продажа");
        for (Currency currency : currencies) {
            result.append(currency.toString()).append("\n");
        }
        return result.toString();
    }
}
