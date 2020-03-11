package life.good.goodlife.service.monobank;

import life.good.goodlife.model.monobonk.Statement;
import life.good.goodlife.model.monobonk.WebhookInfo;

public interface StatementService {
    String currency(String token);
    void createStatementList(String token, String accountId, Long second);
    void createStatement(WebhookInfo webhookInfo);
    Long getLastTimeByAccountId(String accountId);
    Statement findById(String id);
}
