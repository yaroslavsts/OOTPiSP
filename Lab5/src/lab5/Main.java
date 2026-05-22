package lab5;

import shared.Athlete;
import shared.AthleteFrame;
import shared.AthleteRegistry;
import shared.AthleteStore;
import shared.EntityPlugin;
import shared.PluginLoader;
import shared.StoragePipeline;
import shared.StoragePlugin;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        AthleteRegistry registry = AthleteRegistry.basic();
        for (EntityPlugin plugin : PluginLoader.load(Path.of("artifacts/plugins/lab4"), EntityPlugin.class)) {
            plugin.register(registry);
        }
        List<StoragePlugin> storagePlugins = PluginLoader.load(Path.of("artifacts/plugins/lab5"), StoragePlugin.class);

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
            StoragePipeline pipeline = new StoragePipeline(new AthleteStore(registry), storagePlugins);
            Path file = Path.of("artifacts/lab5-encrypted.dat");
            pipeline.save(file, athletes, "xor-encryption", "shift-encryption");
            System.out.println("Variant 3: encryption/decryption");
            System.out.println("Loaded storage plugins: " + storagePlugins.size());
            System.out.println("Loaded after decrypt: " + pipeline.load(file, "xor-encryption", "shift-encryption").get(0).summary());
            return;
        }

        new AthleteFrame(
            "Lab5 - Encryption plugins",
            registry,
            storagePlugins,
            "artifacts/lab5-encrypted.dat",
            "Variant 3: encryption/decryption"
        ).setVisible(true);
    }
}
