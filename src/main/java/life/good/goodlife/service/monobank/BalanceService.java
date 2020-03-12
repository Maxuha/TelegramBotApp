package life.good.goodlife.service.monobank;

import life.good.goodlife.model.monobonk.Account;
import life.good.goodlife.model.monobonk.UserInfo;

public interface BalanceService {
    UserInfo balanceApi(String token);
    Account getBalance(String[] cart);
}
