package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Account;
import life.good.goodlife.model.monobonk.UserInfo;
import life.good.goodlife.model.monobonk.UserMonobank;
import life.good.goodlife.repos.monobank.AccountRepository;
import life.good.goodlife.repos.monobank.UserMonobankRepository;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    private final UserMonobankRepository userMonobankRepository;
    private final AccountRepository accountRepository;

    public LoginService(UserMonobankRepository userMonobankRepository, AccountRepository accountRepository) {
        this.userMonobankRepository = userMonobankRepository;
        this.accountRepository = accountRepository;
    }

    public void createUser(UserMonobank userMonobank) {
        userMonobankRepository.save(userMonobank);
    }

    public void createAccount(Account account) {
         accountRepository.save(account);
    }

    public String getToken(Long userId) {
        UserMonobank user = userMonobankRepository.findByUserId(userId);
        if (user == null) {
            return null;
        }
        return user.getToken();
    }

    public UserInfo getUserInfo(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        String data = Request.get("https://api.monobank.ua/personal/client-info", headers);
        Gson gson = new Gson();
        return gson.fromJson(data, UserInfo.class);
    }
}
