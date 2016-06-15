package pl.edu.agh.model.routecreator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.agh.model.data.Attraction;
import pl.edu.agh.model.data.Location;
import pl.edu.agh.model.data.Route;

import java.time.Duration;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutedAttraction {
    private Route route;
    @JsonIgnore
    private long visitTime;
    private Attraction attraction;

    public Location getLocation() {
        return attraction.getLocation();
    }

    public RoutedAttraction() {
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

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setVisitTime(long visitTime) {
        this.visitTime = visitTime;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }
}
