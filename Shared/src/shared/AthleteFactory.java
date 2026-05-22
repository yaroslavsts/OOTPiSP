package shared;

import java.util.Map;

public interface AthleteFactory {
    Athlete create(Map<String, String> values);
}
