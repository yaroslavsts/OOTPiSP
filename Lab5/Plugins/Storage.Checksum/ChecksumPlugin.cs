using System.Security.Cryptography;
using System.Text;
using OOTPiSP.Common.Plugins;

namespace Storage.Checksum;

public sealed class ChecksumPlugin : IStorageTransformPlugin
{
    private static readonly byte[] Marker = Encoding.UTF8.GetBytes("OOTPiSP-CHECKSUM-V1\n");

    public string Name => "checksum";
    public string Description => "Stores and verifies SHA-256 checksum.";

    public byte[] ProcessBeforeSave(byte[] data)
    {
        var checksum = Convert.ToHexString(SHA256.HashData(data));
        var header = Marker.Concat(Encoding.UTF8.GetBytes(checksum)).Concat(new byte[] { (byte)'\n' }).ToArray();
        return header.Concat(data).ToArray();
    }

    /// <summary>
    /// Verifies the checksum and returns the original payload.
    /// </summary>
    public byte[] ProcessAfterLoad(byte[] data)
    {
        if (!StartsWith(data, Marker))
        {
            throw new InvalidDataException("Checksum marker is missing.");
        }

        var checksumStart = Marker.Length;
        var checksumEnd = Array.IndexOf(data, (byte)'\n', checksumStart);

        if (checksumEnd < 0)
        {
            throw new InvalidDataException("Checksum header is broken.");
        }

        var expected = Encoding.UTF8.GetString(data, checksumStart, checksumEnd - checksumStart);
        var payload = data[(checksumEnd + 1)..];
        var actual = Convert.ToHexString(SHA256.HashData(payload));

        if (!string.Equals(expected, actual, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidDataException("Checksum verification failed.");
        }

        return payload;
    }

    private static bool StartsWith(byte[] data, byte[] marker)
    {
        return data.Length >= marker.Length && marker.Where((value, index) => data[index] == value).Count() == marker.Length;
    }
}
