package triangleplugin;

import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Map;

/**
 * Friend's triangle shape implementation adapted into this project unchanged in behavior.
 */
public class Triangle implements shapes.Shape {
    private final int x1, y1, x2, y2, x3, y3;

    /**
     * Stores the three triangle points.
     */
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    /**
     * Draws the triangle using the Java AWT polygon primitive.
     */
    @Override
    public void draw(Graphics g) {
        int[] xs = {x1, x2, x3};
        int[] ys = {y1, y2, y3};
        g.drawPolygon(new Polygon(xs, ys, 3));
    }

    /**
     * Exposes triangle coordinates so another component can recreate or inspect the shape.
     */
    @Override
    public Map<String, Integer> toParams() {
        return Map.of("x1", x1, "y1", y1, "x2", x2, "y2", y2, "x3", x3, "y3", y3);
    }

    /**
     * Delegates point hit testing to an AWT polygon created from the triangle coordinates.
     */
    @Override
    public boolean contains(int px, int py) {
        Polygon polygon = new Polygon(
            new int[]{x1, x2, x3},
            new int[]{y1, y2, y3}, 3
        );
        return polygon.contains(px, py);
    }

    /**
     * Returns the first point x coordinate.
     */
    public int getX1() { return x1; }

    /**
     * Returns the first point y coordinate.
     */
    public int getY1() { return y1; }

    /**
     * Returns the second point x coordinate.
     */
    public int getX2() { return x2; }

    /**
     * Returns the second point y coordinate.
     */
    public int getY2() { return y2; }

    /**
     * Returns the third point x coordinate.
     */
    public int getX3() { return x3; }

    /**
     * Returns the third point y coordinate.
     */
    public int getY3() { return y3; }
}
