using OOTPiSP.Common.ConsoleUi;
using OOTPiSP.Common.Domain;
using OOTPiSP.Common.Paths;
using OOTPiSP.Common.Plugins;
using OOTPiSP.Common.Storage;

var registry = AthleteRegistryFactory.Create();
var pluginDirectory = RepositoryPaths.PluginDirectory();
var entityPlugins = new PluginLoader().Load<IEntityPlugin>(pluginDirectory);

foreach (var plugin in entityPlugins)
{
    plugin.Register(registry);
}

var document = new EntityDocument();
var console = new EntityConsole(registry, document, new JsonEntityStore(registry));

Console.WriteLine("Lab 4. Dynamic hierarchy plugins.");
Console.WriteLine($"Plugin directory: {pluginDirectory}");
Console.WriteLine(entityPlugins.Count == 0
    ? "No entity plugins were loaded. Build the solution first."
    : $"Loaded entity plugins: {string.Join(", ", entityPlugins.Select(plugin => plugin.Name))}");

if (args.Contains("--interactive"))
{
    while (true)
    {
        Console.Write("> ");
        var command = Console.ReadLine();

        if (command is null || command.Equals("exit", StringComparison.OrdinalIgnoreCase))
        {
            break;
        }

        console.Execute(command);
    }
}
else
{
    var commands = new[]
    {
        "types",
        "add cyclist Name=Pavel Age=21 Gender=male Medals=3 YearsInSport=7 BikeType=road DistanceKm=120",
        "list"
    };

    foreach (var command in commands)
    {
        Console.WriteLine($"> {command}");
        console.Execute(command);
    }
}
