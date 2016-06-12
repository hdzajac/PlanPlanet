package pl.edu.agh.model.routecreator;

import org.junit.Test;
import pl.edu.agh.model.GoogleApiFixture;
import pl.edu.agh.model.data.Location;
import pl.edu.agh.Helpers;

import java.time.Duration;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GoogleDistanceMatrixRouteCheckerTest implements Helpers, GoogleApiFixture {
    private static final double MAX_DISTANCE = 12;
    private static final Location STARTING_LOCATION = new Location(50.053248, 19.921167);
    private static final Location LOCATION_CLOSE_BY = new Location(50.073737, 19.991145);
    private static final Location LOCATION_FAR_AWAY = new Location(50.116282, 20.045901);
    private final GoogleDistanceMatrixRouteChecker checker = new GoogleDistanceMatrixRouteChecker(GOOGLE_API_KEY);

    @Test
    public void shouldReturnSmallDistanceForLocationsThatAreClose() {
        double distanceToReach = checker.routeBetween(STARTING_LOCATION, LOCATION_CLOSE_BY).getDistanceToReach();

        assertThat(distanceToReach, is(lessThan(MAX_DISTANCE)));
    }

    @Test public void shouldReturnBigDistanceForLocationsThatAreFarFromEachOther() {
        double distanceToReach = checker.routeBetween(STARTING_LOCATION, LOCATION_FAR_AWAY).getDistanceToReach();

        assertThat(distanceToReach, is(greaterThan(MAX_DISTANCE)));
    }

    @Test public void shouldReturnValidTravelDuration() {
        Duration timeToReach = checker.routeBetween(STARTING_LOCATION, LOCATION_CLOSE_BY).getTimeToReach();

        assertThat(timeToReach, lastsBetween(Duration.ofMinutes(15), Duration.ofMinutes(30)));
    }
}