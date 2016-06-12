package pl.edu.agh.model.data;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

public enum PlaceType {
    AMUSEMENT_PARK("amusement_park", Duration.ofHours(8)),
    AQUARIUM("aquarium", Duration.ofHours(3)),
    ART_GALLERY("art_gallery", Duration.ofHours(1)),
    BAR("bar", Duration.ofMinutes(90)),
    BOWLING("bowling_alley", Duration.ofHours(2)),
    CAFE("cafe", Duration.ofHours(1)),
    CASINO("casino", Duration.ofHours(3)),
    CHURCH("church", Duration.ofMinutes(20)),
    HINDU_TEMPLE("hindu_temple", Duration.ofMinutes(20)),
    MOSQUE("mosque", Duration.ofMinutes(20)),
    CINEMA("movie_theater", Duration.ofMinutes(150)),
    MUSEUM("museum", Duration.ofHours(2)),
    CLUB("night_club", Duration.ofHours(4)),
    PARK("park", Duration.ofHours(1)),
    RESTAURANT("restaurant", Duration.ofMinutes(90)),
    SPA("spa", Duration.ofHours(3)),
    SYNAGOGUE("synagogue", Duration.ofMinutes(20)),
    HOTEL("lodging", Duration.ofHours(16)),
    ZOO("zoo", Duration.ofHours(3));

    public static PlaceType fromString(String type) {
        return Arrays.stream(values())
                .filter(p -> Objects.equals(p.getType(), type)).findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private final String type;
    private final Duration recommendedVisitDuration;

    PlaceType(String type, Duration duration) {
        this.type = type;
        this.recommendedVisitDuration = duration;
    }

    public String getType() {
        return type;
    }

    public Duration getRecommendedVisitDuration() {
        return recommendedVisitDuration;
    }
}
