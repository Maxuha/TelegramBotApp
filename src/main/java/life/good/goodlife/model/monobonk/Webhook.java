package life.good.goodlife.model.monobonk;

import java.util.Arrays;

public class Webhook {
    private String type;
    private WebhookInfo data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WebhookInfo getData() {
        return data;
    }

    public void setData(WebhookInfo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
