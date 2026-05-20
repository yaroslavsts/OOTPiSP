namespace OOTPiSP.Common.Domain;

public enum FieldKind
{
    String,
    Integer
}

public sealed class EntityField
{
    public EntityField(
        string name,
        FieldKind kind,
        Func<IEntity, string> read,
        Action<IEntity, string> write)
    {
        Name = name;
        Kind = kind;
        Read = read;
        Write = write;
    }

    public string Name { get; }
    public FieldKind Kind { get; }
    public Func<IEntity, string> Read { get; }
    public Action<IEntity, string> Write { get; }
}

public sealed class EntityDefinition
{
    private readonly Func<IReadOnlyDictionary<string, string>, IEntity> _factory;

    public EntityDefinition(
        string typeName,
        string displayName,
        IReadOnlyList<EntityField> fields,
        Func<IReadOnlyDictionary<string, string>, IEntity> factory)
    {
        TypeName = typeName;
        DisplayName = displayName;
        Fields = fields;
        _factory = factory;
    }

    public string TypeName { get; }
    public string DisplayName { get; }
    public IReadOnlyList<EntityField> Fields { get; }

    /// <summary>
    /// Creates an entity by using the registered factory instead of type checks.
    /// </summary>
    public IEntity Create(IReadOnlyDictionary<string, string> values) => _factory(values);
}

public sealed class EntityRegistry
{
    private readonly Dictionary<string, EntityDefinition> _definitions = new(StringComparer.OrdinalIgnoreCase);

    public IEnumerable<EntityDefinition> Definitions => _definitions.Values.OrderBy(definition => definition.TypeName);

    public void Register(EntityDefinition definition) => _definitions[definition.TypeName] = definition;

    public EntityDefinition Get(string typeName)
    {
        if (!_definitions.TryGetValue(typeName, out var definition))
        {
            throw new InvalidOperationException($"Entity type is not registered: {typeName}");
        }

        return definition;
    }

    public EntityDefinition Get(IEntity entity) => Get(entity.TypeName);

    public IEntity Create(string typeName, IReadOnlyDictionary<string, string> values) => Get(typeName).Create(values);
}

public static class FieldValueReader
{
    public static string Text(this IReadOnlyDictionary<string, string> values, string name, string defaultValue = "")
    {
        return values.TryGetValue(name, out var value) ? value : defaultValue;
    }

    public static int Integer(this IReadOnlyDictionary<string, string> values, string name, int defaultValue = 0)
    {
        return values.TryGetValue(name, out var value) && int.TryParse(value, out var number)
            ? number
            : defaultValue;
    }
}
