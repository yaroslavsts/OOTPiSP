using OOTPiSP.Lab2;

var registry = ScriptEditor.CreateDefaultRegistry();
var document = new EditorDocument();
var editor = new ScriptEditor(registry, document);

if (args.Contains("--interactive"))
{
    Console.WriteLine("Lab 2. Script graphic editor. Type 'help' or 'exit'.");

    while (true)
    {
        Console.Write("> ");
        var command = Console.ReadLine();

        if (command is null || command.Equals("exit", StringComparison.OrdinalIgnoreCase))
        {
            break;
        }

        editor.Execute(command);
    }
}
else
{
    Console.WriteLine("Lab 2. Demo script:");

    var demoCommands = new[]
    {
        "add line 10 10 100 10",
        "add rectangle 20 25 80 40",
        "add circle 120 90 30",
        "add triangle 10 120 80 120 45 75",
        "draw"
    };

    foreach (var command in demoCommands)
    {
        Console.WriteLine($"> {command}");
        editor.Execute(command);
    }

    Console.WriteLine("Run with --interactive to enter commands manually.");
}
