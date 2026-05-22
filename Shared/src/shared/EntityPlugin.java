package shared;

public interface EntityPlugin {
    String name();

    void register(AthleteRegistry registry);
}
