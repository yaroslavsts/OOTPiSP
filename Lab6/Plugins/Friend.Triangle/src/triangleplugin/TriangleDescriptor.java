package triangleplugin;

import core.ShapeDescriptor;
import java.util.Map;

/**
 * Friend's descriptor that knows how to create Triangle instances.
 */
public class TriangleDescriptor implements ShapeDescriptor {
    /**
     * Returns the shape name shown by the friend's plugin architecture.
     */
    @Override
    public String shapeName() {
        return "Triangle";
    }

    /**
     * Returns the six coordinates needed to construct a triangle.
     */
    @Override
    public String[] parameterNames() {
        return new String[]{"x1", "y1", "x2", "y2", "x3", "y3"};
    }

    /**
     * Creates a Triangle from the supplied coordinate map.
     */
    @Override
    public shapes.Shape create(Map<String, Integer> params) {
        return new Triangle(
            params.get("x1"), params.get("y1"),
            params.get("x2"), params.get("y2"),
            params.get("x3"), params.get("y3")
        );
    }
}
