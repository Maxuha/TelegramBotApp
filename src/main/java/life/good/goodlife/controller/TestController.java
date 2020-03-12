package life.good.goodlife.controller;

import life.good.goodlife.statics.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Objects;

import static org.apache.commons.io.FileUtils.getFile;

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

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity <?> test3() throws IOException {
        Document doc = Jsoup.connect("https://jump-to-infinity.com/index5.php")
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        System.out.println("Doc: " + doc.text());
        Elements elements = doc.select("a");
        String result = elements.first().attr("href");
        return ResponseEntity.ok(result);
    }
}
