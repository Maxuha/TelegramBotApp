package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.bot.UserHistory;
import life.good.goodlife.model.news.CategoryNews;
import life.good.goodlife.model.news.News;
import life.good.goodlife.service.bot.UserHistoryService;
import life.good.goodlife.service.bot.UserService;
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
    private final UserHistoryService userHistoryService;
    private final UserService userService;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final int size = 5;

    public NewsController(NewsService newsService, UserHistoryService userHistoryService, UserService userService, TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.newsService = newsService;
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
    }

    @BotRequest("Новости")
    BaseRequest getNews(Long chatId) {
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), "/news", "0|0");
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Музыка", "Здоровье", "Наука", "Спорт", "Технологии"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.general)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Следущие 5 новостей")
    BaseRequest getNextNews(Long chatId) {
        if (sendFiveNews(chatId, CategoryNews.none)) {
            return new SendMessage(chatId, "Приятного чтения ☕").disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22");
        }
    }

    @BotRequest("Главные")
    BaseRequest getGeneralNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Музыка", "Здоровье", "Наука", "Спорт", "Технологии"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.general)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Музыка")
    BaseRequest getMusicNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Главные", "Здоровье", "Наука", "Спорт", "Технологии"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.music)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Развлечение")
    BaseRequest getEntertainmentNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Главные", "Здоровье", "Развлечение", "Спорт", "Технологии"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.entertainment)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Здоровье")
    BaseRequest getHealthNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Главные", "Бизнес", "Развлечение", "Спорт", "Технологии"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.health)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Наука")
    BaseRequest getScienceNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Музыка", "Здоровье", "Развлечение", "Спорт", "Технологии"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.science)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Технологии")
    BaseRequest getTechnologyNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Музыка", "Здоровье", "Развлечение", "Главные", "Спорт"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.technology)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Спорт")
    BaseRequest getSportNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Музыка", "Здоровье", "Развлечение", "Главные", "Технологии"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.sports)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Бизнес")
    BaseRequest getBusinessNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Следущие 5 новостей"},
                new String[]{"Музыка", "Здоровье", "Развлечение", "Главные", "Технологии"},
                new String[]{"Главное меню"})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.business)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(true);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    private boolean sendFiveNews(Long chatId, CategoryNews category) {
        UserHistory userHistory = userHistoryService.findLastUserHistoryByUserId(chatId);
        String[] answers = userHistory.getAnswer().split("|");
        int page = Integer.parseInt(answers[0]);
        int offset = Integer.parseInt(answers[1]);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletionService<News> completionService = new ExecutorCompletionService<>(executorService);
        int finalPage = page;
        Future<News> submit = completionService.submit(() -> newsService.getNews(finalPage, category.toString()));
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
            userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), "/news", offset + "|" + page);
            return true;
        } else {
            return false;
        }
    }
}
