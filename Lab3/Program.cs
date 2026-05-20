using OOTPiSP.Common.ConsoleUi;
using OOTPiSP.Common.Domain;
using OOTPiSP.Common.Paths;
using OOTPiSP.Common.Storage;

var registry = AthleteRegistryFactory.Create();
var document = new EntityDocument();
var store = new JsonEntityStore(registry);
var console = new EntityConsole(registry, document, store);

if (args.Contains("--interactive"))
{
    Console.WriteLine("Lab 3. Serialization of athletes. Type 'help' or 'exit'.");

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
    var file = Path.Combine(RepositoryPaths.Artifacts(), "lab3-athletes.json");
    var demoCommands = new[]
    {
        "add boxer Name=Ivan Age=22 Gender=male Medals=4 YearsInSport=8 WeightCategory=75",
        "add swimmer Name=Anna Age=20 Gender=female Medals=6 YearsInSport=10 Style=freestyle BestTime=00:54",
        "add football-player Name=Max Age=24 Gender=male Medals=2 YearsInSport=12 Position=forward Club=BSUIR Goals=31",
        "list",
        $"save {file}",
        $"load {file}",
        "list"
    };

    Console.WriteLine("Lab 3. JSON serialization demo:");

    foreach (var command in demoCommands)
    {
        Console.WriteLine($"> {command}");
        console.Execute(command);
    }

    Console.WriteLine("Run with --interactive to manage objects manually.");
}
