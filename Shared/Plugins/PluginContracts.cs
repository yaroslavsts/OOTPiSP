using OOTPiSP.Common.Domain;

namespace OOTPiSP.Common.Plugins;

public interface IEntityPlugin
{
    string Name { get; }
    void Register(EntityRegistry registry);
}

public interface IStorageTransformPlugin
{
    string Name { get; }
    string Description { get; }
    byte[] ProcessBeforeSave(byte[] data);
    byte[] ProcessAfterLoad(byte[] data);
}
