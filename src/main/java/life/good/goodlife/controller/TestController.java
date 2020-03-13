package life.good.goodlife.controller;

import com.pengrad.telegrambot.request.SendPhoto;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.statics.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;


@RestController
public class TestController {
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;

    public TestController(TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity <?> test() {
        return ResponseEntity.ok("<b>OK<b/>");
    }

    @RequestMapping(path = "/favicon.ico", method = RequestMethod.GET)
    public ResponseEntity <?> test2() throws IOException {
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity <?> test3Get() throws IOException {
        String response = Request.get("https://jump-to-infinity.com/index5.php");
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public ResponseEntity <?> test3(@RequestBody String link) throws Exception {
        StringBuilder linkBuffer = new StringBuilder(link);
        linkBuffer.delete(0, 5);
        System.out.println("encode: " + link);
        byte[] decodedBytes = Base64.getUrlDecoder().decode(linkBuffer.toString());
        link = new String(decodedBytes);
        System.out.println(link);
        SendPhoto sendPhoto = new SendPhoto("593292108", link);
        telegramBotExecuteComponent.sendPhoto(sendPhoto);
        return ResponseEntity.ok(link);
    }
}
