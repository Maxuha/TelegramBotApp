package life.good.goodlife.controller;

import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.monobank.WebhookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "webhook/")
public class WebhookController {
    private static Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final UserService userService;
    private final WebhookServiceImpl webhookService;

    public WebhookController(TelegramBotExecuteComponent telegramBotExecuteComponent, UserService userService,
                             WebhookServiceImpl webhookService) {
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        this.userService = userService;
        this.webhookService = webhookService;
    }

    @RequestMapping(path = "test/get", method = RequestMethod.GET)
    public ResponseEntity <?> testGet() {
        return ResponseEntity.ok("get");
    }

    @RequestMapping(path = "test/post", method = RequestMethod.POST)
    public ResponseEntity <?> testPost() {
        return ResponseEntity.ok("post");
    }

    @RequestMapping(path = "monobank", method = RequestMethod.POST)
    public ResponseEntity <?> monobank(@RequestBody String raw, @RequestHeader("Content-Type") String type) {
        String info = webhookService.createOperation(raw);
        logger.info("Get webhook: {}", raw);
        telegramBotExecuteComponent.sendMessageHtml(userService.findById(1).getChatId(), info);
        return ResponseEntity.ok("ok");
    }

}
