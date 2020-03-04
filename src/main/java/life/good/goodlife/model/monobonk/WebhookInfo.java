package life.good.goodlife.model.monobonk;

import java.util.Arrays;

public class WebhookInfo {
    private String account;
    private Statement statementItem;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Statement getStatementItem() {
        return statementItem;
    }

    public void setStatementItem(Statement statementItem) {
        this.statementItem = statementItem;
    }

    @Override
    public String toString() {
        return statementItem.toString();
    }
}
