package life.good.goodlife.service.monobank;

public interface StatementService {
    String currency(String token);
    void createStatements(String token, String accountId, Long second);
    Long getLastTimeByAccountId(String accountId);
}
