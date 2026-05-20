using OOTPiSP.Common.Domain;
using OOTPiSP.Common.Plugins;

namespace Entity.Cyclist;

public sealed class Cyclist : Sportsman
{
    public Cyclist(
        string name,
        int age,
        string gender,
        int medals,
        int yearsInSport,
        string bikeType,
        int distanceKm)
        : base(name, age, gender, medals, yearsInSport)
    {
        BikeType = bikeType;
        DistanceKm = distanceKm;
    }

    public override string TypeName => "cyclist";
    public string BikeType { get; set; }
    public int DistanceKm { get; set; }

    public override string Summary() => $"{base.Summary()}, bike: {BikeType}, distance: {DistanceKm} km";
}

public sealed class CyclistPlugin : IEntityPlugin
{
    public string Name => "Cyclist entity plugin";

    /// <summary>
    /// Registers a new hierarchy class and its editable fields in the base program.
    /// </summary>
    public void Register(EntityRegistry registry)
    {
        var fields = AthleteRegistryFactory.SportsmanFields<Cyclist>();
        fields.Add(AthleteRegistryFactory.Text<Cyclist>("BikeType", cyclist => cyclist.BikeType, (cyclist, value) => cyclist.BikeType = value));
        fields.Add(AthleteRegistryFactory.Integer<Cyclist>("DistanceKm", cyclist => cyclist.DistanceKm, (cyclist, value) => cyclist.DistanceKm = value));

        registry.Register(new EntityDefinition(
            "cyclist",
            "Cyclist",
            fields,
            values => new Cyclist(
                values.Text("Name", "Unknown"),
                values.Integer("Age"),
                values.Text("Gender", "n/a"),
                values.Integer("Medals"),
                values.Integer("YearsInSport"),
                values.Text("BikeType", "road"),
                values.Integer("DistanceKm"))));
    }
}
