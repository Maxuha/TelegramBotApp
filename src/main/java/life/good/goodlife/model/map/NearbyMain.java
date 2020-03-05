package life.good.goodlife.model.map;

import java.util.Arrays;

public class NearbyMain {
    private Nearby[] results;
    private String status;

    public Nearby[] getResults() {
        return results;
    }

    public void setResults(Nearby[] results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder data = new StringBuilder();
        for (Nearby nearby : results) {
            data.append(nearby).append("::");
        }
        return data.toString();
    }
}
