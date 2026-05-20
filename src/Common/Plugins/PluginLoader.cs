using System.Reflection;
using System.Runtime.Loader;

namespace OOTPiSP.Common.Plugins;

public sealed class PluginLoader
{
    /// <summary>
    /// Loads plugin classes from dll files. Reflection is used only at the plugin boundary.
    /// </summary>
    public List<TPlugin> Load<TPlugin>(string directory)
    {
        if (!Directory.Exists(directory))
        {
            return new List<TPlugin>();
        }

        AssemblyLoadContext.Default.Resolving += (_, assemblyName) =>
        {
            var dependencyPath = Path.Combine(directory, $"{assemblyName.Name}.dll");
            return File.Exists(dependencyPath)
                ? AssemblyLoadContext.Default.LoadFromAssemblyPath(Path.GetFullPath(dependencyPath))
                : null;
        };

        var result = new List<TPlugin>();

        foreach (var file in Directory.GetFiles(directory, "*.dll").OrderBy(Path.GetFileName))
        {
            Assembly assembly;

            try
            {
                assembly = AssemblyLoadContext.Default.LoadFromAssemblyPath(Path.GetFullPath(file));
            }
            catch
            {
                continue;
            }

            var pluginTypes = assembly.GetTypes()
                .Where(type => typeof(TPlugin).IsAssignableFrom(type))
                .Where(type => type is { IsAbstract: false, IsInterface: false });

            foreach (var pluginType in pluginTypes)
            {
                if (Activator.CreateInstance(pluginType) is TPlugin plugin)
                {
                    result.Add(plugin);
                }
            }
        }

        return result;
    }
}
