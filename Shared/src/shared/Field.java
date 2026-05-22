package shared;

public class Field {
    public final String name;
    public final Getter getter;
    public final Setter setter;

    public Field(String name, Getter getter, Setter setter) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }
}
