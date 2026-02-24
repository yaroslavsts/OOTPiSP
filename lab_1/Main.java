import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import gui.ShapesPanel;
import shapes.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShapeList list = new ShapeList();
            list.add(new Rhombus(80, 80, 60, 40));
            list.add(new Circle(220, 100, 50));
            list.add(new Square(320, 50, 80));
            list.add(new Rectangle(40, 200, 120, 80));
            list.add(new Ellipse(220, 220, 70, 45));
            list.add(new Triangle(340, 180, 460, 180, 400, 280));

            JFrame frame = new JFrame("Фигуры");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.add(new ShapesPanel(list));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
