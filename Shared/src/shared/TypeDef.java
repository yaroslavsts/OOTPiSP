package shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class TypeDef {
    public final String type;
    public final List<Field> fields = new ArrayList<>();
    private final Function<Map<String, String>, Athlete> factory;

    public TypeDef(String type, Function<Map<String, String>, Athlete> factory) {
        this.type = type;
        this.factory = factory;
    }

    public TypeDef field(String name, Function<Athlete, String> getter, BiConsumer<Athlete, String> setter) {
        fields.add(new Field(name, getter, setter));
        return this;
    }

    public Athlete create(Map<String, String> values) {
        return factory.apply(values);
    }

    public String toString() {
        return type;
    }
}

class Field {
    public final String name;
    public final Function<Athlete, String> getter;
    public final BiConsumer<Athlete, String> setter;

    public Field(String name, Function<Athlete, String> getter, BiConsumer<Athlete, String> setter) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }
}
