package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.bot.UserHistory;
import life.good.goodlife.model.buttons.Buttons;
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


    public NewsController(NewsService newsService, UserHistoryService userHistoryService, UserService userService,
                          TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.newsService = newsService;
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
    }

    @BotRequest("Новости")
    BaseRequest getNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[7]},
                new String[]{Buttons.getNewsButton()[2]},
                new String[]{Buttons.getNewsButton()[3], Buttons.getNewsButton()[5]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.general)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup).disableNotification(false);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup).disableNotification(false);
        }
    }

    @BotRequest("Следущие 5️⃣ новостей \uD83D\uDCF0")
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
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[7]},
                new String[]{Buttons.getNewsButton()[2]},
                new String[]{Buttons.getNewsButton()[3], Buttons.getNewsButton()[5]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.general)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Музыка \uD83C\uDFB6")
    BaseRequest getMusicNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[5], Buttons.getNewsButton()[8]},
                new String[]{Buttons.getNewsButton()[3]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[6]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.music)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Развлечение \uD83D\uDD79")
    BaseRequest getEntertainmentNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[5], Buttons.getNewsButton()[8]},
                new String[]{Buttons.getNewsButton()[3]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[6]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.entertainment)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Здоровье \uD83C\uDFE5")
    BaseRequest getHealthNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[5], Buttons.getNewsButton()[8]},
                new String[]{Buttons.getNewsButton()[3]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[6]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.health)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Наука \uD83E\uDDEC")
    BaseRequest getScienceNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[5], Buttons.getNewsButton()[8]},
                new String[]{Buttons.getNewsButton()[3]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[6]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.science)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Технологии")
    BaseRequest getTechnologyNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[5], Buttons.getNewsButton()[8]},
                new String[]{Buttons.getNewsButton()[3]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[6]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.technology)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Спорт \uD83C\uDFC5")
    BaseRequest getSportNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[5], Buttons.getNewsButton()[8]},
                new String[]{Buttons.getNewsButton()[3]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[6]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.sports)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    @BotRequest("Бизнес")
    BaseRequest getBusinessNews(Long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Buttons.getNewsButton()[0]},
                new String[]{Buttons.getNewsButton()[5], Buttons.getNewsButton()[8]},
                new String[]{Buttons.getNewsButton()[3]},
                new String[]{Buttons.getNewsButton()[4], Buttons.getNewsButton()[6]},
                new String[]{Buttons.getMainButton()[0]})
                .resizeKeyboard(false);
        if (sendFiveNews(chatId, CategoryNews.business)) {
            return new SendMessage(chatId, "Приятного чтения ☕").replyMarkup(replyKeyboardMarkup);
        } else {
            return new SendMessage(chatId, "Нету новостей \uD83D\uDE22").replyMarkup(replyKeyboardMarkup);
        }
    }

    private boolean sendFiveNews(Long chatId, CategoryNews category) {
        UserHistory userHistory = userHistoryService.findLastUserHistoryByUserId(userService.findByChatId(chatId).getId());
        int page;
        int offset;
        if (userHistory.getAnswer() == null || userHistory.getAnswer().isEmpty()) {
            page = 1;
            offset = 0;
        } else {
            String[] answers = userHistory.getAnswer().split("::");
            page = Integer.parseInt(answers[1]);
            offset = Integer.parseInt(answers[0]);
            if (CategoryNews.none.equals(category)) {
                category = CategoryNews.valueOf(answers[2]);
            }
            System.out.println("page" + page + " offset: " + offset);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletionService<News> completionService = new ExecutorCompletionService<>(executorService);
        int finalPage = page;
        CategoryNews finalCategory = category;
        Future<News> submit = completionService.submit(() -> newsService.getNews(finalPage, finalCategory.toString()));
        News news = null;
        try {
            news = submit.get();
        } catch (InterruptedException e) {
            logger.error("Interrupted thread - " + e.getMessage());
        } catch (ExecutionException e) {
            logger.error("Failed execution thread - " + e.getMessage());
        }
        StringBuilder result = new StringBuilder();
        if (news != null && news.getArticles() != null && news.getArticles().length > 0) {
            for (int i = offset; i < size + offset; i++) {
                result.append("<b>").append(news.getArticles()[i].getTitle()).append("</b>\n")
                        .append(news.getArticles()[i].getDescription()).append("\n")
                        .append("Опубликовано: <code>").append(LocalDateTime.parse(news.getArticles()[i].getPublishedAt()
                                .replace("Z", "")).format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")))
                        .append("</code>\n")
                        .append("<i>")
                        .append(news.getArticles()[i].getAuthor())
                        .append("</i>\n")
                        .append("<a href=\"")
                        .append(news.getArticles()[i].getUrlToImage())
                        .append("\">.</a>\n");

                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Посмотреть").url(news.getArticles()[i].getUrl())
                        });

                telegramBotExecuteComponent.sendMessage(new SendMessage(chatId, result.toString()).replyMarkup(inlineKeyboard)
                        .parseMode(ParseMode.HTML).disableWebPagePreview(false));
                result = new StringBuilder();
            }
            offset += size;
            if (offset == 20) {
                page++;
                offset = 0;
            }
            userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), "/news", offset + "::" + page + "::" + category);
            return true;
        } else {
            return false;
        }
    }
    /*
    result.append("[").append("Опубликовано: ").append(LocalDateTime.parse(news.getArticles()[i].getPublishedAt()
                        .replace("Z", "")).format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")))
                        .append("](").append(news.getArticles()[i].getUrl()).append(")");
    */

}
