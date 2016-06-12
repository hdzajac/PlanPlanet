package pl.edu.agh.model.routecreator;

import javaslang.collection.List;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.data.*;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


// todo address means of transport (currently always using 'driving')
@Component
public class RouteCreator {
    // todo add time to visit to plain attraction?
    private static final Duration DEFAULT_VISIT_DURATION = Duration.ofHours(2);
    private static final int MAX_PROPOSITIONS = 10;

    private final RouteChecker routeChecker;

    @Autowired
    public RouteCreator(RouteChecker routeChecker) {
        this.routeChecker = routeChecker;
    }


    public Plan buildPlan(java.util.List<Attraction> attractions, Preferences preferences, TimeInterval interval) {
        Stream<RoutedAttraction> attractionStream = Stream.ofAll(attractions).map(this::toRoutedAttraction);
        Duration requiredDuration = preferences.getAverageSightseeingTimePerDay().multipliedBy(interval.lengthInDays());

        List<RoutedAttraction> validRoute = generateRouteOfDuration(
                requiredDuration,
                preferences.getMaxAttractionDistanceInKM(),
                attractionStream);

        List<List<RoutedAttraction>> days = splitToDays(interval.lengthInDays(), validRoute);

        return new Plan(days
                .map(List::toJavaList)
                .map(Day::new).toJavaList());
    }

    private RoutedAttraction toRoutedAttraction(Attraction attraction) {
        return new RoutedAttraction(attraction, Route.ZERO_LENGTH, attraction.getPlaceTypes().stream().map(PlaceType::getRecommendedVisitDuration)
                                                                                            .findFirst()
                                                                                            .orElse(DEFAULT_VISIT_DURATION).getSeconds());
    }

    private List<RoutedAttraction> generateRouteOfDuration(Duration length, double maximumDistance, Stream<RoutedAttraction> attractions) {
        BiFunction<Location, Location, Boolean> canBeSuccessorOf = (a, b) -> routeChecker.routeBetween(a, b).getDistanceToReach() <= maximumDistance;

        return findRoute(length, canBeSuccessorOf, attractions, attractions, Option.none());
    }

    private List<RoutedAttraction> findRoute(
            Duration duration,
            BiFunction<Location, Location, Boolean> canBeSuccessor,
            Stream<RoutedAttraction> attractions,
            Stream<RoutedAttraction> reachableAttractions,
            Option<Location> previousLocationOpt
    ) {
        if (duration.isNegative() || duration.isZero() || attractions.isEmpty()) {
            return List.empty();
        }

        Stream<List<RoutedAttraction>> routes = reachableAttractions.map(current -> {
                    Stream<RoutedAttraction> rest = attractions.remove(current);

                    RoutedAttraction adjustedAttraction = previousLocationOpt
                            .map(previousLocation ->
                                    current.withRoute(routeChecker.routeBetween(previousLocation, current.getLocation())))
                            .getOrElse(current.withRoute(Route.ZERO_LENGTH));

                    return findRoute(
                            duration.minus(adjustedAttraction.getTotalRequiredTime()),
                            canBeSuccessor,
                            rest,
                            rest.filter(attraction -> canBeSuccessor.apply(current.getLocation(), attraction.getLocation())),
                            Option.of(adjustedAttraction.getLocation())
                    ).prepend(adjustedAttraction);
                }
        );

        return getLongEnoughOrLongestRoute(routes, duration);
    }

    private List<RoutedAttraction> getLongEnoughOrLongestRoute(Stream<List<RoutedAttraction>> routes, Duration expectedDuration) {
        Option<List<RoutedAttraction>> routeOpt = routes.find(lastsLongerOrEqualTo(expectedDuration));

        return routeOpt.getOrElse(() ->
                routes.maxBy(this::totalDuration)
                        .getOrElse(List.empty()));
    }

    private Predicate<List<RoutedAttraction>> lastsLongerOrEqualTo(Duration expectedDuration) {
        return route -> totalDuration(route).compareTo(expectedDuration) >= 0;
    }

    private Duration totalDuration(List<RoutedAttraction> attractions) {
        return attractions.map(RoutedAttraction::getTotalRequiredTime).foldLeft(Duration.ZERO, Duration::plus);
    }

    private List<List<RoutedAttraction>> splitToDays(int days, List<RoutedAttraction> route) {
        int maxAttractionsPerDay = (int) Math.ceil((double) route.size() / days);
        return route.grouped(maxAttractionsPerDay).toList();
    }


    // attractions must not contain anything that is already in the plan
    public java.util.List<Attraction> getAttractionPropositions(
            Attraction start,
            Attraction end,
            java.util.List<Attraction> attractions,
            Preferences preferences) {

        double maxDistance = preferences.getMaxAttractionDistanceInKM();

        Stream<Attraction> reachableFromStart = reachableFrom(start, Stream.ofAll(attractions), maxDistance);
        Stream<Attraction> reachableFromStartAndEnd = reachableFrom(end, reachableFromStart, maxDistance);

        return reachableFromStartAndEnd.take(MAX_PROPOSITIONS).toJavaList();
    }

    private Stream<Attraction> reachableFrom(Attraction startPoint, Stream<Attraction> attractions, double maxDistance) {
        Function<Attraction, Predicate<Attraction>> isReachable =
                a -> b -> routeChecker.routeBetween(a.getLocation(), b.getLocation()).getDistanceToReach() <= maxDistance;

        return attractions.filter(isReachable.apply(startPoint));
    }
}
