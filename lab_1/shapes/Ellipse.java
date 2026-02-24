package shapes;

import java.awt.Graphics2D;

public class Ellipse extends Shape {
    private final int cx, cy, a, b;

    public Ellipse(int cx, int cy, int a, int b) {
        this.cx = cx;
        this.cy = cy;
        this.a = a;
        this.b = b;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawOval(cx - a, cy - b, 2 * a, 2 * b);
    }
}
