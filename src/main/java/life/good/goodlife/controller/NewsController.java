package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.news.Article;
import life.good.goodlife.model.news.News;
import life.good.goodlife.service.news.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

@BotController
public class NewsController {
    private static Logger logger = LoggerFactory.getLogger(NewsController.class);
    private final NewsService newsService;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private int page = 1;
    private int offset = 0;
    private final int size = 5;

    public NewsController(NewsService newsService, TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.newsService = newsService;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
    }

    @BotRequest("Новости")
    BaseRequest getNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Главные", "Здоровье", "Наука", "Спорт", "Технологии"},
                new String[]{"Главное меню"})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true);
        if (sendFiveNews(chatId)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Следущие 5 новостей")
    BaseRequest getNextNews(Long chatId) {
        if (sendFiveNews(chatId)) {
            return new SendMessage(chatId, "Приятного чтения ☕").disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22");
        }
    }

    private boolean sendFiveNews(Long chatId) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletionService<News> completionService = new ExecutorCompletionService<>(executorService);
        Future<News> submit = completionService.submit(() -> newsService.getNews(page, "general"));

        News news = null;
        try {
            news = submit.get();
        } catch (InterruptedException e) {
            logger.error("Interrupted thread - " + e.getMessage());
        } catch (ExecutionException e) {
            logger.error("Failed execution thread - " + e.getMessage());
        }
        StringBuilder result = new StringBuilder("Главные новости: \n");
        if (news != null && news.getArticles() != null && news.getArticles().length > 0) {
            for (int i = offset; i < size + offset; i++) {
                result.append("[").append("Опубликовано: ").append(LocalDateTime.parse(news.getArticles()[i].getPublishedAt()
                        .replace("Z", "")).format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")))
                        .append("](").append(news.getArticles()[i].getUrl()).append(")");
                telegramBotExecuteComponent.sendMessageMarkdown(chatId, result.toString());
                result = new StringBuilder();
            }
            offset += size;
            if (offset == news.getTotalResults()) {
                page++;
                offset = 0;
            }
            return true;
        } else {
            return false;
        }
    }
}
