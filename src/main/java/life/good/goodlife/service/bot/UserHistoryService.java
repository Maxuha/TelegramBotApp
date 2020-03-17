package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.UserHistory;

public interface UserHistoryService {
    void createUserHistory(Long userId, String command, String answer);
    UserHistory findLastUserHistoryByUserId(Long userId);
    UserHistory findPreLastUserHistoryByUserId(Long userId);
}
