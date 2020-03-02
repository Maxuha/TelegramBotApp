package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.Commands;
import life.good.goodlife.repos.bot.CommandsRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandService {
    private final CommandsRepository commandsRepository;

    public CommandService(CommandsRepository commandsRepository) {
        this.commandsRepository = commandsRepository;
    }

    public Commands findCommandsByName(String title) {
        return commandsRepository.getByTitle(title);
    }
}
