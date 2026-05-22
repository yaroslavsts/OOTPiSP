package plugins.adapter;

import plugins.friend.LegacyTextTool;
import shared.StoragePlugin;

import java.nio.charset.StandardCharsets;

public class FriendBase64Adapter implements StoragePlugin {
    private final LegacyTextTool friendTool = new LegacyTextTool();

    public String name() {
        return "friend-base64-adapter";
    }

    public byte[] beforeSave(byte[] data) {
        String text = new String(data, StandardCharsets.UTF_8);
        return friendTool.encode(text).getBytes(StandardCharsets.UTF_8);
    }

    public byte[] afterLoad(byte[] data) {
        String text = new String(data, StandardCharsets.UTF_8);
        return friendTool.decode(text).getBytes(StandardCharsets.UTF_8);
    }
}
