package shapes;

import java.awt.Graphics;
import java.util.Map;

/**
 * Defines the shape contract expected by the friend's triangle plugin.
 */
public interface Shape {
    /**
     * Draws the shape on the supplied graphics context.
     */
    void draw(Graphics g);

    /**
     * Returns the parameters that are needed to recreate the shape.
     */
    Map<String, Integer> toParams();

    /**
     * Checks whether the supplied point is inside the shape.
     */
    boolean contains(int px, int py);
}
