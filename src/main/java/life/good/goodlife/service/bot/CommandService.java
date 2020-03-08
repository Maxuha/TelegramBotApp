package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.Command;

public interface CommandService {
    Command findCommandsByName(String title);
}
