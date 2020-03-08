package life.good.goodlife.model.monobonk;

public class UserInfo {
    private String clientId;
    private String name;
    private Account[] accounts;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String chatId) {
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
}
