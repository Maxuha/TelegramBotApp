package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Webhook;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {
    Webhook webhook;
    public String createOperation(String data) {
        Gson gson = new Gson();
        webhook = gson.fromJson(data, Webhook.class);
        return webhook.toString();
    }

    public String getBalance() {
        return webhook.getData().getStatementItem().getBalanceCount().toString();
    }
}
