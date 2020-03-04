package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Statement;
import life.good.goodlife.model.monobonk.Webhook;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {
    public String createOperation(String data) {
        Gson gson = new Gson();
        Webhook webhook = gson.fromJson(data, Webhook.class);
        return webhook.toString();
    }
}
