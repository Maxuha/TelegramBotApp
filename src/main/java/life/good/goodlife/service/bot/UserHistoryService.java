package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.UserHistory;
import life.good.goodlife.repos.bot.UserHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;

    public UserHistoryService(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
    }

    public void createUser(UserHistory userHistory) {
        userHistoryRepository.save(userHistory);
    }

    public UserHistory findLastUserHistoryByUserId(long userId) {
        return userHistoryRepository.findLastUserHistoryByUserId(userId);
    }
}
