package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.Command;
import life.good.goodlife.model.bot.UserHistory;

public interface UserHistoryService {
    void createUserHistory(Long userId, Command command);
    UserHistory findLastUserHistoryByUserId(Long userId);
}
