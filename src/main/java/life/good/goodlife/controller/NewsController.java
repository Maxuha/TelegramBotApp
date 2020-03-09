package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.service.news.NewsService;

@BotController
public class NewsController {
    private final NewsService newsService;
    private int size = 5;
    private int offset = 0;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @BotRequest("Новости")
    BaseRequest getNews(Long chatId) {
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Загрузить следущие"),
                        new KeyboardButton("Главные"),
                        new KeyboardButton("Здоровье"),
                        new KeyboardButton("Наука"),
                        new KeyboardButton("Спорт"),
                        new KeyboardButton("Технологии")
                }
        );
        String result = newsService.getNews(size, offset, "general");
        offset += size;
        return new SendMessage(chatId, "Главные новости: \n" + result).replyMarkup(replayKeyboard);
    }
}
