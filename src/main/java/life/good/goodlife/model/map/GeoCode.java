package life.good.goodlife.model.map;


public class GeoCode {
    private String formatted_address;
    private Geometry geometry;
    private String[] types;
    private String status;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder type = new StringBuilder();
        for (String temp : types) {
            type.append("-").append(temp).append("\n");
        }
        return formatted_address + "\n\n" + type;
    }
}
