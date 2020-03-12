package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Account;
import life.good.goodlife.model.monobonk.UserInfo;
import life.good.goodlife.repos.monobank.AccountRepository;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BalanceServiceImpl implements BalanceService {
    private final AccountRepository accountRepository;

    public BalanceServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserInfo balanceApi(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        String data = Request.get("https://api.monobank.ua/personal/client-info", headers);
        Gson gson = new Gson();
        return gson.fromJson(data, UserInfo.class);
    }

    @Override
    public Account getBalance(String[] cart) {
        return accountRepository.findByMaskedPan(cart);
    }
}
