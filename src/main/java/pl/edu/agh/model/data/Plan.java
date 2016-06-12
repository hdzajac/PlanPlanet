package pl.edu.agh.model.data;

import org.springframework.data.annotation.Id;
import pl.edu.agh.model.routecreator.RoutedAttraction;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Plan {

    @Id
    private String id;
    private List<Day> days;
    private String city;

    public Plan(List<Day> days) {
        this.days = days;
    }

    public List<Day> getDays() {
        return days;
    }

    public List<RoutedAttraction> getAllAttractionsInOrder() {
        return getDays().stream().flatMap(day -> day.getAttractions().stream()).collect(toList());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){
        return String.format("ID: %s, %s:\n\tnumber of days: %d",id, city, days.size());
    }
}
