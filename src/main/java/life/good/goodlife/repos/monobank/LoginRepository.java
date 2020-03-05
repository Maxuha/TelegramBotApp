package life.good.goodlife.repos.monobank;

import life.good.goodlife.model.monobonk.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);
}
