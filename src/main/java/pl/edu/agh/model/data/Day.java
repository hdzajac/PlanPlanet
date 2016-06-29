package pl.edu.agh.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.agh.model.routecreator.RoutedAttraction;

import java.time.Duration;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {
    private List<RoutedAttraction> attractions;

    public Day(){}

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

    public void setAttractions(List<RoutedAttraction> attractions) {
        this.attractions = attractions;
    }

    @Override
    public String toString() {
        return "Day(" + attractions + ')';
    }
}
