package lab6;

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
        List<StoragePlugin> storagePlugins = new ArrayList<>();
        storagePlugins.addAll(PluginLoader.load(Path.of("artifacts/plugins/lab5"), StoragePlugin.class));
        storagePlugins.addAll(PluginLoader.load(Path.of("artifacts/plugins/lab6"), StoragePlugin.class));

        if (args.length > 0 && args[0].equals("--demo")) {
            List<Athlete> athletes = new ArrayList<>();
            athletes.add(registry.create("skater", Map.of(
                "Name", "Daria",
                "Age", "19",
                "Gender", "female",
                "Medals", "5",
                "Years", "11",
                "Discipline", "speed",
                "Score", "98"
            )));
            StoragePipeline pipeline = new StoragePipeline(new AthleteStore(registry), storagePlugins);
            Path file = Path.of("artifacts/lab6-adapter.dat");
            pipeline.save(file, athletes, "friend-base64-adapter");
            System.out.println("Adapter: friend-base64-adapter");
            System.out.println("Factory Method: TypeDef creates objects");
            System.out.println("Strategy: StoragePlugin processing");
            System.out.println("Loaded: " + pipeline.load(file, "friend-base64-adapter").get(0).summary());
            return;
        }

        new AthleteFrame(
            "Lab6 - Adapter and patterns",
            registry,
            storagePlugins,
            "artifacts/lab6-adapter.dat",
            "Adapter + Factory Method + Strategy"
        ).setVisible(true);
    }
}
