using OOTPiSP.Common.Domain;
using OOTPiSP.Common.Plugins;

namespace OOTPiSP.Common.Storage;

public sealed class StoragePipeline
{
    private readonly JsonEntityStore _store;
    private readonly Dictionary<string, IStorageTransformPlugin> _plugins;

    public StoragePipeline(JsonEntityStore store, IEnumerable<IStorageTransformPlugin> plugins)
    {
        _store = store;
        _plugins = plugins.ToDictionary(plugin => plugin.Name, StringComparer.OrdinalIgnoreCase);
    }

    public IReadOnlyCollection<IStorageTransformPlugin> Plugins => _plugins.Values;

    /// <summary>
    /// Applies selected plugins in the given order before writing bytes to a file.
    /// </summary>
    public void Save(string path, IReadOnlyList<IEntity> entities, IReadOnlyList<string> pluginNames)
    {
        var data = _store.Serialize(entities);

        foreach (var plugin in Resolve(pluginNames))
        {
            data = plugin.ProcessBeforeSave(data);
        }

        Directory.CreateDirectory(Path.GetDirectoryName(path) ?? ".");
        File.WriteAllBytes(path, data);
    }

    /// <summary>
    /// Applies selected plugins in reverse order because loading reverses the save pipeline.
    /// </summary>
    public List<IEntity> Load(string path, IReadOnlyList<string> pluginNames)
    {
        var data = File.ReadAllBytes(path);

        foreach (var plugin in Resolve(pluginNames).AsEnumerable().Reverse())
        {
            data = plugin.ProcessAfterLoad(data);
        }

        return _store.Deserialize(data);
    }

    private List<IStorageTransformPlugin> Resolve(IReadOnlyList<string> pluginNames)
    {
        var result = new List<IStorageTransformPlugin>();

        foreach (var pluginName in pluginNames)
        {
            if (!_plugins.TryGetValue(pluginName, out var plugin))
            {
                throw new InvalidOperationException($"Storage plugin is not loaded: {pluginName}");
            }

            result.Add(plugin);
        }

        return result;
    }
}
