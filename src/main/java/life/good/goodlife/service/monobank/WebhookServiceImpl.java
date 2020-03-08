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
            return replenishment();
        } else {
            return writeOff();
        }
    }

    private String replenishment(){
        String result = "";
        result += "Пополнение на карту\n";
        result += webhook.getData().getStatementItem().getDescription() + "\n";
        if (webhook.getData().getStatementItem().getComment() != null) {
            result += webhook.getData().getStatementItem().getComment() + "\n";
        }
        Balance balance = Balance.getBalanceFactory(webhook.getData().getStatementItem().getBalance(), 980);
        int operationAmount = webhook.getData().getStatementItem().getOperationAmount();
        Balance operationAmountBalance = Balance.getBalanceFactory(operationAmount,
                webhook.getData().getStatementItem().getCurrencyCode());
        int amount = webhook.getData().getStatementItem().getAmount();
        Balance amountBalance = Balance.getBalanceFactory(amount, 980);
        if (webhook.getData().getStatementItem().getCurrencyCode() != 980) {
            result += operationAmountBalance + " * " + (amount / operationAmount) + " " +
                    CurrencyCodeFactory.getSymbolByCurrencyCode(980) +
                    " = " + amountBalance;
        } else {
            result += operationAmountBalance;
        }
        result += "\n<b>На балансе: " + balance + "</b>\n";
        return result;
    }

    private String writeOff() {
        String result = "";
        result += "Списание с карты";
        result += webhook.getData().getStatementItem().getDescription() + "\n";
        if (webhook.getData().getStatementItem().getComment() != null) {
            result += webhook.getData().getStatementItem().getComment() + "\n";
        }
        Balance balance = Balance.getBalanceFactory(webhook.getData().getStatementItem().getBalance(), 980);
        int operationAmount = webhook.getData().getStatementItem().getOperationAmount();
        Balance operationAmountBalance = Balance.getBalanceFactory(operationAmount,
                webhook.getData().getStatementItem().getCurrencyCode());
        int amount = webhook.getData().getStatementItem().getAmount();
        Balance amountBalance = Balance.getBalanceFactory(amount, 980);
        if (webhook.getData().getStatementItem().getCurrencyCode() != 980) {
            result += operationAmountBalance + " * " + (amount / operationAmount) + " " +
                    CurrencyCodeFactory.getSymbolByCurrencyCode(980) +
                    " = " + amountBalance;
        } else {
            result += operationAmountBalance;
        }
        Balance commission = Balance.getBalanceFactory(webhook.getData().getStatementItem().getCommissionRate(), 980);
        result += "\nКомиссия: " + commission + "\n";
        Balance cashback = Balance.getBalanceFactory(webhook.getData().getStatementItem().getCashbackAmount(), 980);
        result += "Кешбек: " + cashback + "\n";
        result += "<b>На балансе: " + balance + "</b>\n";
        return result;
    }

    @Override
    public String getBalance() {
        return webhook.getData().getStatementItem().getBalanceCount().toString();
    }
}
