package pl.edu.agh.model.matching;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import javaslang.control.Try;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.model.data.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

@Component
public class GooglePlacesProvider implements PlacesProvider {
    private final String googleApiKey;
    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper;

    public GooglePlacesProvider() {
        this.googleApiKey = "AIzaSyCAorm-P3fhY47KtB0CwnlJwYBQlt7oJxM";
        this.mapper = new ObjectMapper();
    }

    public GooglePlacesProvider(String googleApiKey) {
        this.googleApiKey = googleApiKey;
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Attraction> listOfAttractions(Location placeLocation, int radiusInM, Preferences preferences) {

        return getUrls(placeLocation, radiusInM, preferences)
                .map(url -> rest.getForObject(url, String.class))
                .map(this::parseAttractions)
                .flatMap(Collection::stream)
                .filter(attr -> attr.getPrice()
                        .map(price -> price.between(preferences.getMinPrice(), preferences.getMaxPrice()))
                        .orElse(true))
                .filter(attraction -> !attraction.getPlaceTypes().contains(PlaceType.HOTEL))
                .collect(toList());
    }

    private Stream<String> getUrls(Location placeLocation, int radiusInM, Preferences preferences) {
        Stream<String> keywords = preferences.getKeywords().stream().map(keyword -> formatRequestUrl(
                placeLocation, radiusInM, Optional.of(keyword), Optional.empty()));

        Stream<String> types = preferences.getTypes().stream().map(type -> formatRequestUrl(
                placeLocation, radiusInM, Optional.empty(), Optional.of(type.getType())));

        Stream<String> defaultUrl = Stream.of(formatRequestUrl(placeLocation, radiusInM, Optional.empty(), Optional.empty()));

        return Stream.of(keywords, types, defaultUrl).flatMap(identity());
    }

    private List<Attraction> parseAttractions(String rawResult) {
        try {
            GooglePlacesResponse rs = mapper.readValue(rawResult, GooglePlacesResponse.class);

            return Arrays.stream(rs.results).map(res -> {
                Loc loc = res.geometry.location;
                Location location = new Location(loc.lat, loc.lng);
                double rating = res.rating;
                String name = res.name;
                List<PlaceType> placeTypes = Arrays.stream(res.types)
                        .flatMap(type -> Try.of(() -> PlaceType.fromString(type)).toJavaStream())
                        .collect(toList());
                return new Attraction(location, rating, name, res.getPrice(), placeTypes);
            }).collect(toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private String formatRequestUrl(Location location, int radiusInM,
                                    Optional<String> keywordOpt, Optional<String> typeOpt) {
        String result = String.format(Locale.US,
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                        "?location=%.15f,%.15f" +
                        "&radius=%d",
                location.getLatitude(), location.getLongitude(), radiusInM);

        result += typeOpt.map(type -> "&type=" + type).orElse("");
        result += keywordOpt.map(type -> "&keyword=" + type).orElse("");
        result += "&key=" + googleApiKey;

        return result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GooglePlacesResponse {
        public String[] html_attributions;
        public Result[] results;
        public String next_page_token;
        public String status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Result {
        private boolean priceSet = false;

        public URL icon;
        public String id;
        public String place_id;
        public Geometry geometry;
        public String name;
        public Opening opening_hours;
        public Photo[] photos;
        public String scope = "GOOGLE";
        public AltIds alt_ids;
        private int price_level;

        public void setPrice_level(int price_level) {
            this.price_level = price_level;
            priceSet = true;
        }

        public Optional<Price> getPrice() {
            if (priceSet) {
                return Optional.of(Price.values()[price_level]);
            } else {
                return Optional.empty();
            }
        }

        public double rating;
        public String reference;
        public String[] types;
        public String vicinity;
        public String formatted_address;
        public boolean permanently_closed;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Opening {
        public boolean open_now;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Photo {
        public String photo_reference;
        public int height;
        public int width;
        public String[] html_attributions;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AltIds {
        public String place_id;
        public String scope = "GOOGLE";
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Geometry {
        public Loc location;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Loc {
        public double lat;
        public double lng;
    }
}
