package life.good.goodlife.controller;

import com.pengrad.telegrambot.request.GetStickerSet;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.request.UploadStickerFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.statics.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@RestController
public class TestController {
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;

    public TestController(TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
    }



    @RequestMapping(path = "/favicon.ico", method = RequestMethod.GET)
    public ResponseEntity <?> test2() {
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(path = "/googlee3518009d066e9ef.html", method = RequestMethod.GET)
    public ResponseEntity <?> test5() {
        return ResponseEntity.ok("google-site-verification: googlee3518009d066e9ef.html");
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity <?> test3Get() {
        String response = Request.get("https://jump-to-infinity.com/index5.php?cart=5&balance=100");
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
        SendSticker sendSticker = new SendSticker(593292108, data);
        telegramBotExecuteComponent.sendSticker(sendSticker);
        return ResponseEntity.ok(link);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity <?> sessionMusic(@RequestParam String token) {
        Session session = Authenticator.getSession(token, System.getenv().get("music_api_key"), System.getenv().get("music_secret"));
        Track.love("BTS", "ON", session);
        return ResponseEntity.ok(session);
    }
}
