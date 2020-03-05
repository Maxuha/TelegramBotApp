package life.good.goodlife.model.map;

public class Nearby {
    private Geometry geometry;
    private String icon;
    private String name;
    private OpeningHours opening_hours;
    private float rating;
    private String[] types;
    private int user_ratings_total;
    private String vicinity;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(OpeningHours opening_hours) {
        this.opening_hours = opening_hours;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(int user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @Override
    public String toString() {
        StringBuilder type = new StringBuilder();
        for (String temp : types) {
            type.append(temp).append(", ");
        }
        return name + "\n" + vicinity + "\n" + (opening_hours.isOpen_now() ? "Открыто" : "Закрыто") + "\n" + type +
                "\nРейтинг: " + rating + "\nОтзывов: " + user_ratings_total;
    }
}
