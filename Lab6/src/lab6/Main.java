package lab6;

import shared.AthleteFrame;
import shared.AthleteRegistry;
import shared.EntityPlugin;
import shared.PluginLoader;
import shared.StoragePlugin;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Starts Lab 6 and adapts a friend's triangle plugin to the Lab 5 storage pipeline.
 */
public class Main {
    /**
     * Loads the base entity model, Lab 4 entity plugins, Lab 5 storage strategies, and the triangle adapter plugin.
     */
    public static void main(String[] args) throws Exception {
        AthleteRegistry registry = AthleteRegistry.basic();

        // Keep the Lab 4 entity extension available, so the copied lab has the same object set as Lab 5.
        for (EntityPlugin plugin : PluginLoader.load(Path.of("artifacts/plugins/lab4"), EntityPlugin.class)) {
            plugin.register(registry);
        }

        // Storage plugins are strategies: the user can choose one or several processing algorithms in the UI.
        List<StoragePlugin> storagePlugins = new ArrayList<>();
        storagePlugins.addAll(PluginLoader.load(Path.of("artifacts/plugins/lab5"), StoragePlugin.class));
        storagePlugins.addAll(PluginLoader.load(Path.of("artifacts/plugins/lab6"), StoragePlugin.class));

        new AthleteFrame(
            "Lab6 - Triangle adapter",
            registry,
            storagePlugins,
            "artifacts/lab6-triangle-adapter.txt",
            "Adapter + Strategy + Factory Method"
        ).setVisible(true);
    }
}
