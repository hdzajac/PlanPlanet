package pl.edu.agh.model.routecreator;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import pl.edu.agh.Helpers;
import pl.edu.agh.model.GoogleApiFixture;
import pl.edu.agh.model.data.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

public class RouteCreatorTest implements Helpers, GoogleApiFixture {
    @Test
    public void planShouldCoverWholeRequestedPeriod() {
        Plan plan = routeCreator.buildPlan(preferredAttractions, EASY_PREFERENCES, TWO_DAYS);

        assertThat(plan.getDays().size(), is(equalTo(2)));
    }

    @Test
    public void eachDayShouldContainAtLeastOneAttraction() {
        Plan plan = routeCreator.buildPlan(preferredAttractions, EASY_PREFERENCES, TWO_DAYS);

        plan.getDays().forEach(day -> assertThat(day.getAttractions(), is(not(empty()))));
    }

    @Test
    public void planShouldContainDistinctValues() {
        Plan plan = routeCreator.buildPlan(preferredAttractions, EASY_PREFERENCES, TWO_DAYS);

        List<RoutedAttraction> attractionList = plan.getAllAttractionsInOrder();

        assertThat(attractionList, hasDistinctItems());
    }

    @Test
    public void distanceBetweenConsecutiveAttractionsShouldNotExceedPreferredLimit() {
        Plan plan = routeCreator.buildPlan(preferredAttractions, SMALL_DISTANCE_PREFERENCES, TWO_DAYS);

        List<RoutedAttraction> attractions = plan.getAllAttractionsInOrder();

        assertThat(attractions, haveMaxDistanceOf(SMALL_DISTANCE));
    }

    //@Ignore // slow
    @Test
    public void distanceBetweenConsecutiveAttractionsShouldNotExceedPreferredLimitUsingGoogleApi() {
        RouteCreator routeCreator = new RouteCreator(new GoogleDistanceMatrixRouteChecker(GOOGLE_API_KEY));
        Plan plan = routeCreator.buildPlan(preferredAttractions, SMALL_DISTANCE_PREFERENCES, TWO_DAYS);

        List<RoutedAttraction> attractions = plan.getAllAttractionsInOrder();

        assertThat(attractions, haveMaxDistanceOf(SMALL_DISTANCE));
    }

    @Test
    public void firstAttractionShouldHaveZeroReachDuration() {
        Plan plan = routeCreator.buildPlan(preferredAttractions, SMALL_DISTANCE_PREFERENCES, TWO_DAYS);

        RoutedAttraction firstAttraction = plan.getDays().get(0).getAttractions().get(0);

        assertThat(firstAttraction.getRoute().getTimeToReach(), is(Duration.ZERO));
    }

    @Test
    public void totalDurationOfEachDayShouldBeCloseToPreferredValue() {
        Plan plan = new RouteCreator(offlineChecker(Duration.ofHours(1)))
                .buildPlan(preferredAttractions, FOUR_HOURS_OF_SIGHT_SEEING_A_DAY, TWO_DAYS);

        plan.getDays().forEach(day -> assertThat(day.getTotalDuration(), lastsBetween(
                Duration.ofHours(3),
                Duration.ofHours(5)
        )));
    }

    @Test
    public void totalNumberOfAttractionsShouldBeLowerIfTravelingTakesMoreTime() {
        Plan shorterPlan = new RouteCreator(offlineChecker(Duration.ofHours(1)))
                .buildPlan(preferredAttractions, FOUR_HOURS_OF_SIGHT_SEEING_A_DAY, TWO_DAYS);
        Plan longerPlan = new RouteCreator(offlineChecker(Duration.ZERO))
                .buildPlan(preferredAttractions, FOUR_HOURS_OF_SIGHT_SEEING_A_DAY, TWO_DAYS);

        int shorterPlanLength = shorterPlan.getAllAttractionsInOrder().size();
        int longerPlanLength = longerPlan.getAllAttractionsInOrder().size();

        assertThat(shorterPlanLength, is(lessThan(longerPlanLength)));
    }

    @Test
    public void shouldReturnOnlyReachableAttractionsInPreferredOrderAsPropositions() {
        Attraction beforeMissing = attractionWithLocation(50.065707, 19.924216);
        Attraction afterMissing = attractionWithLocation(50.065271, 19.994790);
        List<Attraction> preferredAttractions = Arrays.asList(
                attractionWithLocation(50.099649, 19.916256), // Too far
                attractionWithLocation(50.071062, 19.953887),
                attractionWithLocation(50.090770, 20.005303), // Too far
                attractionWithLocation(50.062839, 19.947546),
                attractionWithLocation(50.062839, 19.947546),
                attractionWithLocation(50.062153, 19.954816)
        );
        Preferences fourKilometers = basicPreferences(4, Duration.ZERO);


        List<Attraction> attractions = routeCreator.getAttractionPropositions(
                beforeMissing, afterMissing, preferredAttractions, fourKilometers);


        List<Attraction> expectedAttractions = Arrays.asList(
                attractionWithLocation(50.071062, 19.953887),
                attractionWithLocation(50.062839, 19.947546),
                attractionWithLocation(50.062839, 19.947546),
                attractionWithLocation(50.062153, 19.954816)
        );
        assertEquals(expectedAttractions, attractions);
    }

