package pl.edu.agh.model.routecreator;

import pl.edu.agh.model.data.Attraction;
import pl.edu.agh.model.data.Location;
import pl.edu.agh.model.data.Route;

import java.time.Duration;

public class RoutedAttraction {
    private final Route route;
    private final long visitTime;
    private final Attraction attraction;

    public Location getLocation() {
        return attraction.getLocation();
    }

    public RoutedAttraction(Attraction attraction, Route route, long visitTime) {
        this.attraction = attraction;
        this.route = route;
        this.visitTime = visitTime;
    }

    public RoutedAttraction withRoute(Route route) {
        return new RoutedAttraction(getAttraction(), route, visitTime);
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public Route getRoute() {
        return route;
    }

    public Duration getTotalRequiredTime() {
        return Duration.ofSeconds(visitTime).plus(route.getTimeToReach());
    }

    public Duration getVisitTime() {
        return Duration.ofSeconds(visitTime);
    }

}
