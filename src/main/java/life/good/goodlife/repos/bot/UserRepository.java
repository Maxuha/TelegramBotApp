package life.good.goodlife.repos.bot;

import life.good.goodlife.model.bot.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findAllByChatId(Long chatId);
}
