package life.good.goodlife.repos.bot;

import life.good.goodlife.model.bot.Commands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandsRepository extends JpaRepository<Commands, Long> {
    Commands getByTitle(String title);
}
