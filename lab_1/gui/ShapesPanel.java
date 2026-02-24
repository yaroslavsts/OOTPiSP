package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import shapes.ShapeList;

public class ShapesPanel extends JPanel {
    private final ShapeList shapeList;

    public ShapesPanel(ShapeList shapeList) {
        this.shapeList = shapeList;
        setBackground(java.awt.Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapeList.drawAll((Graphics2D) g);
    }
}
