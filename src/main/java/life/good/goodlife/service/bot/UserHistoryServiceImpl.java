package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.Command;
import life.good.goodlife.model.bot.User;
import life.good.goodlife.model.bot.UserHistory;
import life.good.goodlife.repos.bot.UserHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {
    private static Logger logger = LoggerFactory.getLogger(UserHistoryServiceImpl.class);
    private final UserHistoryRepository userHistoryRepository;
    private final UserService userService;
    private final CommandService commandService;

    public UserHistoryServiceImpl(UserHistoryRepository userHistoryRepository, UserService userService, CommandService commandService) {
        this.userHistoryRepository = userHistoryRepository;
        this.userService = userService;
        this.commandService = commandService;
    }

    @Override
    public void createUserHistory(Long userId, String command, String answer) {
        UserHistory userHistory = new UserHistory();
        logger.info("Find command: '{}'", command);
        userHistory.setCommandsId(commandService.findCommandsByName(command).getId());
        userHistory.setUserId(userId);
        userHistory.setDate(LocalDateTime.now());
        userHistory.setAnswer(answer);
        logger.info("Creating history command '{}}'", command);
        userHistoryRepository.save(userHistory);
    }

    @Override
    public UserHistory findLastUserHistoryByUserId(Long userId) {
        return userHistoryRepository.findLastUserHistoryByUserId(userId)[0];
    }

    @Override
    public UserHistory findPreLastUserHistoryByUserId(Long userId) {
        return userHistoryRepository.findLastUserHistoryByUserId(userId)[1];
    }
}
