package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.news.Article;
import life.good.goodlife.service.news.NewsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@BotController
public class NewsController {
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
        sendFiveNews(chatId);
        return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
    }

    @BotRequest("Следущие 5 новостей")
    BaseRequest getNextNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Главные", "Здоровье", "Наука", "Спорт", "Технологии"},
                new String[]{"Главное меню"})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true);
        sendFiveNews(chatId);
        return new SendMessage(chatId, "Приятного чтения ☕" + offset).replyMarkup(replyKeyboardMarkup);
    }

    private void sendFiveNews(Long chatId) {
        Article[] articles = newsService.getNews(1, "general");
        StringBuilder result = new StringBuilder("Главные новости: \n");
        for (int i = offset; i < size + offset; i++) {
            result.append("[").append("Опубликовано: ").append(LocalDateTime.parse(articles[i].getPublishedAt()
                    .replace("Z", "")).format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")))
                    .append("](").append(articles[i].getUrl()).append(")");
            telegramBotExecuteComponent.sendMessageMarkdown(chatId, result.toString());
            result = new StringBuilder();
        }
        offset += size;
        if (offset == 35) {
            page++;
            offset = 0;
        }
    }
}
