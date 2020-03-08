package life.good.goodlife.service.monobank;

public interface StatementService {
    String currency(String token);
    void createStatements(String token, Long second);
    Long getLastTimeByAccountId(String accountId);
}
