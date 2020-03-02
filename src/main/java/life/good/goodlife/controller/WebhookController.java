package life.good.goodlife.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/webhook/")
public class WebhookController {
    @RequestMapping(path = "monobank/", method = RequestMethod.POST)
    public ResponseEntity <?> monobank(@RequestBody String webHookUrl) {
        System.out.println(webHookUrl);
        return ResponseEntity.ok("ok");
    }
}
