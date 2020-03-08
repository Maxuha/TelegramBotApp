package life.good.goodlife.service.monobank;

public interface StatementService {
    String currency(String token);
    void CreateStatements(String token);
}
