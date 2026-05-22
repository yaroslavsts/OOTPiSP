package shared;

public class Swimmer extends Sportsman {
    public String style;
    public String bestTime;

    public Swimmer(String name, int age, String gender, int medals, int yearsInSport, String style, String bestTime) {
        super(name, age, gender, medals, yearsInSport);
        this.style = style;
        this.bestTime = bestTime;
    }

    public String type() {
        return "swimmer";
    }

    public String summary() {
        return super.summary() + ", style: " + style + ", best time: " + bestTime;
    }
}
