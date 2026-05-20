using System.Text;
using System.Text.Json;
using OOTPiSP.Common.Domain;

namespace OOTPiSP.Common.Storage;

public sealed class JsonEntityStore
{
    private readonly EntityRegistry _registry;
    private readonly JsonSerializerOptions _options = new() { WriteIndented = true };

    public JsonEntityStore(EntityRegistry registry)
    {
        _registry = registry;
    }

    public byte[] Serialize(IReadOnlyList<IEntity> entities)
    {
        var document = new StoredDocument
        {
            Items = entities.Select(ToStoredEntity).ToList()
        };

        return Encoding.UTF8.GetBytes(JsonSerializer.Serialize(document, _options));
    }

    public List<IEntity> Deserialize(byte[] bytes)
    {
        var json = Encoding.UTF8.GetString(bytes);
        var document = JsonSerializer.Deserialize<StoredDocument>(json, _options) ?? new StoredDocument();

        return document.Items.Select(item =>
        {
            var entity = _registry.Create(item.Type, item.Properties);
            entity.Id = item.Id;
            return entity;
        }).ToList();
    }

    public void Save(string path, IReadOnlyList<IEntity> entities)
    {
        Directory.CreateDirectory(Path.GetDirectoryName(path) ?? ".");
        File.WriteAllBytes(path, Serialize(entities));
    }

    public List<IEntity> Load(string path) => Deserialize(File.ReadAllBytes(path));

    private StoredEntity ToStoredEntity(IEntity entity)
    {
        var definition = _registry.Get(entity);
        var properties = definition.Fields.ToDictionary(field => field.Name, field => field.Read(entity));
        return new StoredEntity(entity.TypeName, entity.Id, properties);
    }

    private sealed class StoredDocument
    {
        public int Version { get; set; } = 1;
        public List<StoredEntity> Items { get; set; } = new();
    }

    private sealed record StoredEntity(string Type, Guid Id, Dictionary<string, string> Properties);
}
