package life.good.goodlife.controller;

import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.monobank.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "webhook/")
public class WebhookController {
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final UserService userService;
    private final WebhookService webhookService;

    public WebhookController(TelegramBotExecuteComponent telegramBotExecuteComponent, UserService userService, WebhookService webhookService) {
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
        telegramBotExecuteComponent.sendMessage(userService.findById(1).getChatId(), raw);
        telegramBotExecuteComponent.sendMessage(userService.findById(1).getChatId(), webhookService.createOperation(type));
        return ResponseEntity.ok("ok");
    }

}
