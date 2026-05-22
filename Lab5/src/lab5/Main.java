package lab5;

import shared.AthleteFrame;
import shared.AthleteRegistry;
import shared.EntityPlugin;
import shared.PluginLoader;
import shared.StoragePlugin;

import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        AthleteRegistry registry = AthleteRegistry.basic();
        for (EntityPlugin plugin : PluginLoader.load(Path.of("artifacts/plugins/lab4"), EntityPlugin.class)) {
            plugin.register(registry);
        }
        List<StoragePlugin> storagePlugins = PluginLoader.load(Path.of("artifacts/plugins/lab5"), StoragePlugin.class);

        new AthleteFrame(
            "Lab5 - Encryption plugins",
            registry,
            storagePlugins,
            "artifacts/lab5-encrypted.txt",
            "Encrypted text format"
        ).setVisible(true);
    }
}
