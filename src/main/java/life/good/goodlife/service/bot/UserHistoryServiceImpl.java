package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.Command;
import life.good.goodlife.model.bot.UserHistory;
import life.good.goodlife.repos.bot.UserHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;

    public UserHistoryServiceImpl(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
    }

    @Override
    public void createUserHistory(Long userId, Command command) {
        //Commands commands = commandServiceImpl.findCommandsByName(command);
        UserHistory userHistory = new UserHistory();
        userHistory.setCommandsId(command.getId());
        userHistory.setUserId(userId);
        userHistory.setDate(LocalDateTime.now());
        userHistoryRepository.save(userHistory);
    }

    @Override
    public UserHistory findLastUserHistoryByUserId(Long userId) {
        return userHistoryRepository.findLastUserHistoryByUserId(userId);
    }
}
