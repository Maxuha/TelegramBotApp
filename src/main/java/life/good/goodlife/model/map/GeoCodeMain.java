package life.good.goodlife.model.map;

import java.util.Arrays;

public class GeoCodeMain {
    private GeoCode[] results;
    private String status;

    public GeoCode[] getResults() {
        return results;
    }

    public void setResults(GeoCode[] results) {
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
        return Arrays.toString(results);
    }
}
