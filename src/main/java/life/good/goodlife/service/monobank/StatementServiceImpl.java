package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.monobonk.Currency;
import life.good.goodlife.model.monobonk.Statement;
import life.good.goodlife.repos.monobank.StatementRepository;
import life.good.goodlife.statics.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatementServiceImpl implements StatementService {
    private static Logger logger = LoggerFactory.getLogger(StatementServiceImpl.class);
    private final StatementRepository statementRepository;

    public StatementServiceImpl(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    @Override
    public String currency(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        String data = Request.get("https://api.monobank.ua/personal/statement", headers);
        Gson gson = new Gson();
        Currency[] currencies = gson.fromJson(data, Currency[].class);
        StringBuilder result = new StringBuilder("<b>Курс валют</b>\n            Покупка     Продажа\n");
        for (Currency currency : currencies) {
            result.append(currency.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public Long getLastTimeByAccountId(String accountId) {
        return statementRepository.findByAccountIdFirstOrderByTimeDesc(accountId);
    }

    @Override
    public void createStatements(String token, Long second) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Token", token);
        Long prevSecond = second;
        logger.info("Get Statements to second: {}", second);
        Boolean response = true;
        Integer failedResponse = 0;
        Gson gson;
        String data;
        Statement[] statements;
        while (response) {
            prevSecond -= 268200;
            logger.info("To request");
            data = Request.get("https://api.monobank.ua/personal/statement/0/" + prevSecond + "/" + second, headers);
            gson = new Gson();
            statements = gson.fromJson(data, Statement[].class);
            for (Statement statement : statements) {
                logger.info("Statement {} to database", statement);
                statementRepository.save(statement);
            }
            if (statements.length == 0) {
                failedResponse++;
            } else {
                failedResponse--;
            }
            if (failedResponse == 5) {
                response = false;
            }
            logger.info("Failed response: " + failedResponse);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                logger.error("Thread error sleep");
            }
            second = prevSecond;
        }
    }
}
