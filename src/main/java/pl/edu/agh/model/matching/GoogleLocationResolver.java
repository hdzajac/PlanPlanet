package pl.edu.agh.model.matching;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.model.data.Location;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
public class GoogleLocationResolver implements LocationResolver {
    private final String linkFormat;
    private final RestTemplate rest = new RestTemplate();

    public GoogleLocationResolver() {
        this.linkFormat = "https://maps.googleapis.com/maps/api/geocode/json?" +
                "address=%s" +
                "&key=" + "AIzaSyCAorm-P3fhY47KtB0CwnlJwYBQlt7oJxM";
    }


    public GoogleLocationResolver(String googleApiKey) {
        this.linkFormat = "https://maps.googleapis.com/maps/api/geocode/json?" +
                "address=%s" +
                "&key=" + googleApiKey;
    }

    @Override
    public Location resolve(String address) {
        try {
            String requestUri = getApiString(address);
            String rawResult = rest.getForObject(requestUri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            GoogleGeocodingResponse result = mapper.readValue(rawResult, GoogleGeocodingResponse.class);

            GoogleLocation googleLocation = result.results[0].geometry.location;
            return new Location(googleLocation.lat, googleLocation.lng);
        } catch (Exception e) {
            throw new UnresolvedLocationException();
        }
    }

    private String getApiString(String address) {
        String encodedAddress;
        try {
            encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            encodedAddress = address.replaceAll("\\s+", "+");
        }
        return String.format(Locale.US, linkFormat, encodedAddress);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GoogleGeocodingResponse {
        public Result[] results;
        public String status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Result {
        public Geometry geometry;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Geometry {
        public GoogleLocation location;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GoogleLocation {
        public double lat;
        public double lng;
    }
}
