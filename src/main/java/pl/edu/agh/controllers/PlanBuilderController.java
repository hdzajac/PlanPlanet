package pl.edu.agh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.data.*;
import pl.edu.agh.model.planbuilder.PlanBuilder;
import pl.edu.agh.model.planbuilder.TemporaryPlan;
import pl.edu.agh.model.routecreator.TimeInterval;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RestController
public class PlanBuilderController {

    private final PlanBuilder planBuilder;

    @Autowired
    public PlanBuilderController(PlanBuilder planBuilder) {
        this.planBuilder = planBuilder;
    }


    @RequestMapping(value = "/api/buildplan", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<PlanDto> buildPlan(@RequestBody PlanRequestDto request) {
        TemporaryPlan temporaryPlan = planBuilder.generatePlan(request.getPreferences(), request.getTimeInterval(), request.getDestination());

        return new ResponseEntity<>(new PlanDto(temporaryPlan.getPlan(), temporaryPlan.getId()), HttpStatus.OK);
    }


    @RequestMapping(value = "/api/propositions", method = RequestMethod.GET)
    public @ResponseBody List<AttractionDto> getAttractionPropositions(
            @RequestParam("planId") String id,
            @RequestParam("dayId") int day,
            @RequestParam("attractionId") int attraction) {
        List<Attraction> propositions = planBuilder.getAttractionPropositions(id, day, attraction);

        return propositions.stream().map(AttractionDto::new).collect(toList());
    }



    private static class PlanDto {
        public String id;
        public List<DayDto> days;
        PlanDto(Plan plan, String id) {
            this.id = id;
            List<Day> days = plan.getDays();
            this.days = IntStream.range(0, days.size())
                    .mapToObj(index -> new DayDto(index + 1, days.get(index)))
                    .collect(toList());
        }
    }

    private static class DayDto {
        public int index;
        public List<AttractionDto> attractions;

        public DayDto(int index, Day day) {
            this.index = index;
            this.attractions = day.getAttractions().stream()
                    .map(ra -> new AttractionDto(ra.getAttraction()))
                    .collect(toList());
        }
    }

    private static class AttractionDto {
        public String name;
        public Location location;
        public String details;

        public AttractionDto(Attraction attraction) {
            this.name = attraction.getName();
            this.location = attraction.getLocation();
            this.details = "Rating: " + attraction.getRating() + "\n" +
                    attraction.getPrice().map(price -> "Price: " + price + "\n").orElse("") +
                    attraction.getPlaceTypes().stream().findFirst().map(type -> "Type: " + type.getType() + "\n").orElse("");
        }
    }

    private static class PlanRequestDto {
        public String destination;
        public String dateFrom;
        public String dateTo;
        public PreferencesDto preferences;

        public TimeInterval getTimeInterval() {
            LocalDate from = ZonedDateTime.parse(dateFrom).toLocalDate();
            LocalDate to = ZonedDateTime.parse(dateTo).toLocalDate();
            return new TimeInterval(from, to);
        }

        public String getDestination() {
            return destination;
        }

        public Preferences getPreferences() {
            return preferences.getPreferences();
        }
    }

    private static class PreferencesDto {
        public String[] keywords;
        public PlaceType[] types;
        public Price minPrice;
        public Price maxPrice;
        public int maxDistanceKm;
        public int sightseeingTimeADay;

        public Preferences getPreferences() {
            return new Preferences(maxDistanceKm,
                    Duration.ofHours(sightseeingTimeADay).getSeconds(),
                    Arrays.asList(types), Arrays.asList(keywords),
                    minPrice, maxPrice);
        }
    }
}