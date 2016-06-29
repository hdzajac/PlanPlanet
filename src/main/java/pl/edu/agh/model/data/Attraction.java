package pl.edu.agh.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attraction {
    private Optional<Price> price;
    private List<PlaceType> placeTypes;
    private Location location;
    private double rating;
    private String name;

    public Attraction() {
    }

    public Attraction(Location location, double rating, String name, Optional<Price> price, List<PlaceType> placeTypes) {
        this.location = location;
        this.rating = rating;
        this.name = name;
        this.price = price;
        this.placeTypes = placeTypes;
    }

    public Optional<Price> getPrice() {
        return price;
    }

    public List<PlaceType> getPlaceTypes() {
        return placeTypes;
    }

    public double getRating() {
        return rating;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Optional<Price> price) {
        this.price = price;
    }

    public void setPlaceTypes(List<PlaceType> placeTypes) {
        this.placeTypes = placeTypes;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "location=" + location +
                ", rating=" + rating +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attraction that = (Attraction) o;
        return Double.compare(that.rating, rating) == 0 &&
                Objects.equals(location, that.location) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, rating, name);
    }
}
