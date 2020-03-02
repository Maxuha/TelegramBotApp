package life.good.goodlife.controller;

import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.service.bot.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "webhook/")
public class WebhookController {
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final UserService userService;

    public WebhookController(TelegramBotExecuteComponent telegramBotExecuteComponent, UserService userService) {
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        this.userService = userService;
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
        System.out.println(raw);
        System.out.println(type);
        telegramBotExecuteComponent.sendMessage(userService.findById(1).getChatId(), raw);
        return ResponseEntity.ok("ok");
    }

}
