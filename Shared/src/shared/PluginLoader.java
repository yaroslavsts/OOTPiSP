package shared;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginLoader {
    public static <T> List<T> load(Path dir, Class<T> type) throws Exception {
        List<T> result = new ArrayList<>();
        if (!Files.isDirectory(dir)) {
            return result;
        }

        List<URL> urls = new ArrayList<>();
        try (var files = Files.list(dir)) {
            for (Path file : files.filter(x -> x.toString().endsWith(".jar")).toList()) {
                urls.add(file.toUri().toURL());
            }
        }

        URLClassLoader loader = new URLClassLoader(urls.toArray(new URL[0]), PluginLoader.class.getClassLoader());
        for (T plugin : ServiceLoader.load(type, loader)) {
            result.add(plugin);
        }
        return result;
    }
}
