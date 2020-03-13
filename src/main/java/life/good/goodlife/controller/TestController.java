package life.good.goodlife.controller;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.igorsuhorukov.phantomjs.PhantomJsDowloader;
import life.good.goodlife.statics.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Objects;

import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.sizeOf;

@RestController
public class TestController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity <?> test() {
        return ResponseEntity.ok("<b>OK<b/>");
    }

    @RequestMapping(path = "/favicon.ico", method = RequestMethod.GET)
    public ResponseEntity <?> test2() throws IOException {
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public ResponseEntity <?> test3(@RequestBody String link) throws Exception {
        System.out.println("link: " + link);
        String response = Request.get("https://jump-to-infinity.com/index5.php");
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
        return ResponseEntity.ok(link + " " + response);
    }
}
