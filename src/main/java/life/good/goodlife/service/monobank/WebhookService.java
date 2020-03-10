package life.good.goodlife.service.monobank;

import life.good.goodlife.model.monobonk.Webhook;

public interface WebhookService {
    Webhook createOperation(String data);
}
