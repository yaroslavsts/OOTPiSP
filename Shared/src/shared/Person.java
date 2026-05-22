package shared;

public class Person extends Athlete {
    public String name;
    public int age;
    public String gender;

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String type() {
        return "person";
    }

    public String summary() {
        return name + ", " + age + ", " + gender;
    }
}
