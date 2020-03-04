package life.good.goodlife.model.monobonk;

public class Balance {
    private int main;
    private int cent;
    private String name;

    public Balance(int main, int cent, String name) {
        this.main = main;
        this.cent = cent;
        this.name = name;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public int getCent() {
        return cent;
    }

    public void setCent(int cent) {
        this.cent = cent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return main + "." + cent + " " + name;
    }
}
