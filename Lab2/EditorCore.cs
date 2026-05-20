namespace OOTPiSP.Lab2;

public sealed record CreationContext(IReadOnlyList<int> Numbers);

/// <summary>
/// Figure data classes contain geometry only and do not know how to draw themselves.
/// </summary>
public abstract class Figure
{
    protected Figure(string typeName)
    {
        TypeName = typeName;
    }

    public Guid Id { get; } = Guid.NewGuid();
    public string TypeName { get; }
}

public sealed class Line : Figure
{
    public Line(int x1, int y1, int x2, int y2) : base("line")
    {
        X1 = x1;
        Y1 = y1;
        X2 = x2;
        Y2 = y2;
    }

    public int X1 { get; }
    public int Y1 { get; }
    public int X2 { get; }
    public int Y2 { get; }
}

public sealed class RectangleFigure : Figure
{
    public RectangleFigure(int x, int y, int width, int height) : base("rectangle")
    {
        X = x;
        Y = y;
        Width = width;
        Height = height;
    }

    public int X { get; }
    public int Y { get; }
    public int Width { get; }
    public int Height { get; }
}

public sealed class EllipseFigure : Figure
{
    public EllipseFigure(int x, int y, int width, int height) : base("ellipse")
    {
        X = x;
        Y = y;
        Width = width;
        Height = height;
    }

    public int X { get; }
    public int Y { get; }
    public int Width { get; }
    public int Height { get; }
}

public sealed class Circle : Figure
{
    public Circle(int centerX, int centerY, int radius) : base("circle")
    {
        CenterX = centerX;
        CenterY = centerY;
        Radius = radius;
    }

    public int CenterX { get; }
    public int CenterY { get; }
    public int Radius { get; }
}

public sealed class Triangle : Figure
{
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) : base("triangle")
    {
        X1 = x1;
        Y1 = y1;
        X2 = x2;
        Y2 = y2;
        X3 = x3;
        Y3 = y3;
    }

    public int X1 { get; }
    public int Y1 { get; }
    public int X2 { get; }
    public int Y2 { get; }
    public int X3 { get; }
    public int Y3 { get; }
}

public sealed class Rhombus : Figure
{
    public Rhombus(int centerX, int centerY, int width, int height) : base("rhombus")
    {
        CenterX = centerX;
        CenterY = centerY;
        Width = width;
        Height = height;
    }

    public int CenterX { get; }
    public int CenterY { get; }
    public int Width { get; }
    public int Height { get; }
}

public interface IFigureCreator
{
    string TypeName { get; }
    string Syntax { get; }
    Figure Create(CreationContext context);
}

public interface IFigureRenderer
{
    Type FigureType { get; }
    string Render(Figure figure);
}

public sealed class FigureCreator<TFigure> : IFigureCreator where TFigure : Figure
{
    private readonly Func<IReadOnlyList<int>, TFigure> _factory;

    public FigureCreator(string typeName, string syntax, Func<IReadOnlyList<int>, TFigure> factory)
    {
        TypeName = typeName;
        Syntax = syntax;
        _factory = factory;
    }

    public string TypeName { get; }
    public string Syntax { get; }

    /// <summary>
    /// Creates a figure from script numbers. Validation is kept near each creator.
    /// </summary>
    public Figure Create(CreationContext context) => _factory(context.Numbers);
}

public sealed class FigureRenderer<TFigure> : IFigureRenderer where TFigure : Figure
{
    private readonly Func<TFigure, string> _renderer;

    public FigureRenderer(Func<TFigure, string> renderer)
    {
        _renderer = renderer;
    }

    public Type FigureType => typeof(TFigure);

    public string Render(Figure figure) => _renderer((TFigure)figure);
}

public sealed class FigureRegistry
{
    private readonly Dictionary<string, IFigureCreator> _creators = new(StringComparer.OrdinalIgnoreCase);
    private readonly Dictionary<Type, IFigureRenderer> _renderers = new();

    public IEnumerable<IFigureCreator> Creators => _creators.Values.OrderBy(creator => creator.TypeName);

    public void RegisterCreator(IFigureCreator creator) => _creators[creator.TypeName] = creator;

    public void RegisterRenderer(IFigureRenderer renderer) => _renderers[renderer.FigureType] = renderer;

    /// <summary>
    /// Creates figures through the registry, so new figure types do not require if/switch changes.
    /// </summary>
    public Figure Create(string typeName, IReadOnlyList<int> numbers)
    {
        if (!_creators.TryGetValue(typeName, out var creator))
        {
            throw new InvalidOperationException($"Unknown figure type: {typeName}");
        }

        return creator.Create(new CreationContext(numbers));
    }

    public string Render(Figure figure)
    {
        if (!_renderers.TryGetValue(figure.GetType(), out var renderer))
        {
            throw new InvalidOperationException($"Renderer is not registered for {figure.TypeName}");
        }

        return renderer.Render(figure);
    }
}

public sealed class EditorDocument
{
    private readonly List<Figure> _figures = new();

