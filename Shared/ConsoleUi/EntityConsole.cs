using OOTPiSP.Common.Domain;
using OOTPiSP.Common.Storage;

namespace OOTPiSP.Common.ConsoleUi;

public sealed class EntityConsole
{
    private readonly EntityRegistry _registry;
    private readonly EntityDocument _document;
    private readonly JsonEntityStore _store;
    private readonly Dictionary<string, Action<string[]>> _commands;

    public EntityConsole(EntityRegistry registry, EntityDocument document, JsonEntityStore store)
    {
        _registry = registry;
        _document = document;
        _store = store;
        _commands = new Dictionary<string, Action<string[]>>(StringComparer.OrdinalIgnoreCase)
        {
            ["types"] = Types,
            ["add"] = Add,
            ["list"] = List,
            ["edit"] = Edit,
            ["remove"] = Remove,
            ["save"] = Save,
            ["load"] = Load,
            ["help"] = Help
        };
    }

    public void Execute(string commandLine)
    {
        var parts = commandLine.Split(' ', StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries);

        if (parts.Length == 0)
        {
            return;
        }

        if (!_commands.TryGetValue(parts[0], out var command))
        {
            Console.WriteLine($"Unknown command: {parts[0]}");
            return;
        }

        command(parts.Skip(1).ToArray());
    }

    private void Types(string[] _)
    {
        foreach (var definition in _registry.Definitions)
        {
            var fields = string.Join(" ", definition.Fields.Select(field => $"{field.Name}=..."));
            Console.WriteLine($"{definition.TypeName}: add {definition.TypeName} {fields}");
        }
    }

    private void Add(string[] args)
    {
        if (args.Length < 1)
        {
            Console.WriteLine("Usage: add <type> Field=Value");
            return;
        }

        try
        {
            var entity = _registry.Create(args[0], ParsePairs(args.Skip(1)));
            _document.Add(entity);
            Console.WriteLine($"Added {entity.TypeName}: {entity.Id}");
        }
        catch (Exception error)
        {
            Console.WriteLine($"Cannot add entity: {error.Message}");
        }
    }

    private void List(string[] _)
    {
        foreach (var entity in _document.Items)
        {
            Console.WriteLine($"{entity.Id} [{entity.TypeName}] {entity.Summary()}");
        }
    }

    private void Edit(string[] args)
    {
        if (args.Length < 2)
        {
            Console.WriteLine("Usage: edit <id-prefix> Field=Value");
            return;
        }

        var entity = _document.Find(args[0]);

        if (entity is null)
        {
            Console.WriteLine("Entity was not found.");
            return;
        }

        try
        {
            var definition = _registry.Get(entity);
            var updates = ParsePairs(args.Skip(1));
            var fieldMap = definition.Fields.ToDictionary(field => field.Name, StringComparer.OrdinalIgnoreCase);

            foreach (var update in updates)
            {
                if (fieldMap.TryGetValue(update.Key, out var field))
                {
                    field.Write(entity, update.Value);
                }
            }

            Console.WriteLine($"Updated {entity.Id}");
        }
        catch (Exception error)
        {
            Console.WriteLine($"Cannot edit entity: {error.Message}");
        }
    }

    private void Remove(string[] args)
    {
        if (args.Length != 1)
        {
            Console.WriteLine("Usage: remove <id-prefix>");
            return;
        }

        Console.WriteLine(_document.Remove(args[0]) ? "Removed." : "Entity was not found.");
    }

    private void Save(string[] args)
    {
        if (args.Length != 1)
        {
            Console.WriteLine("Usage: save <file>");
            return;
        }

        _store.Save(args[0], _document.Items);
        Console.WriteLine($"Saved {_document.Items.Count} item(s) to {args[0]}");
    }

    private void Load(string[] args)
    {
        if (args.Length != 1)
        {
            Console.WriteLine("Usage: load <file>");
            return;
        }

        _document.ReplaceAll(_store.Load(args[0]));
        Console.WriteLine($"Loaded {_document.Items.Count} item(s) from {args[0]}");
    }

    private void Help(string[] _)
    {
        Console.WriteLine("Commands: types, add, list, edit, remove, save, load, help, exit");
    }

    public static Dictionary<string, string> ParsePairs(IEnumerable<string> args)
    {
        return args
            .Select(argument => argument.Split('=', 2))
            .Where(parts => parts.Length == 2)
            .ToDictionary(parts => parts[0], parts => parts[1], StringComparer.OrdinalIgnoreCase);
    }
}
