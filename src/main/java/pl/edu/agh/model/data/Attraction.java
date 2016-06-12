package pl.edu.agh.model.data;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Attraction {
    private final Optional<Price> price;
    private final List<PlaceType> placeTypes;
    private final Location location;
    private final double rating;
    private final String name;

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
