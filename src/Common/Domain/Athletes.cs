namespace OOTPiSP.Common.Domain;

public interface IEntity
{
    Guid Id { get; set; }
    string TypeName { get; }
    string Summary();
}

public abstract class EntityBase : IEntity
{
    protected EntityBase(string typeName)
    {
        TypeName = typeName;
    }

    public Guid Id { get; set; } = Guid.NewGuid();
    public virtual string TypeName { get; }

    public abstract string Summary();
}

public class Person : EntityBase
{
    public Person(string name, int age, string gender) : base("person")
    {
        Name = name;
        Age = age;
        Gender = gender;
    }

    public string Name { get; set; }
    public int Age { get; set; }
    public string Gender { get; set; }

    public override string Summary() => $"{Name}, {Age}, {Gender}";
}

public class Sportsman : Person
{
    public Sportsman(string name, int age, string gender, int medals, int yearsInSport)
        : base(name, age, gender)
    {
        Medals = medals;
        YearsInSport = yearsInSport;
    }

    public int Medals { get; set; }
    public int YearsInSport { get; set; }

    public override string TypeName => "sportsman";

    public override string Summary() => $"{base.Summary()}, medals: {Medals}, years: {YearsInSport}";
}

public class Boxer : Sportsman
{
    public Boxer(string name, int age, string gender, int medals, int yearsInSport, int weightCategory)
        : base(name, age, gender, medals, yearsInSport)
    {
        WeightCategory = weightCategory;
    }

    public override string TypeName => "boxer";
    public int WeightCategory { get; set; }

    public override string Summary() => $"{base.Summary()}, weight category: {WeightCategory}";
}

public class Swimmer : Sportsman
{
    public Swimmer(string name, int age, string gender, int medals, int yearsInSport, string style, string bestTime)
        : base(name, age, gender, medals, yearsInSport)
    {
        Style = style;
        BestTime = bestTime;
    }

    public override string TypeName => "swimmer";
    public string Style { get; set; }
    public string BestTime { get; set; }

    public override string Summary() => $"{base.Summary()}, style: {Style}, best time: {BestTime}";
}

public class Jumper : Sportsman
{
    public Jumper(string name, int age, string gender, int medals, int yearsInSport, int longestJump)
        : base(name, age, gender, medals, yearsInSport)
    {
        LongestJump = longestJump;
    }

    public override string TypeName => "jumper";
    public int LongestJump { get; set; }

    public override string Summary() => $"{base.Summary()}, longest jump: {LongestJump}";
}

public class FootballPlayer : Sportsman
{
    public FootballPlayer(
        string name,
        int age,
        string gender,
        int medals,
        int yearsInSport,
        string position,
        string club,
        int goals)
        : base(name, age, gender, medals, yearsInSport)
    {
        Position = position;
        Club = club;
        Goals = goals;
    }

    public override string TypeName => "football-player";
    public string Position { get; set; }
    public string Club { get; set; }
    public int Goals { get; set; }

    public override string Summary() => $"{base.Summary()}, {Position}, {Club}, goals: {Goals}";
}

public class Skater : Sportsman
{
    public Skater(string name, int age, string gender, int medals, int yearsInSport, string discipline, int bestScore)
        : base(name, age, gender, medals, yearsInSport)
    {
        Discipline = discipline;
        BestScore = bestScore;
    }

    public override string TypeName => "skater";
    public string Discipline { get; set; }
    public int BestScore { get; set; }

    public override string Summary() => $"{base.Summary()}, discipline: {Discipline}, best score: {BestScore}";
}
