package shapes;

import java.awt.Graphics2D;

public class Rhombus extends Shape {
    private final int cx, cy, a, b;

    public Rhombus(int cx, int cy, int a, int b) {
        this.cx = cx;
        this.cy = cy;
        this.a = a;
        this.b = b;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawPolygon(
            new int[] { cx, cx + a, cx, cx - a },
            new int[] { cy - b, cy, cy + b, cy },
            4
        );
    }
}
