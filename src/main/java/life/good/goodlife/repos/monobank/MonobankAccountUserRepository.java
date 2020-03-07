package life.good.goodlife.repos.monobank;

import life.good.goodlife.model.monobonk.MonobankAccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonobankAccountUserRepository extends JpaRepository<MonobankAccountUser, Long> {
}
