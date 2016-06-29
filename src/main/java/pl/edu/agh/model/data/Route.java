package pl.edu.agh.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Duration;
import java.util.Objects;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {
    public static final Route ZERO_LENGTH = new Route(Duration.ZERO.getSeconds(), 0);
    public static final Route UNREACHABLE = new Route(Duration.ofSeconds(Long.MAX_VALUE).getSeconds(), Double.POSITIVE_INFINITY);

    @JsonIgnore
    private long timeToReach;
    private double distanceToReach;

    public Route() {
    }

    public Route(long timeToReach, double distanceToReach) {
        this.timeToReach = timeToReach;
        this.distanceToReach = distanceToReach;
    }

    public Duration getTimeToReach() {
        return Duration.ofSeconds(timeToReach);
    }

    public double getDistanceToReach() {
        return distanceToReach;
    }

    @Override
    public String toString() {
        return "Route(" +
                "timeToReach=" + timeToReach +
                ", distanceToReach=" + distanceToReach +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Double.compare(route.distanceToReach, distanceToReach) == 0 &&
                Objects.equals(timeToReach, route.timeToReach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeToReach, distanceToReach);
    }

    public void setTimeToReach(long timeToReach) {
        this.timeToReach = timeToReach;
    }

    public void setDistanceToReach(double distanceToReach) {
        this.distanceToReach = distanceToReach;
    }
}
