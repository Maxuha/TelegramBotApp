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
public class LoginServiceImpl implements LoginService {
    private final UserMonobankRepository userMonobankRepository;
    private final AccountRepository accountRepository;

    public LoginServiceImpl(UserMonobankRepository userMonobankRepository, AccountRepository accountRepository) {
        this.userMonobankRepository = userMonobankRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void createUser(UserMonobank userMonobank) {
        userMonobankRepository.save(userMonobank);
    }

    @Override
    public void createAccount(Account account) {
         accountRepository.save(account);
    }



    @Override
    public String getToken(Long userId) {
        UserMonobank user = userMonobankRepository.findByUserId(userId);
        if (user == null) {
            return null;
        }
        return user.getToken();
    }

    @Override
    public UserInfo getUserInfo(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        String data = Request.get("https://api.monobank.ua/personal/client-info", headers);
        Gson gson = new Gson();
        return gson.fromJson(data, UserInfo.class);
    }

    @Override
    public UserMonobank getByUserId(Long userId) {
        return userMonobankRepository.findFirstByUserId(userId);
    }

    @Override
    public UserMonobank getByClientId(String clientId) {
        return userMonobankRepository.findFirstByClientId(clientId);
    }

    @Override
    public Account getAccountById(String id) {
       return accountRepository.findFirstById(id);
    }
}
