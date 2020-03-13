package life.good.goodlife.controller;

import com.pengrad.telegrambot.request.SendPhoto;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.statics.Request;
import org.codehaus.plexus.util.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

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

    @RequestMapping(path = "/test", method = RequestMethod.OPTIONS)
    public ResponseEntity <?> test3(@RequestBody String link) throws Exception {
        StringBuilder linkBuffer = new StringBuilder(link);
        linkBuffer.delete(0, 5);
        byte[] linkBytes = Base64.decodeBase64(linkBuffer.toString().getBytes());
        //byte[] linkBytes = Base64.decodeBase64(linkBuffer.toString().getBytes());
        link = new String(linkBytes);
        System.out.println("link: " + link);
        //URL url = new URL(link);
       // BufferedImage img = ImageIO.read(url);
        SendPhoto sendPhoto = new SendPhoto("593292108", "data:image/jpeg;base64,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");
        telegramBotExecuteComponent.sendPhoto(sendPhoto);

        /*final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("https://jump-to-infinity.com/index5.php");
        System.out.println(page.getHtmlElementById("download").getTextContent());*/
       /* String result = "";
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlPage page = webClient.getPage("https://jump-to-infinity.com/index5.php");
        webClient.waitForBackgroundJavaScriptStartingBefore(20000);
        webClient.waitForBackgroundJavaScript(20000);
        System.out.println(page.asXml());
        Document doc = Jsoup.parse(page.asXml());
        Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        for (Element image : images) {
            System.out.println("src : " + image.attr("src"));
        }
        webClient.close();*/

        //Document document = Jsoup.connect("https://jump-to-infinity.com/index5.php").timeout(10000).get();

        /*String response = Request.get("https://jump-to-infinity.com/index5.php");
        int indexStart = response.indexOf("<a id=\"download\" download=\"myImage.jpg\" href=\"");
        int indexFinish = response.indexOf("\">Download to myImage.jpg</a>");
        System.out.println(response);
        String result = response.substring(indexStart, indexFinish);
        System.out.println("Start: " + response.charAt(indexStart) + response.charAt(indexStart+1) + + response.charAt(indexFinish+2));
        System.out.println("Finish: " + response.charAt(indexFinish) + response.charAt(indexFinish+1) + response.charAt(indexFinish+2));
        System.out.println("Start: " + indexStart + " Finish: " + indexFinish);*/
        return ResponseEntity.ok(link);
    }
}
