namespace OOTPiSP.Common.Domain;

public static class AthleteRegistryFactory
{
    public static EntityRegistry Create()
    {
        var registry = new EntityRegistry();

        registry.Register(new EntityDefinition(
            "person",
            "Person",
            PersonFields<Person>(),
            values => new Person(values.Text("Name", "Unknown"), values.Integer("Age"), values.Text("Gender", "n/a"))));

        registry.Register(new EntityDefinition(
            "sportsman",
            "Sportsman",
            SportsmanFields<Sportsman>(),
            values => new Sportsman(
                values.Text("Name", "Unknown"),
                values.Integer("Age"),
                values.Text("Gender", "n/a"),
                values.Integer("Medals"),
                values.Integer("YearsInSport"))));

        registry.Register(new EntityDefinition(
            "boxer",
            "Boxer",
            SportsmanFields<Boxer>().Append(Integer<Boxer>("WeightCategory", boxer => boxer.WeightCategory, (boxer, value) => boxer.WeightCategory = value)).ToList(),
            values => new Boxer(
                values.Text("Name", "Unknown"),
                values.Integer("Age"),
                values.Text("Gender", "n/a"),
                values.Integer("Medals"),
                values.Integer("YearsInSport"),
                values.Integer("WeightCategory"))));

        registry.Register(new EntityDefinition(
            "swimmer",
            "Swimmer",
            SportsmanFields<Swimmer>()
                .Append(Text<Swimmer>("Style", swimmer => swimmer.Style, (swimmer, value) => swimmer.Style = value))
                .Append(Text<Swimmer>("BestTime", swimmer => swimmer.BestTime, (swimmer, value) => swimmer.BestTime = value))
                .ToList(),
            values => new Swimmer(
                values.Text("Name", "Unknown"),
                values.Integer("Age"),
                values.Text("Gender", "n/a"),
                values.Integer("Medals"),
                values.Integer("YearsInSport"),
                values.Text("Style", "freestyle"),
                values.Text("BestTime", "00:00"))));

        registry.Register(new EntityDefinition(
            "jumper",
            "Jumper",
            SportsmanFields<Jumper>().Append(Integer<Jumper>("LongestJump", jumper => jumper.LongestJump, (jumper, value) => jumper.LongestJump = value)).ToList(),
            values => new Jumper(
                values.Text("Name", "Unknown"),
                values.Integer("Age"),
                values.Text("Gender", "n/a"),
                values.Integer("Medals"),
                values.Integer("YearsInSport"),
                values.Integer("LongestJump"))));

        registry.Register(new EntityDefinition(
            "football-player",
            "Football player",
            SportsmanFields<FootballPlayer>()
                .Append(Text<FootballPlayer>("Position", player => player.Position, (player, value) => player.Position = value))
                .Append(Text<FootballPlayer>("Club", player => player.Club, (player, value) => player.Club = value))
                .Append(Integer<FootballPlayer>("Goals", player => player.Goals, (player, value) => player.Goals = value))
                .ToList(),
            values => new FootballPlayer(
                values.Text("Name", "Unknown"),
                values.Integer("Age"),
                values.Text("Gender", "n/a"),
                values.Integer("Medals"),
                values.Integer("YearsInSport"),
                values.Text("Position", "forward"),
                values.Text("Club", "none"),
                values.Integer("Goals"))));

        registry.Register(new EntityDefinition(
            "skater",
            "Skater",
            SportsmanFields<Skater>()
                .Append(Text<Skater>("Discipline", skater => skater.Discipline, (skater, value) => skater.Discipline = value))
                .Append(Integer<Skater>("BestScore", skater => skater.BestScore, (skater, value) => skater.BestScore = value))
                .ToList(),
            values => new Skater(
                values.Text("Name", "Unknown"),
                values.Integer("Age"),
                values.Text("Gender", "n/a"),
                values.Integer("Medals"),
                values.Integer("YearsInSport"),
                values.Text("Discipline", "speed"),
                values.Integer("BestScore"))));

        return registry;
    }

    public static EntityField Text<T>(string name, Func<T, string> read, Action<T, string> write)
        where T : IEntity
    {
        return new EntityField(name, FieldKind.String, entity => read((T)entity), (entity, value) => write((T)entity, value));
    }

    public static EntityField Integer<T>(string name, Func<T, int> read, Action<T, int> write)
        where T : IEntity
    {
        return new EntityField(
            name,
            FieldKind.Integer,
            entity => read((T)entity).ToString(),
            (entity, value) => write((T)entity, int.Parse(value)));
    }

    public static List<EntityField> PersonFields<T>() where T : Person
    {
        return new List<EntityField>
        {
            Text<T>("Name", person => person.Name, (person, value) => person.Name = value),
            Integer<T>("Age", person => person.Age, (person, value) => person.Age = value),
            Text<T>("Gender", person => person.Gender, (person, value) => person.Gender = value)
        };
    }

    public static List<EntityField> SportsmanFields<T>() where T : Sportsman
    {
        var fields = PersonFields<T>();
        fields.Add(Integer<T>("Medals", sportsman => sportsman.Medals, (sportsman, value) => sportsman.Medals = value));
        fields.Add(Integer<T>("YearsInSport", sportsman => sportsman.YearsInSport, (sportsman, value) => sportsman.YearsInSport = value));
        return fields;
    }
}
