package plugins.cyclist;

import shared.AthleteRegistry;
import shared.EntityPlugin;

public class CyclistPlugin implements EntityPlugin {
    public String name() {
        return "cyclist-plugin";
    }

    public void register(AthleteRegistry registry) {
        registry.add(AthleteRegistry.sportsman("cyclist", values -> new Cyclist(
            AthleteRegistry.text(values, "Name"),
            AthleteRegistry.number(values, "Age"),
            AthleteRegistry.text(values, "Gender"),
            AthleteRegistry.number(values, "Medals"),
            AthleteRegistry.number(values, "Years"),
            AthleteRegistry.text(values, "Bike"),
            AthleteRegistry.number(values, "Distance")
        ))
            .field("Bike", a -> ((Cyclist) a).bike, (a, x) -> ((Cyclist) a).bike = x)
            .field("Distance", a -> "" + ((Cyclist) a).distance, (a, x) -> ((Cyclist) a).distance = Integer.parseInt(x)));
    }
}
