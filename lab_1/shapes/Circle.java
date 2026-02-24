package shapes;

import java.awt.Graphics2D;

public class Circle extends Shape {
    private final int cx, cy, r;

    public Circle(int cx, int cy, int r) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawOval(cx - r, cy - r, 2 * r, 2 * r);
    }
}
