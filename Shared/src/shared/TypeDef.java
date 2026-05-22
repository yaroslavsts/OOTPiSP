package shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeDef {
    public final String type;
    public final List<Field> fields = new ArrayList<>();
    private final AthleteFactory factory;

    public TypeDef(String type, AthleteFactory factory) {
        this.type = type;
        this.factory = factory;
    }

    public TypeDef field(String name, Getter getter, Setter setter) {
        fields.add(new Field(name, getter, setter));
        return this;
    }

    public Athlete create(Map<String, String> values) {
        return factory.create(values);
    }

    public String toString() {
        return type;
    }
}
