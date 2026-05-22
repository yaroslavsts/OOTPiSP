package lab1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

abstract class Figure {
    abstract void draw(Graphics g);
}

class LineFigure extends Figure {
    int x1, y1, x2, y2;

    LineFigure(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    void draw(Graphics g) {
        g.drawLine(x1, y1, x2, y2);
    }
}

class RectangleFigure extends Figure {
    int x, y, width, height;

    RectangleFigure(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void draw(Graphics g) {
        g.drawRect(x, y, width, height);
    }
}

class EllipseFigure extends Figure {
    int x, y, width, height;

    EllipseFigure(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void draw(Graphics g) {
        g.drawOval(x, y, width, height);
    }
}

class CircleFigure extends Figure {
    int x, y, radius;

    CircleFigure(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    void draw(Graphics g) {
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}

class TriangleFigure extends Figure {
    int[] xs;
    int[] ys;

    TriangleFigure(int x1, int y1, int x2, int y2, int x3, int y3) {
        xs = new int[] { x1, x2, x3 };
        ys = new int[] { y1, y2, y3 };
    }

    void draw(Graphics g) {
        g.drawPolygon(xs, ys, 3);
    }
}

class RhombusFigure extends Figure {
    int x, y, width, height;

    RhombusFigure(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void draw(Graphics g) {
        int[] xs = { x, x + width / 2, x, x - width / 2 };
        int[] ys = { y - height / 2, y, y + height / 2, y };
        g.drawPolygon(xs, ys, 4);
    }
}

class FigureList {
    List<Figure> figures = new ArrayList<>();

    void add(Figure figure) {
        figures.add(figure);
    }

    void draw(Graphics g) {
        for (Figure figure : figures) {
            figure.draw(g);
        }
    }
}

class DrawPanel extends JPanel {
    FigureList list;

    DrawPanel(FigureList list) {
        this.list = list;
        setBackground(Color.WHITE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        list.draw(g);
    }
}

public class Main {
    public static void main(String[] args) {
        FigureList list = sampleFigures();

        JFrame frame = new JFrame("Lab1 - Inheritance and polymorphism");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 360);
        frame.setLocationRelativeTo(null);
        frame.add(new DrawPanel(list));
        frame.setVisible(true);
    }

    private static FigureList sampleFigures() {
        FigureList list = new FigureList();
        list.add(new LineFigure(20, 30, 180, 30));
        list.add(new RectangleFigure(40, 60, 120, 70));
        list.add(new EllipseFigure(210, 60, 130, 70));
        list.add(new CircleFigure(120, 210, 45));
        list.add(new TriangleFigure(250, 230, 340, 230, 295, 150));
        list.add(new RhombusFigure(420, 180, 100, 80));
        return list;
    }
}
