package pl.edu.agh.model.matching;

import org.junit.Test;
import pl.edu.agh.model.GoogleApiFixture;
import pl.edu.agh.model.data.Location;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;

public class GoogleLocationResolverTest implements GoogleApiFixture {
    private static final double DISTANCE_THRESHOLD_KM = 5.0;

    @Test public void shouldResolveLocation() throws Exception {
        LocationResolver resolver = new GoogleLocationResolver(GOOGLE_API_KEY);
        Location expectedLocation = new Location(40.420207, -3.7150387);

        Location location = resolver.resolve("Madrid");

        assertThat(location.distanceTo(expectedLocation), is(lessThan(DISTANCE_THRESHOLD_KM)));
    }

    @Test(expected = UnresolvedLocationException.class)
    public void shouldThrowOnInvalidLocation() throws Exception {
        LocationResolver resolver = new GoogleLocationResolver(GOOGLE_API_KEY);

        resolver.resolve("asdasfasfadf");
    }
}