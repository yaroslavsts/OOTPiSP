using System.Text;

namespace Friend.LegacyBase64;

public interface ILegacyTextProcessor
{
    string Title { get; }
    string Encode(string text);
    string Decode(string text);
}

public sealed class LegacyBase64Plugin : ILegacyTextProcessor
{
    public string Title => "Friend legacy base64 text processor";

    public string Encode(string text) => Convert.ToBase64String(Encoding.UTF8.GetBytes(text));

    public string Decode(string text) => Encoding.UTF8.GetString(Convert.FromBase64String(text));
}
