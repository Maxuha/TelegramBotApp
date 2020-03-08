package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.User;

import java.util.List;

public interface UserService {
    void createUser(User user);
    List<User> findAll();
    User findByChatId(long chatId);
    User findById(long id);
}
