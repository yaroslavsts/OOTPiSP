package shared;

import java.util.UUID;

public abstract class Athlete {
    public String id = UUID.randomUUID().toString();

    public abstract String type();

    public abstract String summary();
}
