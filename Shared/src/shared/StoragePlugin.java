package shared;

public interface StoragePlugin {
    String name();

    byte[] beforeSave(byte[] data) throws Exception;

    byte[] afterLoad(byte[] data) throws Exception;
}
