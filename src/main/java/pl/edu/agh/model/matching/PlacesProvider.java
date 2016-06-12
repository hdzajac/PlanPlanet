package pl.edu.agh.model.matching;

import pl.edu.agh.model.data.Attraction;
import pl.edu.agh.model.data.Location;
import pl.edu.agh.model.data.Preferences;

import java.util.List;

public interface PlacesProvider {
    List<Attraction> listOfAttractions(Location location, int distanceInM, Preferences preferences);
}
