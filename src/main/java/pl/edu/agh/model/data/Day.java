package pl.edu.agh.model.data;

import pl.edu.agh.model.routecreator.RoutedAttraction;

import java.time.Duration;
import java.util.List;

public class Day {
    private final List<RoutedAttraction> attractions;

    public Day(List<RoutedAttraction> attractions) {
        this.attractions = attractions;
    }

    public List<RoutedAttraction> getAttractions() {
        return attractions;
    }

    public Duration getTotalDuration() {
        return attractions.stream()
                .map(RoutedAttraction::getTotalRequiredTime)
                .reduce(Duration.ZERO, Duration::plus);
    }

    @Override
    public String toString() {
        return "Day(" + attractions + ')';
    }
}
