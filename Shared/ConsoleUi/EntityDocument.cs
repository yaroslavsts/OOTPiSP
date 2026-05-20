using OOTPiSP.Common.Domain;

namespace OOTPiSP.Common.ConsoleUi;

public sealed class EntityDocument
{
    private readonly List<IEntity> _items = new();

    public IReadOnlyList<IEntity> Items => _items;

    public void Add(IEntity entity) => _items.Add(entity);

    public void ReplaceAll(IEnumerable<IEntity> entities)
    {
        _items.Clear();
        _items.AddRange(entities);
    }

    public bool Remove(string idPrefix)
    {
        var entity = Find(idPrefix);

        if (entity is null)
        {
            return false;
        }

        _items.Remove(entity);
        return true;
    }

    public IEntity? Find(string idPrefix)
    {
        return _items.FirstOrDefault(entity => entity.Id.ToString().StartsWith(idPrefix, StringComparison.OrdinalIgnoreCase));
    }
}