    public IReadOnlyList<Figure> Figures => _figures;

    public void Add(Figure figure) => _figures.Add(figure);

    public void Clear() => _figures.Clear();
}

public sealed class ScriptEditor
{
    private readonly FigureRegistry _registry;
    private readonly EditorDocument _document;
    private readonly Dictionary<string, Action<string[]>> _commands;

    public ScriptEditor(FigureRegistry registry, EditorDocument document)
    {
        _registry = registry;
        _document = document;
        _commands = new Dictionary<string, Action<string[]>>(StringComparer.OrdinalIgnoreCase)
        {
            ["add"] = AddFigure,
            ["types"] = PrintTypes,
            ["list"] = ListFigures,
            ["draw"] = DrawFigures,
            ["clear"] = Clear,
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

    public static FigureRegistry CreateDefaultRegistry()
    {
        var registry = new FigureRegistry();

        registry.RegisterCreator(new FigureCreator<Line>("line", "line x1 y1 x2 y2", Require(4, values => new Line(values[0], values[1], values[2], values[3]))));
        registry.RegisterCreator(new FigureCreator<RectangleFigure>("rectangle", "rectangle x y width height", Require(4, values => new RectangleFigure(values[0], values[1], values[2], values[3]))));
        registry.RegisterCreator(new FigureCreator<EllipseFigure>("ellipse", "ellipse x y width height", Require(4, values => new EllipseFigure(values[0], values[1], values[2], values[3]))));
        registry.RegisterCreator(new FigureCreator<Circle>("circle", "circle centerX centerY radius", Require(3, values => new Circle(values[0], values[1], values[2]))));
        registry.RegisterCreator(new FigureCreator<Triangle>("triangle", "triangle x1 y1 x2 y2 x3 y3", Require(6, values => new Triangle(values[0], values[1], values[2], values[3], values[4], values[5]))));
        registry.RegisterCreator(new FigureCreator<Rhombus>("rhombus", "rhombus centerX centerY width height", Require(4, values => new Rhombus(values[0], values[1], values[2], values[3]))));

        registry.RegisterRenderer(new FigureRenderer<Line>(line => $"Line({line.X1}, {line.Y1}, {line.X2}, {line.Y2})"));
        registry.RegisterRenderer(new FigureRenderer<RectangleFigure>(rectangle => $"Rectangle({rectangle.X}, {rectangle.Y}, {rectangle.Width}, {rectangle.Height})"));
        registry.RegisterRenderer(new FigureRenderer<EllipseFigure>(ellipse => $"Ellipse({ellipse.X}, {ellipse.Y}, {ellipse.Width}, {ellipse.Height})"));
        registry.RegisterRenderer(new FigureRenderer<Circle>(circle => $"Circle({circle.CenterX}, {circle.CenterY}, {circle.Radius})"));
        registry.RegisterRenderer(new FigureRenderer<Triangle>(triangle => $"Triangle({triangle.X1}, {triangle.Y1}, {triangle.X2}, {triangle.Y2}, {triangle.X3}, {triangle.Y3})"));
        registry.RegisterRenderer(new FigureRenderer<Rhombus>(rhombus => $"Rhombus({rhombus.CenterX}, {rhombus.CenterY}, {rhombus.Width}, {rhombus.Height})"));

        return registry;
    }

    private static Func<IReadOnlyList<int>, TFigure> Require<TFigure>(int count, Func<IReadOnlyList<int>, TFigure> factory)
    {
        return values =>
        {
            if (values.Count != count)
            {
                throw new ArgumentException($"Expected {count} number(s), got {values.Count}.");
            }

            return factory(values);
        };
    }

    private void AddFigure(string[] args)
    {
        if (args.Length < 2)
        {
            Console.WriteLine("Usage: add <type> <numbers>");
            return;
        }

        try
        {
            var numbers = args.Skip(1).Select(int.Parse).ToArray();
            var figure = _registry.Create(args[0], numbers);
            _document.Add(figure);
            Console.WriteLine($"Added {figure.TypeName}: {figure.Id}");
        }
        catch (Exception error)
        {
            Console.WriteLine($"Cannot add figure: {error.Message}");
        }
    }

    private void PrintTypes(string[] _)
    {
        foreach (var creator in _registry.Creators)
        {
            Console.WriteLine(creator.Syntax);
        }
    }

    private void ListFigures(string[] _)
    {
        foreach (var figure in _document.Figures)
        {
            Console.WriteLine($"{figure.Id} {figure.TypeName}");
        }
    }

    private void DrawFigures(string[] _)
    {
        foreach (var figure in _document.Figures)
        {
            Console.WriteLine(_registry.Render(figure));
        }
    }

    private void Clear(string[] _)
    {
        _document.Clear();
        Console.WriteLine("Document cleared.");
    }

    private void Help(string[] _)
    {
        Console.WriteLine("Commands: types, add <type> <numbers>, list, draw, clear, help, exit");
    }
}
