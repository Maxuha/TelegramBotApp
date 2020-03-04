package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Currency;
import life.good.goodlife.model.monobonk.UserInfo;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BalanceService {
    private String token;

    public BalanceService() {
        token = System.getenv().get("bank_monobank_token");
    }

    public String balance() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        String data = Request.get("https://api.monobank.ua/personal/client-info", headers);
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(data, UserInfo.class);
        String msg = userInfo.toString();
        return msg;
    }
}