package lab2;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

abstract class Figure {
}

class Line extends Figure {
    int x1, y1, x2, y2;

    Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
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
}

class EllipseFigure extends RectangleFigure {
    EllipseFigure(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}

class Circle extends Figure {
    int x, y, radius;

    Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
}

class Triangle extends Figure {
    int[] xs, ys;

    Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        xs = new int[] { x1, x2, x3 };
        ys = new int[] { y1, y2, y3 };
    }
}

class Rhombus extends RectangleFigure {
    Rhombus(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}

interface Creator {
    String name();

    String help();

    Figure create(int[] n);
}

interface Drawer {
    void draw(Graphics g, Figure figure);
}

class Registry {
    Map<String, Creator> creators = new LinkedHashMap<>();
    Map<Class<?>, Drawer> drawers = new LinkedHashMap<>();

    void add(Creator creator) {
        creators.put(creator.name(), creator);
    }

    void addDrawer(Class<?> type, Drawer drawer) {
        drawers.put(type, drawer);
    }

    Figure create(String type, String numbers) {
        String[] parts = numbers.trim().split("\\s+");
        int[] n = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            n[i] = Integer.parseInt(parts[i]);
        }
        return creators.get(type).create(n);
    }

    void draw(Graphics g, Figure figure) {
        drawers.get(figure.getClass()).draw(g, figure);
    }

    static Registry basic() {
        Registry r = new Registry();
        r.add(new SimpleCreator("line", "x1 y1 x2 y2", n -> new Line(n[0], n[1], n[2], n[3])));
        r.add(new SimpleCreator("rectangle", "x y width height", n -> new RectangleFigure(n[0], n[1], n[2], n[3])));
        r.add(new SimpleCreator("ellipse", "x y width height", n -> new EllipseFigure(n[0], n[1], n[2], n[3])));
        r.add(new SimpleCreator("circle", "x y radius", n -> new Circle(n[0], n[1], n[2])));
        r.add(new SimpleCreator("triangle", "x1 y1 x2 y2 x3 y3", n -> new Triangle(n[0], n[1], n[2], n[3], n[4], n[5])));
        r.add(new SimpleCreator("rhombus", "centerX centerY width height", n -> new Rhombus(n[0], n[1], n[2], n[3])));

        r.addDrawer(Line.class, (g, f) -> {
            Line x = (Line) f;
            g.drawLine(x.x1, x.y1, x.x2, x.y2);
        });
        r.addDrawer(RectangleFigure.class, (g, f) -> {
            RectangleFigure x = (RectangleFigure) f;
            g.drawRect(x.x, x.y, x.width, x.height);
        });
        r.addDrawer(EllipseFigure.class, (g, f) -> {
            EllipseFigure x = (EllipseFigure) f;
            g.drawOval(x.x, x.y, x.width, x.height);
        });
        r.addDrawer(Circle.class, (g, f) -> {
            Circle x = (Circle) f;
            g.drawOval(x.x - x.radius, x.y - x.radius, x.radius * 2, x.radius * 2);
        });
        r.addDrawer(Triangle.class, (g, f) -> {
            Triangle x = (Triangle) f;
            g.drawPolygon(x.xs, x.ys, 3);
        });
        r.addDrawer(Rhombus.class, (g, f) -> {
            Rhombus x = (Rhombus) f;
            int[] xs = { x.x, x.x + x.width / 2, x.x, x.x - x.width / 2 };
            int[] ys = { x.y - x.height / 2, x.y, x.y + x.height / 2, x.y };
            g.drawPolygon(xs, ys, 4);
        });
        return r;
    }
}

interface FigureMaker {
    Figure make(int[] n);
}

class SimpleCreator implements Creator {
    private final String name;
    private final String help;
    private final FigureMaker maker;

    SimpleCreator(String name, String help, FigureMaker maker) {
        this.name = name;
        this.help = help;
        this.maker = maker;
    }

    public String name() {
        return name;
    }

    public String help() {
        return help;
    }

    public Figure create(int[] n) {
        return maker.make(n);
    }

    public String toString() {
        return name;
    }
}

class EditorPanel extends JPanel {
    Registry registry;
    List<Figure> figures = new ArrayList<>();

    EditorPanel(Registry registry) {
        this.registry = registry;
        setBackground(Color.WHITE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Figure figure : figures) {
            registry.draw(g, figure);
        }
    }
}

class EditorFrame extends JFrame {
    EditorFrame() {
        Registry registry = Registry.basic();
        EditorPanel canvas = new EditorPanel(registry);
        JComboBox<Creator> types = new JComboBox<>(registry.creators.values().toArray(new Creator[0]));
        JTextField params = new JTextField("20 20 120 70", 24);
        JLabel help = new JLabel();

        types.addActionListener(e -> help.setText(((Creator) types.getSelectedItem()).help()));
        help.setText(((Creator) types.getSelectedItem()).help());

        JButton add = new JButton("Add");
        add.addActionListener(e -> {
            try {
                Creator creator = (Creator) types.getSelectedItem();
                canvas.figures.add(registry.create(creator.name(), params.getText()));
                canvas.repaint();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error.getMessage());
            }
        });

        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            canvas.figures.clear();
            canvas.repaint();
        });

        JPanel top = new JPanel();
        top.add(new JLabel("Figure:"));
        top.add(types);
        top.add(new JLabel("Numbers:"));
        top.add(params);
        top.add(add);
        top.add(clear);
        top.add(help);

        setTitle("Lab2 - Simple graphic editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 520);
        setLocationRelativeTo(null);
        add(top, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
    }
}

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) {
            Registry registry = Registry.basic();
            Figure figure = registry.create("rectangle", "15 25 120 60");
            System.out.println("Created: " + figure.getClass().getSimpleName());
            return;
        }
        new EditorFrame().setVisible(true);
    }
}
