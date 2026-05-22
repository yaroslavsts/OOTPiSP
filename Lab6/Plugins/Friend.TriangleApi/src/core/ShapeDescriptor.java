package core;

import java.util.Map;

/**
 * Describes a shape plugin from the friend's graphic editor architecture.
 */
public interface ShapeDescriptor {
    /**
     * Returns the human-readable shape name.
     */
    String shapeName();

    /**
     * Returns the ordered parameter names required to create the shape.
     */
    String[] parameterNames();

    /**
     * Creates a shape instance from integer parameters.
     */
    shapes.Shape create(Map<String, Integer> params);
}
