package life.good.goodlife.repos.monobank;

import life.good.goodlife.model.monobonk.UserMonobank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<UserMonobank, Long> {
    UserMonobank findByUserId(Long userId);
}