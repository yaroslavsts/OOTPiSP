package lab3;

import shared.AthleteFrame;
import shared.AthleteRegistry;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AthleteRegistry registry = AthleteRegistry.basic();

        new AthleteFrame(
            "Lab3 - Text serialization",
            registry,
            List.of(),
            "artifacts/lab3-text.txt",
            "Variant 3: Text format"
        ).setVisible(true);
    }
}
