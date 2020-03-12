package life.good.goodlife.repos.monobank;

import life.good.goodlife.model.monobonk.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findById(String id);
    Account findFirstByClientId(String clientId);
    Account findFirstById(String accountId);
    List<Account> findByClientId(String clientId);
    Account findByMaskedPan(String[] maskedPan);
}
