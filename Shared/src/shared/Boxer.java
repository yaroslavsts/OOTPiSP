package shared;

public class Boxer extends Sportsman {
    public int weightCategory;

    public Boxer(String name, int age, String gender, int medals, int yearsInSport, int weightCategory) {
        super(name, age, gender, medals, yearsInSport);
        this.weightCategory = weightCategory;
    }

    public String type() {
        return "boxer";
    }

    public String summary() {
        return super.summary() + ", weight: " + weightCategory;
    }
}
