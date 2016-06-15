package pl.edu.agh.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import pl.edu.agh.model.routecreator.RoutedAttraction;

import java.util.List;

import static java.util.stream.Collectors.toList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan {

    @Id
    private String id;
    private String author;
    private List<Day> days;
    private String city;

    public Plan(){}

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public String toString(){
        return String.format("ID: %s, %s:\n\tnumber of days: %d",id, city, days.size());
    }
}
