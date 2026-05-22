package shared;

public class Skater extends Sportsman {
    public String discipline;
    public int bestScore;

    public Skater(String name, int age, String gender, int medals, int yearsInSport, String discipline, int bestScore) {
        super(name, age, gender, medals, yearsInSport);
        this.discipline = discipline;
        this.bestScore = bestScore;
    }

    public String type() {
        return "skater";
    }

    public String summary() {
        return super.summary() + ", discipline: " + discipline + ", score: " + bestScore;
    }
}
