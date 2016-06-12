package pl.edu.agh.model.routecreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import javaslang.Tuple;
import javaslang.Tuple2;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.model.data.Location;
import pl.edu.agh.model.data.Route;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GoogleDistanceMatrixRouteChecker implements RouteChecker {
    private final String linkFormat;
    private final RestTemplate rest = new RestTemplate();

    private Map<Tuple2<Location, Location>, Route> cache = new ConcurrentHashMap<>();

    public GoogleDistanceMatrixRouteChecker() {
        this("AIzaSyAFn_20TGxeLBxwLQ6GfnhOzrvHjyYOVcw");
    }

    public GoogleDistanceMatrixRouteChecker(String googleApiKey) {
        this.linkFormat =
                "https://maps.googleapis.com/maps/api/distancematrix/json" +
                        "?units=metric" +
                        "&origins=%.15f,%.15f" +
                        "&destinations=%.15f,%.15f" +
                        "&mode=driving" +
                        "&key=" + googleApiKey;
    }


    @Override
    public Route routeBetween(Location from, Location to) {
        return cache.computeIfAbsent(Tuple.of(from, to), this::routeBetween);
    }

    private Route routeBetween(Tuple2<Location, Location> locations) {
        try {
            String requestUri = getApiString(locations._1, locations._2);
            String rawResult = rest.getForObject(requestUri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            GoogleDistanceMatrixResponse result = mapper.readValue(rawResult, GoogleDistanceMatrixResponse.class);

            double distanceInKm = result.rows[0].elements[0].distance.value / 1000.0;
            Duration duration = Duration.ofSeconds(result.rows[0].elements[0].duration.value);
            return new Route(duration.getSeconds(), distanceInKm);
        } catch (Exception e) {
            return Route.UNREACHABLE;
        }
    }

    private String getApiString(Location from, Location to) {
        return String.format(Locale.US, linkFormat,
                from.getLatitude(), from.getLongitude(),
                to.getLatitude(), to.getLongitude());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GoogleDistanceMatrixResponse {
        public String[] destination_addresses;
        public String[] origin_addresses;
        public Row[] rows;
        public String status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Row {
        public Element[] elements;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Element {
        public String status;
        public Info distance;
        public Info duration;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Info {
        public String text;
        public int value;
    }
}
