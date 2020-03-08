package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.bank.CurrencyCodeFactory;
import life.good.goodlife.model.monobonk.Account;
import life.good.goodlife.model.monobonk.Balance;
import life.good.goodlife.model.monobonk.UserInfo;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BalanceServiceImpl implements BalanceService {
    @Override
    public String balance(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        String data = Request.get("https://api.monobank.ua/personal/client-info", headers);
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(data, UserInfo.class);
        StringBuilder result = new StringBuilder("<b>Мой баланс: </b>\n\n");
        Account[] accounts = userInfo.getAccounts();
        for (Account account : accounts) {
            for (int i = 0; i < account.getMaskedPan().length; i++) {
                result.append("Карта: ").append(account.getMaskedPan()[i]).append("\n")
                        .append("Тип: ").append(account.getType()).append("\n")
                        .append("Баланс: ").append(getBalance(account.getBalance(), account.getCurrencyCode())).append("\n")
                        .append("Кредитный лимит: ").append(getBalance(account.getCreditLimit(), account.getCurrencyCode())).append("\n")
                        .append("\n------------------------------------------------------\n\n");
            }
        }
        return result.toString();
    }

    private Balance getBalance(Integer balance, Integer currencyCode) {
        String[] balances = new String[2];
        String balanceStr;

        balanceStr = balance.toString();
        if (balance > 99) {
            balances[0] = balanceStr.substring(balanceStr.length()-2);
            balances[1] = balanceStr.substring(0, balanceStr.length()-2);
        } else {
            balances[0] = balanceStr;
            balances[1] = "0";
        }
        return new Balance(balances[1], balances[0],
                CurrencyCodeFactory.getSymbolByCurrencyCode(currencyCode));
    }
}
