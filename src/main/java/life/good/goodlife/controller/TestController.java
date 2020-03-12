package life.good.goodlife.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
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
        InputStream inputStream = Objects.requireNonNull(TestController.class.getClassLoader().getResource("static/index.html")).openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer html = new StringBuffer();
        while (reader.ready()) {
            html.append(reader.readLine());
        }
        System.out.println(html);
        return ResponseEntity.ok(html);
    }
}
