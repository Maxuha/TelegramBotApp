package life.good.goodlife.service.monobank;

public interface WebhookService {
    String createOperation(String data);
    String getBalance();
}
