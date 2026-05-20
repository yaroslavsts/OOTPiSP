using OOTPiSP.Common.ConsoleUi;
using OOTPiSP.Common.Domain;
using OOTPiSP.Common.Paths;
using OOTPiSP.Common.Plugins;
using OOTPiSP.Common.Storage;

var registry = AthleteRegistryFactory.Create();
var document = new EntityDocument();
var storagePlugins = new PluginLoader().Load<IStorageTransformPlugin>(RepositoryPaths.PluginDirectory());
var pipeline = new StoragePipeline(new JsonEntityStore(registry), storagePlugins);

document.Add(registry.Create("skater", new Dictionary<string, string>
{
    ["Name"] = "Daria",
    ["Age"] = "19",
    ["Gender"] = "female",
    ["Medals"] = "5",
    ["YearsInSport"] = "11",
    ["Discipline"] = "speed",
    ["BestScore"] = "98"
}));

Console.WriteLine("Lab 6. Patterns and adapter plugin.");
Console.WriteLine($"Loaded storage plugins: {string.Join(", ", storagePlugins.Select(plugin => plugin.Name))}");

var file = Path.Combine(RepositoryPaths.Artifacts(), "lab6-athletes.dat");
var selectedPlugins = new[] { "legacy-base64-adapter", "checksum" };

pipeline.Save(file, document.Items, selectedPlugins);
Console.WriteLine($"Saved with adapted plugin [{string.Join(", ", selectedPlugins)}]: {file}");

document.ReplaceAll(pipeline.Load(file, selectedPlugins));
Console.WriteLine("Loaded after adapter plugin:");

foreach (var entity in document.Items)
{
    Console.WriteLine($"{entity.Id} [{entity.TypeName}] {entity.Summary()}");
}

Console.WriteLine();
Console.WriteLine("Patterns used:");
Console.WriteLine("Adapter: Adapter.LegacyBase64 wraps a friend's incompatible base64 plugin into IStorageTransformPlugin.");
Console.WriteLine("Factory Method: EntityDefinition creates registered entities from field values without switch by type.");
Console.WriteLine("Strategy: storage transform plugins are interchangeable processing strategies selected at runtime.");
