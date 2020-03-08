package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.Command;
import life.good.goodlife.repos.bot.CommandsRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandServiceImpl implements CommandService{
    private final CommandsRepository commandsRepository;

    public CommandServiceImpl(CommandsRepository commandsRepository) {
        this.commandsRepository = commandsRepository;
    }

    @Override
    public Command findCommandsByName(String title) {
        return commandsRepository.getByTitle(title);
    }
}
