package pl.edu.agh.model.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.data.Attraction;
import pl.edu.agh.model.data.Location;
import pl.edu.agh.model.data.Preferences;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Matcher {
    private static final int DEFAULT_ATTRACTION_RADIUS_M = 40 * 1000;

    private final PlacesProvider placesProvider;
    private final LocationResolver locationResolver;

    @Autowired
    public Matcher(PlacesProvider placesProvider, LocationResolver locationResolver) {
        this.placesProvider = placesProvider;
        this.locationResolver = locationResolver;
    }

    public List<Attraction> getMatchingAttractions(Preferences preferences, String locationName) {
        Location location = locationResolver.resolve(locationName);
        return placesProvider.listOfAttractions(location, DEFAULT_ATTRACTION_RADIUS_M, preferences)
                .stream()
                .distinct()
                .sorted((a1, a2) -> -Double.compare(a1.getRating(), a2.getRating()))
                .collect(Collectors.toList());
    }
}
