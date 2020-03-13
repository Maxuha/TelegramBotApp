package life.good.goodlife.controller;

import com.pengrad.telegrambot.request.GetStickerSet;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.request.UploadStickerFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.statics.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
        link = linkBuffer.toString();
        String[] strings = link.split(",");
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        link = new String(data);
        //UploadStickerFile uploadStickerFile = new UploadStickerFile(593292108, link);
        //GetFileResponse response = telegramBotExecuteComponent.sendUploadStickerFile(uploadStickerFile);
        //System.out.println(response.toString());
        /*GetStickerSet getStickerSet = new GetStickerSet(stickerSet);
        GetStickerSetResponse response = bot.execute(getStickerSet);
        StickerSet stickerSet = response.stickerSet();*/
        SendSticker sendSticker = new SendSticker(593292108, link);
        telegramBotExecuteComponent.sendSticker(sendSticker);
        SendPhoto sendPhoto = new SendPhoto(593292108, data);
        telegramBotExecuteComponent.sendPhoto(sendPhoto);
        return ResponseEntity.ok(link);
    }
}
