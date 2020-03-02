package life.good.goodlife;

import com.github.telegram.mvc.api.EnableTelegram;
import com.github.telegram.mvc.config.TelegramBotBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.github.telegram.mvc.config.TelegramMvcConfiguration;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableTelegram
public class GoodLifeApplication implements TelegramMvcConfiguration {
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(GoodLifeApplication.class, args);
    }

    @Override
    public void configuration(TelegramBotBuilder telegramBotBuilder) {
        telegramBotBuilder.token(environment.getProperty("telegram.bot.token")).alias("BotBean");
    }
}
