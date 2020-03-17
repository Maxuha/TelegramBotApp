package life.good.goodlife.repos.bot;

import life.good.goodlife.model.bot.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    @Query(value = "SELECT * FROM user_history where user_id = ?1 ORDER BY date DESC LIMIT 2;", nativeQuery = true)
    UserHistory[] findLastUserHistoryByUserId(Long userId);
}
