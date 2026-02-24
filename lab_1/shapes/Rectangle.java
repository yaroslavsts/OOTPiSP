package shapes;

import java.awt.Graphics2D;

public class Rectangle extends Shape {
    private final int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawRect(x, y, width, height);
    }
}
