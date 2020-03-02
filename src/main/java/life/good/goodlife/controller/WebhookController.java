package life.good.goodlife.controller;

import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.service.bot.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity <?> monobank(@RequestBody String webHookUrl) {
        System.out.println(webHookUrl);
        telegramBotExecuteComponent.sendMessage(userService.findById(1).getChatId(), webHookUrl);
        return ResponseEntity.ok("ok");
    }

}
