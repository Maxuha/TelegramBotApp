package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.User;
import life.good.goodlife.repos.bot.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByChatId(long chatId) {
        return userRepository.findAllByChatId(chatId);
    }

    public User findById(long id) {
        return userRepository.findAllById(id);
    }
}
