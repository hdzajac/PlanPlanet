package pl.edu.agh.model.routecreator;

import pl.edu.agh.model.data.Location;
import pl.edu.agh.model.data.Route;

public interface RouteChecker {
    Route routeBetween(Location from, Location to);
}
