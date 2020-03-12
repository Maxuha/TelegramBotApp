package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Webhook;
import org.springframework.stereotype.Service;

@Service
public class WebhookServiceImpl implements WebhookService {
    @Override
    public Webhook createOperation(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, Webhook.class);
    }
}
