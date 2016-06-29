package pl.edu.agh.model.data;

import java.util.Objects;

public class Location {
    private double latitude;
    private double longitude;

    public Location() {
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Location(" + latitude + ", " + longitude + ')';
    }

    // http://stackoverflow.com/a/21623206/5123895
    public double distanceTo(Location that) {
        double p = 0.017453292519943295;    // Math.PI / 180
        double a = 0.5 - Math.cos((that.getLatitude() - this.getLatitude()) * p) / 2 +
                Math.cos(this.getLatitude() * p) * Math.cos(that.getLatitude() * p) *
                        (1 - Math.cos((that.getLongitude() - this.getLongitude()) * p)) / 2;

        return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return distanceTo(location) <= 0.05;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
