package life.good.goodlife.model.monobonk;

import java.util.Arrays;

public class UserInfo {
    private String chatId;
    private String name;
    private Account[] accounts;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
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
        return Arrays.toString(accounts);
    }
}
