package shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class ShapeList {
    private final List<Shape> shapes = new ArrayList<>();

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public void drawAll(Graphics2D g) {
        for (Shape s : shapes) {
            s.draw(g);
        }
    }
}
