package plugins.shift;

import shared.StoragePlugin;

public class ShiftEncryptionPlugin implements StoragePlugin {
    public String name() {
        return "shift-encryption";
    }

    public byte[] beforeSave(byte[] data) {
        return shift(data, 3);
    }

    public byte[] afterLoad(byte[] data) {
        return shift(data, -3);
    }

    private byte[] shift(byte[] data, int value) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] + value);
        }
        return result;
    }
}
