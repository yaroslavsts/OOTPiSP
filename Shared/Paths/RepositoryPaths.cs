namespace OOTPiSP.Common.Paths;

public static class RepositoryPaths
{
    public static string Root()
    {
        var directory = new DirectoryInfo(AppContext.BaseDirectory);

        while (directory is not null)
        {
            if (File.Exists(Path.Combine(directory.FullName, "OOTPiSP.sln")))
            {
                return directory.FullName;
            }

            directory = directory.Parent;
        }

        return Directory.GetCurrentDirectory();
    }

    public static string Artifacts() => Path.Combine(Root(), "artifacts");

    public static string PluginDirectory() => Path.Combine(Artifacts(), "plugins");
}
