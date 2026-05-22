package shared;

public class FootballPlayer extends Sportsman {
    public String position;
    public String club;
    public int goals;

    public FootballPlayer(String name, int age, String gender, int medals, int yearsInSport, String position, String club, int goals) {
        super(name, age, gender, medals, yearsInSport);
        this.position = position;
        this.club = club;
        this.goals = goals;
    }

    public String type() {
        return "football";
    }

    public String summary() {
        return super.summary() + ", " + position + ", " + club + ", goals: " + goals;
    }
}
