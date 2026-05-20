using OOTPiSP.Common.ConsoleUi;
using OOTPiSP.Common.Domain;
using OOTPiSP.Common.Paths;
using OOTPiSP.Common.Plugins;
using OOTPiSP.Common.Storage;

var registry = AthleteRegistryFactory.Create();
var document = new EntityDocument();
var storagePlugins = new PluginLoader().Load<IStorageTransformPlugin>(RepositoryPaths.PluginDirectory());
var pipeline = new StoragePipeline(new JsonEntityStore(registry), storagePlugins);

document.Add(registry.Create("boxer", new Dictionary<string, string>
{
    ["Name"] = "Ivan",
    ["Age"] = "22",
    ["Gender"] = "male",
    ["Medals"] = "4",
    ["YearsInSport"] = "8",
    ["WeightCategory"] = "75"
}));

Console.WriteLine("Lab 5. Functional storage plugins.");
Console.WriteLine(storagePlugins.Count == 0
    ? "No storage plugins were loaded. Build the solution first."
    : $"Loaded storage plugins: {string.Join(", ", storagePlugins.Select(plugin => plugin.Name))}");

var file = Path.Combine(RepositoryPaths.Artifacts(), "lab5-athletes.dat");
var selectedPlugins = new[] { "gzip", "checksum" };

pipeline.Save(file, document.Items, selectedPlugins);
Console.WriteLine($"Saved with plugins [{string.Join(", ", selectedPlugins)}]: {file}");

document.ReplaceAll(pipeline.Load(file, selectedPlugins));
Console.WriteLine("Loaded after reverse processing:");

foreach (var entity in document.Items)
{
    Console.WriteLine($"{entity.Id} [{entity.TypeName}] {entity.Summary()}");
}

Console.WriteLine("Additional plugin settings are not implemented because they are an optional 10-point task.");
