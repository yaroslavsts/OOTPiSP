package lab3;

import shared.Athlete;
import shared.AthleteFrame;
import shared.AthleteRegistry;
import shared.AthleteStore;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        AthleteRegistry registry = AthleteRegistry.basic();

        if (args.length > 0 && args[0].equals("--demo")) {
            List<Athlete> athletes = new ArrayList<>();
            athletes.add(registry.create("boxer", Map.of(
                "Name", "Ivan",
                "Age", "22",
                "Gender", "male",
                "Medals", "4",
                "Years", "8",
                "Weight", "75"
            )));

            AthleteStore store = new AthleteStore(registry);
            Path file = Path.of("artifacts/lab3-text.txt");
            store.save(file, athletes);
            System.out.println("Variant 3: Text serialization");
            System.out.println("Saved and loaded: " + store.load(file).get(0).summary());
            return;
        }

        new AthleteFrame(
            "Lab3 - Text serialization",
            registry,
            List.of(),
            "artifacts/lab3-text.txt",
            "Variant 3: Text format"
        ).setVisible(true);
    }
}
