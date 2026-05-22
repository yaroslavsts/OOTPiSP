package plugins.cyclist;

import shared.Sportsman;

public class Cyclist extends Sportsman {
    public String bike;
    public int distance;

    public Cyclist(String name, int age, String gender, int medals, int years, String bike, int distance) {
        super(name, age, gender, medals, years);
        this.bike = bike;
        this.distance = distance;
    }

    public String type() {
        return "cyclist";
    }

    public String summary() {
        return super.summary() + ", bike: " + bike + ", distance: " + distance;
    }
}
