package life.good.goodlife.service.monobank;

import life.good.goodlife.model.monobonk.Account;
import life.good.goodlife.model.monobonk.UserInfo;
import life.good.goodlife.model.monobonk.UserMonobank;

public interface LoginService {
    void createUser(UserMonobank userMonobank);
    void createAccount(Account account);
    String getToken(Long userId);
    UserInfo getUserInfo(String token);
    String getClientIdByUserId(Long userId);
    UserMonobank getUserIdByClientId(String clientId);
    Account getAccountById(String id);
}
