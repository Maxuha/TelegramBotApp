package life.good.goodlife.service.monobank;

import life.good.goodlife.model.monobonk.UserMonobank;
import life.good.goodlife.repos.monobank.LoginRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public String getToken(Long userId) {
        UserMonobank user = loginRepository.findByUserId(userId);
        return user.getToken();
    }
}
