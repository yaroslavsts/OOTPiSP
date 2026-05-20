namespace OOTPiSP.Lab1;

/// <summary>
/// Base class for all figures in the first lab.
/// </summary>
public abstract class Figure
{
    protected Figure(string name)
    {
        Name = name;
    }

    public string Name { get; }

    /// <summary>
    /// Returns a text representation of a drawing command.
    /// </summary>
    public abstract string Draw();
}

public sealed class Line : Figure
{
    public Line(int x1, int y1, int x2, int y2) : base("Line")
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

    public override string Draw() => $"Line({X1}, {Y1}, {X2}, {Y2})";
}

public sealed class RectangleFigure : Figure
{
    public RectangleFigure(int x, int y, int width, int height) : base("Rectangle")
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

    public override string Draw() => $"Rectangle({X}, {Y}, {Width}, {Height})";
}

public sealed class EllipseFigure : Figure
{
    public EllipseFigure(int x, int y, int width, int height) : base("Ellipse")
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

    public override string Draw() => $"Ellipse({X}, {Y}, {Width}, {Height})";
}

public sealed class Circle : Figure
{
    public Circle(int centerX, int centerY, int radius) : base("Circle")
    {
        CenterX = centerX;
        CenterY = centerY;
        Radius = radius;
    }

    public int CenterX { get; }
    public int CenterY { get; }
    public int Radius { get; }

    public override string Draw() => $"Circle({CenterX}, {CenterY}, {Radius})";
}

public sealed class Triangle : Figure
{
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) : base("Triangle")
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

    public override string Draw() => $"Triangle({X1}, {Y1}, {X2}, {Y2}, {X3}, {Y3})";
}

public sealed class Rhombus : Figure
{
    public Rhombus(int centerX, int centerY, int width, int height) : base("Rhombus")
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

    public override string Draw() => $"Rhombus({CenterX}, {CenterY}, {Width}, {Height})";
}

/// <summary>
/// Stores figures and draws them through polymorphic calls.
/// </summary>
public sealed class FigureList
{
    private readonly List<Figure> _figures = new();

    public void Add(Figure figure) => _figures.Add(figure);

    /// <summary>
    /// Draws every figure without knowing the concrete type.
    /// </summary>
    public IReadOnlyList<string> DrawAll() => _figures.Select(figure => figure.Draw()).ToList();
}
