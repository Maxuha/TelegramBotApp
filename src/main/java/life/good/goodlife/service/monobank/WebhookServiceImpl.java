package life.good.goodlife.service.monobank;

import com.google.gson.Gson;
import life.good.goodlife.model.bank.CurrencyCodeFactory;
import life.good.goodlife.model.monobonk.Balance;
import life.good.goodlife.model.monobonk.Webhook;
import org.springframework.stereotype.Service;

@Service
public class WebhookServiceImpl implements WebhookService {
    Webhook webhook;

    @Override
    public String createOperation(String data) {
        Gson gson = new Gson();
        webhook = gson.fromJson(data, Webhook.class);
        String result = "";
        if (webhook.getData().getStatementItem().getAmount() > 0) {
            result += "Пополнение на карту";
        } else {
            result += "Списание с карты";
        }
        result += "\n" + webhook.getData().getStatementItem().getDescription() + "\n";
        if (webhook.getData().getStatementItem().getComment() != null) {
            result += webhook.getData().getStatementItem().getComment() + "\n";
        }
        Balance balance = Balance.getBalanceFactory(webhook.getData().getStatementItem().getBalance(), 980);
        int operationAmount = webhook.getData().getStatementItem().getOperationAmount();
        Balance operationAmountBalance = Balance.getBalanceFactory(operationAmount,
                webhook.getData().getStatementItem().getCurrencyCode());
        int amount = webhook.getData().getStatementItem().getAmount();
        Balance amountBalance = Balance.getBalanceFactory(amount, 980);
        result += "<b>" + operationAmountBalance + "</b>" + " * " + (amount / operationAmount) + " " +
                CurrencyCodeFactory.getSymbolByCurrencyCode(webhook.getData().getStatementItem().getCurrencyCode()) +
                " = " + amountBalance + "\n";
        Balance commission = Balance.getBalanceFactory(webhook.getData().getStatementItem().getCommissionRate(),
                webhook.getData().getStatementItem().getCurrencyCode());
        result += "Комиссия: " + commission + "\n";
        Balance cashback = Balance.getBalanceFactory(webhook.getData().getStatementItem().getCashbackAmount(), 980);
        result += "Кешбек: " + cashback + "\n";
        result += "На балансе: " + balance + "\n";
        return result;
    }

    @Override
    public String getBalance() {
        return webhook.getData().getStatementItem().getBalanceCount().toString();
    }
}
