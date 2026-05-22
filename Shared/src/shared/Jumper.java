package shared;

public class Jumper extends Sportsman {
    public int longestJump;

    public Jumper(String name, int age, String gender, int medals, int yearsInSport, int longestJump) {
        super(name, age, gender, medals, yearsInSport);
        this.longestJump = longestJump;
    }

    public String type() {
        return "jumper";
    }

    public String summary() {
        return super.summary() + ", jump: " + longestJump;
    }
}
