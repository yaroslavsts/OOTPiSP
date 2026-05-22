package shared;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StoragePipeline {
    private final AthleteStore store;
    private final Map<String, StoragePlugin> plugins = new LinkedHashMap<>();

    public StoragePipeline(AthleteStore store, List<StoragePlugin> plugins) {
        this.store = store;
        for (StoragePlugin plugin : plugins) {
            this.plugins.put(plugin.name(), plugin);
        }
    }

    public void save(Path file, List<Athlete> athletes, String... names) throws Exception {
        byte[] data = store.toBytes(athletes);
        for (String name : names) {
            data = plugins.get(name).beforeSave(data);
        }
        Files.createDirectories(file.toAbsolutePath().getParent());
        Files.write(file, data);
    }

    public List<Athlete> load(Path file, String... names) throws Exception {
        byte[] data = Files.readAllBytes(file);
        for (int i = names.length - 1; i >= 0; i--) {
            data = plugins.get(names[i]).afterLoad(data);
        }
        return store.fromBytes(data);
    }
}
