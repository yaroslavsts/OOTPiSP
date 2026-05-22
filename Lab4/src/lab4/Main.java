package lab4;

import shared.Athlete;
import shared.AthleteFrame;
import shared.AthleteRegistry;
import shared.EntityPlugin;
import shared.PluginLoader;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        AthleteRegistry registry = AthleteRegistry.basic();
        List<EntityPlugin> plugins = PluginLoader.load(Path.of("artifacts/plugins/lab4"), EntityPlugin.class);
        for (EntityPlugin plugin : plugins) {
            plugin.register(registry);
        }

        if (args.length > 0 && args[0].equals("--demo")) {
            Athlete cyclist = registry.create("cyclist", Map.of(
                "Name", "Pavel",
                "Age", "21",
                "Gender", "male",
                "Medals", "3",
                "Years", "7",
                "Bike", "road",
                "Distance", "120"
            ));
            System.out.println("Loaded entity plugins: " + plugins.size());
            System.out.println(cyclist.summary());
            return;
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
