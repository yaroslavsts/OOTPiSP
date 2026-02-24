package shapes;

import java.awt.Graphics2D;

public class Square extends Shape {
    private final int x, y, size;

    public Square(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawRect(x, y, size, size);
    }
}
