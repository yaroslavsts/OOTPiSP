using OOTPiSP.Lab1;

var figures = new FigureList();

figures.Add(new Line(10, 20, 90, 20));
figures.Add(new RectangleFigure(15, 25, 120, 60));
figures.Add(new EllipseFigure(40, 30, 80, 50));
figures.Add(new Circle(150, 75, 35));
figures.Add(new Triangle(20, 120, 80, 120, 50, 70));
figures.Add(new Rhombus(200, 120, 70, 45));

Console.WriteLine("Lab 1. Static figure list:");

foreach (var drawingCommand in figures.DrawAll())
{
    Console.WriteLine(drawingCommand);
}
