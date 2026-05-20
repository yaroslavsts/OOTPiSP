using System.Text;
using Friend.LegacyBase64;
using OOTPiSP.Common.Plugins;

namespace Adapter.LegacyBase64;

public sealed class LegacyBase64Adapter : IStorageTransformPlugin
{
    private readonly ILegacyTextProcessor _legacyPlugin = new LegacyBase64Plugin();

    public string Name => "legacy-base64-adapter";
    public string Description => "Adapts a friend's text-only base64 plugin to the storage plugin interface.";

    public byte[] ProcessBeforeSave(byte[] data)
    {
        var text = Encoding.UTF8.GetString(data);
        return Encoding.UTF8.GetBytes(_legacyPlugin.Encode(text));
    }

    public byte[] ProcessAfterLoad(byte[] data)
    {
        var text = Encoding.UTF8.GetString(data);
        return Encoding.UTF8.GetBytes(_legacyPlugin.Decode(text));
    }
}
