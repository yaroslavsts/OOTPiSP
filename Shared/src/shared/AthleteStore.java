package shared;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AthleteStore {
    private final AthleteRegistry registry;

    public AthleteStore(AthleteRegistry registry) {
        this.registry = registry;
    }

    public byte[] toBytes(List<Athlete> athletes) {
        StringBuilder text = new StringBuilder();
        for (Athlete athlete : athletes) {
            TypeDef type = registry.get(athlete.type());
            text.append(athlete.type()).append("|").append(athlete.id);
            for (Field field : type.fields) {
                text.append("|").append(field.name).append("=").append(field.getter.apply(athlete));
            }
            text.append("\n");
        }
        return text.toString().getBytes(StandardCharsets.UTF_8);
    }

    public List<Athlete> fromBytes(byte[] bytes) {
        List<Athlete> result = new ArrayList<>();
        String text = new String(bytes, StandardCharsets.UTF_8);
        for (String line : text.split("\\R")) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split("\\|");
            Map<String, String> values = new LinkedHashMap<>();
            for (int i = 2; i < parts.length; i++) {
                String[] pair = parts[i].split("=", 2);
                if (pair.length == 2) {
                    values.put(pair[0], pair[1]);
                }
            }
            Athlete athlete = registry.create(parts[0], values);
            athlete.id = parts[1];
            result.add(athlete);
        }
        return result;
    }

    public void save(Path file, List<Athlete> athletes) throws Exception {
        Files.createDirectories(file.toAbsolutePath().getParent());
        Files.write(file, toBytes(athletes));
    }

    public List<Athlete> load(Path file) throws Exception {
        return fromBytes(Files.readAllBytes(file));
    }
}
