package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Webhook;
import org.springframework.stereotype.Service;

@Service
public class WebhookServiceImpl implements WebhookService {
    Webhook webhook;

    @Override
    public String createOperation(String data) {
        Gson gson = new Gson();
        webhook = gson.fromJson(data, Webhook.class);
        return webhook.toString();
    }

    @Override
    public String getBalance() {
        return webhook.getData().getStatementItem().getBalanceCount().toString();
    }
}
