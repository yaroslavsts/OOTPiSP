package shared;

public class Sportsman extends Person {
    public int medals;
    public int yearsInSport;

    public Sportsman(String name, int age, String gender, int medals, int yearsInSport) {
        super(name, age, gender);
        this.medals = medals;
        this.yearsInSport = yearsInSport;
    }

    public String type() {
        return "sportsman";
    }

    public String summary() {
        return super.summary() + ", medals: " + medals + ", years: " + yearsInSport;
    }
}
