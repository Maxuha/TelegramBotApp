package life.good.goodlife.model.monobonk;

public class UserInfo {
    private String clientId;
    private String name;
    private Account[] accounts;

    public String getChatId() {
        return clientId;
    }

    public void setChatId(String chatId) {
        this.clientId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void setAccounts(Account[] accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("<b>Мой Баланс:</b>\n");
        for (Account account: accounts) {
            result.append(account.toString());
        }
        return result.toString();
    }
}
