package shared;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class AthleteRegistry {
    private final Map<String, TypeDef> types = new LinkedHashMap<>();

    public void add(TypeDef type) {
        types.put(type.type, type);
    }

    public TypeDef get(String type) {
        TypeDef result = types.get(type);
        if (result == null) {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
        return result;
    }

    public Collection<TypeDef> all() {
        return types.values();
    }

    public Athlete create(String type, Map<String, String> values) {
        return get(type).create(values);
    }

    public static AthleteRegistry basic() {
        AthleteRegistry registry = new AthleteRegistry();

        registry.add(person("person", v -> new Person(text(v, "Name"), number(v, "Age"), text(v, "Gender"))));
        registry.add(sportsman("sportsman", v -> new Sportsman(text(v, "Name"), number(v, "Age"), text(v, "Gender"), number(v, "Medals"), number(v, "Years"))));
        registry.add(sportsman("boxer", v -> new Boxer(text(v, "Name"), number(v, "Age"), text(v, "Gender"), number(v, "Medals"), number(v, "Years"), number(v, "Weight"))).field("Weight", a -> "" + ((Boxer) a).weightCategory, (a, x) -> ((Boxer) a).weightCategory = Integer.parseInt(x)));
        registry.add(sportsman("swimmer", v -> new Swimmer(text(v, "Name"), number(v, "Age"), text(v, "Gender"), number(v, "Medals"), number(v, "Years"), text(v, "Style"), text(v, "BestTime"))).field("Style", a -> ((Swimmer) a).style, (a, x) -> ((Swimmer) a).style = x).field("BestTime", a -> ((Swimmer) a).bestTime, (a, x) -> ((Swimmer) a).bestTime = x));
        registry.add(sportsman("jumper", v -> new Jumper(text(v, "Name"), number(v, "Age"), text(v, "Gender"), number(v, "Medals"), number(v, "Years"), number(v, "Jump"))).field("Jump", a -> "" + ((Jumper) a).longestJump, (a, x) -> ((Jumper) a).longestJump = Integer.parseInt(x)));
        registry.add(sportsman("football", v -> new FootballPlayer(text(v, "Name"), number(v, "Age"), text(v, "Gender"), number(v, "Medals"), number(v, "Years"), text(v, "Position"), text(v, "Club"), number(v, "Goals"))).field("Position", a -> ((FootballPlayer) a).position, (a, x) -> ((FootballPlayer) a).position = x).field("Club", a -> ((FootballPlayer) a).club, (a, x) -> ((FootballPlayer) a).club = x).field("Goals", a -> "" + ((FootballPlayer) a).goals, (a, x) -> ((FootballPlayer) a).goals = Integer.parseInt(x)));
        registry.add(sportsman("skater", v -> new Skater(text(v, "Name"), number(v, "Age"), text(v, "Gender"), number(v, "Medals"), number(v, "Years"), text(v, "Discipline"), number(v, "Score"))).field("Discipline", a -> ((Skater) a).discipline, (a, x) -> ((Skater) a).discipline = x).field("Score", a -> "" + ((Skater) a).bestScore, (a, x) -> ((Skater) a).bestScore = Integer.parseInt(x)));

        return registry;
    }

    public static TypeDef person(String type, AthleteFactory factory) {
        return new TypeDef(type, factory)
            .field("Name", a -> ((Person) a).name, (a, x) -> ((Person) a).name = x)
            .field("Age", a -> "" + ((Person) a).age, (a, x) -> ((Person) a).age = Integer.parseInt(x))
            .field("Gender", a -> ((Person) a).gender, (a, x) -> ((Person) a).gender = x);
    }

    public static TypeDef sportsman(String type, AthleteFactory factory) {
        return person(type, factory)
            .field("Medals", a -> "" + ((Sportsman) a).medals, (a, x) -> ((Sportsman) a).medals = Integer.parseInt(x))
            .field("Years", a -> "" + ((Sportsman) a).yearsInSport, (a, x) -> ((Sportsman) a).yearsInSport = Integer.parseInt(x));
    }

    public static String text(Map<String, String> values, String name) {
        return values.getOrDefault(name, "");
    }

    public static int number(Map<String, String> values, String name) {
        return Integer.parseInt(values.getOrDefault(name, "0"));
    }
}
