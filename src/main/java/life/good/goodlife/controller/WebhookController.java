package life.good.goodlife.controller;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.bank.CurrencyCodeFactory;
import life.good.goodlife.model.monobonk.Balance;
import life.good.goodlife.model.monobonk.Webhook;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.monobank.LoginService;
import life.good.goodlife.service.monobank.StatementService;
import life.good.goodlife.service.monobank.WebhookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "webhook/")
public class WebhookController {
    private static Logger logger = LoggerFactory.getLogger(WebhookController.class);
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final UserService userService;
    private final WebhookServiceImpl webhookService;
    private final StatementService statementService;
    private final LoginService loginService;

    public WebhookController(TelegramBotExecuteComponent telegramBotExecuteComponent, UserService userService,
                             WebhookServiceImpl webhookService, StatementService statementService, LoginService loginService) {
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        this.userService = userService;
        this.webhookService = webhookService;
        this.statementService = statementService;
        this.loginService = loginService;
    }

    @RequestMapping(path = "test/get", method = RequestMethod.GET)
    public ResponseEntity <?> testGet() {
        return ResponseEntity.ok("get");
    }

    @RequestMapping(path = "test/post", method = RequestMethod.POST)
    public ResponseEntity <?> testPost() {
        return ResponseEntity.ok("post");
    }

    @RequestMapping(path = "monobank", method = RequestMethod.POST)
    public ResponseEntity <?> monobank(@RequestBody String raw, @RequestHeader("Content-Type") String type) {
        Webhook webhook = webhookService.createOperation(raw);
        statementService.createStatement(webhook.getData());
        logger.info("Get webhook: {}", raw);
        String data;
        if (webhook.getData().getStatementItem().getAmount() > 0) {
            data = replenishment(webhook);
        } else {
            data = writeOff(webhook);
        }

        String clientId = loginService.getAccountById(webhook.getData().getAccount()).getClientId();
        System.out.println("C: " + loginService.getByClientId(clientId));
        //Long userId = loginService.getByClientId(clientId).getUserId();
        SendMessage sendMessage = new SendMessage(userService.findById(4).getChatId(), data).disableNotification(true)
                .disableWebPagePreview(true).parseMode(ParseMode.HTML);
        telegramBotExecuteComponent.sendMessage(sendMessage);
        return ResponseEntity.ok("ok");
    }

    private String replenishment(Webhook webhook){
        String result = "";
        result += "<b>Пополнение на карту</b>\n";
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

    private String writeOff(Webhook webhook) {
        String result = "";
        result += "<b>Списание с карты</b>\n";
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
}
