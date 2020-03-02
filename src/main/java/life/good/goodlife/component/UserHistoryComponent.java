package life.good.goodlife.component;

import life.good.goodlife.model.bot.Commands;
import life.good.goodlife.model.bot.UserHistory;
import life.good.goodlife.service.bot.CommandService;
import life.good.goodlife.service.bot.UserHistoryService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserHistoryComponent {
    private final UserHistoryService userHistoryService;
    private final CommandService commandService;

    public UserHistoryComponent(UserHistoryService userHistoryService, CommandService commandService) {
        this.userHistoryService = userHistoryService;
        this.commandService = commandService;
    }

    public void createUserHistory(Long userId, String command) {
        UserHistory userHistory = new UserHistory();
        Commands commands = commandService.findCommandsByName(command);
        userHistory.setCommandsId(commands.getId());
        userHistory.setUserId(userId);
        userHistory.setDate(LocalDateTime.now());
        userHistoryService.createUser(userHistory);
    }

    public UserHistory findLastUserHistoryByUserId(Long userId) {
        return userHistoryService.findLastUserHistoryByUserId(userId);
    }
}
