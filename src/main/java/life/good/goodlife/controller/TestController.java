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

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity <?> test3() throws IOException {
        String response = Request.get("https://jump-to-infinity.com/index5.php");
        int indexStart = response.indexOf("<a id=\"download\" download=\"myImage.jpg\" href=\"");
        int indexFinish = response.indexOf("\">Download to myImage.jpg</a>");
        String result = response.substring(indexStart+58, indexFinish-1);
        System.out.println("Start: " + response.charAt(indexStart));
        System.out.println("Finish: " + response.charAt(indexFinish));
        System.out.println("Start: " + indexStart + " Finish: " + indexFinish);
        return ResponseEntity.ok(response);
    }
}
