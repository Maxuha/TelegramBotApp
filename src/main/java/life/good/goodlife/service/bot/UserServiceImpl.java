package life.good.goodlife.service.bot;

import life.good.goodlife.model.bot.User;
import life.good.goodlife.repos.bot.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByChatId(long chatId) {
        return userRepository.findAllByChatId(chatId);
    }

    @Override
    public User findById(long id) {
        return userRepository.findAllById(id);
    }
}
