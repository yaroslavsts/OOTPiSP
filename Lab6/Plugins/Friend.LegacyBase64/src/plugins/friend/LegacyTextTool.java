package plugins.friend;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LegacyTextTool {
    public String encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public String decode(String text) {
        return new String(Base64.getDecoder().decode(text), StandardCharsets.UTF_8);
    }
}
