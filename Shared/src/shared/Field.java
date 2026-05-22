package shared;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class Field {
    public final String name;
    public final Function<Athlete, String> getter;
    public final BiConsumer<Athlete, String> setter;

    public Field(String name, Function<Athlete, String> getter, BiConsumer<Athlete, String> setter) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }
}
