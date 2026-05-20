using System.IO.Compression;
using OOTPiSP.Common.Plugins;

namespace Storage.GZip;

public sealed class GZipPlugin : IStorageTransformPlugin
{
    public string Name => "gzip";
    public string Description => "Compresses data with GZip before saving and decompresses it after loading.";

    public byte[] ProcessBeforeSave(byte[] data)
    {
        using var output = new MemoryStream();

        using (var gzip = new GZipStream(output, CompressionLevel.SmallestSize, leaveOpen: true))
        {
            gzip.Write(data);
        }

        return output.ToArray();
    }

    public byte[] ProcessAfterLoad(byte[] data)
    {
        using var input = new MemoryStream(data);
        using var gzip = new GZipStream(input, CompressionMode.Decompress);
        using var output = new MemoryStream();
        gzip.CopyTo(output);
        return output.ToArray();
    }
}
