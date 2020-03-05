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
        StringBuilder result = new StringBuilder();
        for (GeoCode geoCode : results) {
            result.append(geoCode.toString()).append(",\n");
        }
        return result.toString();
    }
}
