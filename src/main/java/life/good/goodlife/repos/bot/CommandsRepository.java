package life.good.goodlife.repos.bot;

import life.good.goodlife.model.bot.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandsRepository extends JpaRepository<Command, Long> {
    Command getByTitle(String title);
}
