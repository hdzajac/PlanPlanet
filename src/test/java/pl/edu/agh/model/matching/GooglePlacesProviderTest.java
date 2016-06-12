package pl.edu.agh.model.matching;

import org.junit.Test;
import pl.edu.agh.Helpers;
import pl.edu.agh.model.data.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class GooglePlacesProviderTest implements Helpers, GooglePlacesApiFixture {
    private static final Location LOCATION = new Location(50.072183, 19.921271);
    private final GooglePlacesProvider provider = new GooglePlacesProvider(GOOGLE_PLACES_API_KEY);
    private static final Preferences PREFERENCES = new Preferences(
            10, Duration.ofHours(5).getSeconds(),
            Arrays.asList(PlaceType.CASINO, PlaceType.CHURCH, PlaceType.MUSEUM),
            Collections.singletonList("gallery"), Price.FREE, Price.VERY_EXPENSIVE);

    @Test
    public void shouldFindAttractions() throws Exception {
        List<Attraction> attractions = provider.listOfAttractions(LOCATION, 50000, PREFERENCES);

        assertThat(attractions, is(not(empty())));
        assertTrue(attractions.stream().anyMatch(a -> a.getPlaceTypes().stream().anyMatch(type -> type == PlaceType.CHURCH)));
    }
}