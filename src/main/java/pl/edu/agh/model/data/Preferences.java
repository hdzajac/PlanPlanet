package pl.edu.agh.model.data;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Preferences {
    private double maxAttractionDistanceInKM;
    private long averageSightseeingTimePerDay;
    private List<PlaceType> types;
    private List<String> keywords;
    private Price minPrice;
    private Price maxPrice;

    public Preferences() {
    }

    public void setMaxAttractionDistanceInKM(double maxAttractionDistanceInKM) {
        this.maxAttractionDistanceInKM = maxAttractionDistanceInKM;
    }

    public void setAverageSightseeingTimePerDay(long averageSightseeingTimePerDay) {
        this.averageSightseeingTimePerDay = averageSightseeingTimePerDay;
    }

    public void setTypes(List<PlaceType> types) {
        this.types = types;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setMinPrice(Price minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Price maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Preferences(double maxAttractionDistanceInKM, long averageSightseeingTimePerDay, List<PlaceType> types, List<String> keywords, Price minPrice, Price maxPrice) {
        this.maxAttractionDistanceInKM = maxAttractionDistanceInKM;
        this.averageSightseeingTimePerDay = averageSightseeingTimePerDay;
        this.types = types;
        this.keywords = keywords;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }


    public Preferences(double maxAttractionDistanceInKM, long averageSightseeingTimePerDay) {
        this(maxAttractionDistanceInKM, averageSightseeingTimePerDay, Arrays.asList(PlaceType.ART_GALLERY, PlaceType.MUSEUM), Arrays.asList("gallery", "museum"),
                Price.FREE, Price.VERY_EXPENSIVE);
    }

    public double getMaxAttractionDistanceInKM() {
        return maxAttractionDistanceInKM;
    }

    public Duration getAverageSightseeingTimePerDay() {
        return Duration.ofSeconds(averageSightseeingTimePerDay);
    }

    public List<PlaceType> getTypes() {
        return types;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public Price getMinPrice() {
        return minPrice;
    }

    public Price getMaxPrice() {
        return maxPrice;
    }
}
