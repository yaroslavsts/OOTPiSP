package lab4;

import shared.AthleteFrame;
import shared.AthleteRegistry;
import shared.EntityPlugin;
import shared.PluginLoader;

import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        AthleteRegistry registry = AthleteRegistry.basic();
        List<EntityPlugin> plugins = PluginLoader.load(Path.of("artifacts/plugins/lab4"), EntityPlugin.class);
        for (EntityPlugin plugin : plugins) {
            plugin.register(registry);
        }

        new AthleteFrame(
            "Lab4 - Hierarchy plugin",
            registry,
            List.of(),
            "artifacts/lab4-text.txt",
            "Loaded entity plugins: " + plugins.size()
        ).setVisible(true);
    }
}