    @Test
    public void shouldReturnOnlyReachableAttractionsInPreferredOrderAsPropositionsUsingGoogleApi() {
        RouteCreator routeCreator = new RouteCreator(new GoogleDistanceMatrixRouteChecker(GOOGLE_API_KEY));

        Attraction beforeMissing = attractionWithLocation(50.065707, 19.924216);
        Attraction afterMissing = attractionWithLocation(50.065271, 19.994790);
        List<Attraction> preferredAttractions = Arrays.asList(
                attractionWithLocation(50.099649, 19.916256), // Too far
                attractionWithLocation(50.071062, 19.953887),
                attractionWithLocation(50.090770, 20.005303), // Too far
                attractionWithLocation(50.062839, 19.947546),
                attractionWithLocation(50.062839, 19.947546),
                attractionWithLocation(50.062153, 19.954816)
        );
        Preferences fourKilometers = basicPreferences(5, Duration.ZERO);


        List<Attraction> attractions = routeCreator.getAttractionPropositions(
                beforeMissing, afterMissing, preferredAttractions, fourKilometers);


        List<Attraction> expectedAttractions = Arrays.asList(
                attractionWithLocation(50.071062, 19.953887),
                attractionWithLocation(50.062839, 19.947546),
                attractionWithLocation(50.062839, 19.947546),
                attractionWithLocation(50.062153, 19.954816)
        );
        assertEquals(expectedAttractions, attractions);
    }

    private static final double GREAT_DISTANCE = 100000;
    private static final double SMALL_DISTANCE = 5;
    private static final Duration DEFAULT_SIGHTSEEING_TIME = Duration.ofHours(3);
    private static final Preferences SMALL_DISTANCE_PREFERENCES = basicPreferences(SMALL_DISTANCE, DEFAULT_SIGHTSEEING_TIME);
    private static final Preferences EASY_PREFERENCES = basicPreferences(GREAT_DISTANCE, DEFAULT_SIGHTSEEING_TIME);
    private static final Preferences FOUR_HOURS_OF_SIGHT_SEEING_A_DAY = basicPreferences(SMALL_DISTANCE, Duration.ofHours(4));
    private static final TimeInterval TWO_DAYS = new TimeInterval(LocalDate.of(2016, 3, 1), LocalDate.of(2016, 3, 2));
    private final RouteCreator routeCreator = new RouteCreator(offlineChecker(Duration.ZERO));
    private List<Attraction> preferredAttractions = Arrays.asList(
            attractionWithLocation(50.053549, 19.917624),
            attractionWithLocation(50.082826, 20.033669),
            attractionWithLocation(50.099721, 20.018358),
            attractionWithLocation(50.067931, 19.912628),
            attractionWithLocation(50.055646, 19.934669),
            attractionWithLocation(50.075644, 19.945759),
            attractionWithLocation(50.055711, 19.945183),
            attractionWithLocation(50.065016, 19.950899),
            attractionWithLocation(50.078893, 19.935631),
            attractionWithLocation(50.080546, 19.949364),
            attractionWithLocation(50.075589, 19.958719),
            attractionWithLocation(50.074817, 19.919752),
            attractionWithLocation(50.056089, 19.928889),
            attractionWithLocation(50.043930, 19.946671),
            attractionWithLocation(50.047632, 19.929860)
    );

    private RouteChecker offlineChecker(Duration duration) {
        return (from, to) -> new Route(duration.getSeconds(), from.distanceTo(to));
    }

    private Attraction attractionWithLocation(double lat, double len) {
        return new Attraction(new Location(lat, len), 0, "", Optional.empty(), Collections.emptyList());
    }

    private static Preferences basicPreferences(double maxAttractionDistanceInKM, Duration averageSightseeingTimePerDay) {
        return new Preferences(maxAttractionDistanceInKM, averageSightseeingTimePerDay.getSeconds());
    }

    private Matcher<List<RoutedAttraction>> haveMaxDistanceOf(double requiredDistance) {
        return new TypeSafeMatcher<List<RoutedAttraction>>() {
            @Override
            public void describeTo(final Description description) {
                description.appendText("attractions should be within distance of ").appendValue(requiredDistance);
            }

            @Override
            protected void describeMismatchSafely(final List<RoutedAttraction> item, final Description mismatchDescription) {
                mismatchDescription.appendText("had attractions not within given distance of ").appendValue(requiredDistance);
            }

            @Override
            protected boolean matchesSafely(final List<RoutedAttraction> attractionList) {
                for (int i = 0; i < attractionList.size() - 1; i++) {
                    RoutedAttraction current = attractionList.get(i);
                    RoutedAttraction next = attractionList.get(i + 1);

                    double distance = current.getLocation().distanceTo(next.getLocation());

                    if (distance > requiredDistance) {
                        return false;
                    }
                }
                return true;
            }
        };
    }
}