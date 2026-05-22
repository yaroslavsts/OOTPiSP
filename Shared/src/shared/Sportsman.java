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

class Boxer extends Sportsman {
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

class Swimmer extends Sportsman {
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

class Jumper extends Sportsman {
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

class FootballPlayer extends Sportsman {
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

class Skater extends Sportsman {
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
