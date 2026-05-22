package plugins.triangleadapter;

import core.ShapeDescriptor;
import shared.StoragePlugin;
import shapes.Shape;
import triangleplugin.TriangleDescriptor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Adapts the friend's triangle shape plugin to the StoragePlugin interface used by Lab 5.
 */
public class TriangleStorageAdapter implements StoragePlugin {
    private final ShapeDescriptor descriptor = new TriangleDescriptor();
    private final Shape triangle = descriptor.create(defaultTriangleParams());

    /**
     * Returns the storage strategy name that appears in the encryption selection UI.
     */
    public String name() {
        return "triangle-shape-adapter";
    }

    /**
     * Applies a reversible triangle-based byte mask before the data is written.
     */
    public byte[] beforeSave(byte[] data) {
        return transform(data);
    }

    /**
     * Applies the same reversible triangle-based byte mask after the data is read.
     */
    public byte[] afterLoad(byte[] data) {
        return transform(data);
    }

    /**
     * Uses the friend's triangle hit-test and parameters to build a deterministic XOR mask.
     */
    private byte[] transform(byte[] data) {
        byte[] result = new byte[data.length];
        int seed = parameterSeed();

        for (int i = 0; i < data.length; i++) {
            int x = i % 9;
            int y = (i / 9) % 8;
            int mask = triangle.contains(x, y) ? 0x5A : 0x33;
            result[i] = (byte) (data[i] ^ mask ^ ((seed + i) & 0x0F));
        }

        return result;
    }

    /**
     * Builds stable default coordinates for the triangle supplied by the friend's descriptor.
     */
    private static Map<String, Integer> defaultTriangleParams() {
        Map<String, Integer> params = new LinkedHashMap<>();
        params.put("x1", 0);
        params.put("y1", 0);
        params.put("x2", 8);
        params.put("y2", 0);
        params.put("x3", 4);
        params.put("y3", 7);
        return params;
    }

    /**
     * Derives a stable seed from the friend's descriptor order and the triangle parameters.
     */
    private int parameterSeed() {
        Map<String, Integer> params = triangle.toParams();
        int seed = 17;

        for (String name : descriptor.parameterNames()) {
            seed = seed * 31 + params.getOrDefault(name, 0);
        }

        return seed;
    }
}
