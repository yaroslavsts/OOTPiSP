package plugins.xor;

import shared.StoragePlugin;

public class XorEncryptionPlugin implements StoragePlugin {
    public String name() {
        return "xor-encryption";
    }

    public byte[] beforeSave(byte[] data) {
        return xor(data);
    }

    public byte[] afterLoad(byte[] data) {
        return xor(data);
    }

    private byte[] xor(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ 90);
        }
        return result;
    }
}
