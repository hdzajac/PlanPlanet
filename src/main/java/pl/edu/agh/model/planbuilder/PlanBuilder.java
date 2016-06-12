package pl.edu.agh.model.planbuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.data.Attraction;
import pl.edu.agh.model.data.Plan;
import pl.edu.agh.model.data.Preferences;
import pl.edu.agh.model.matching.Matcher;
import pl.edu.agh.model.routecreator.RouteCreator;
import pl.edu.agh.model.routecreator.RoutedAttraction;
import pl.edu.agh.model.routecreator.TimeInterval;
import pl.edu.agh.services.TemporaryPlanService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class PlanBuilder {
    private final Matcher matcher;
    private final RouteCreator routeCreator;
    private final TemporaryPlanService planService;

    @Autowired
    public PlanBuilder(Matcher matcher, RouteCreator routeCreator, TemporaryPlanService planService) {
        this.routeCreator = routeCreator;
        this.matcher = matcher;
        this.planService = planService;
    }

    public TemporaryPlan generatePlan(Preferences preferences, TimeInterval interval, String city) {
        List<Attraction> attractions = this.matcher.getMatchingAttractions(preferences, city);
        Plan generatedPlan = routeCreator.buildPlan(attractions, preferences, interval);
        List<Attraction> spareAttractions = createSpareAttractionsList(getUsedAttractions(generatedPlan), attractions);

        return planService.save(new TemporaryPlan(generatedPlan, spareAttractions, preferences));
    }

    private List<Attraction> getUsedAttractions(Plan generatedPlan) {
        return generatedPlan.getAllAttractionsInOrder().stream().map(RoutedAttraction::getAttraction).collect(toList());
    }

    private List<Attraction> createSpareAttractionsList(List<Attraction> usedAttractions, List<Attraction> allAttractions) {
        return allAttractions.stream().filter(attraction -> !usedAttractions.contains(attraction)).collect(toList());
    }

    public List<Attraction> getAttractionPropositions(String temporaryPlanId, int dayIndex, int attractionIndex) {
        return planService.findById(temporaryPlanId).map(temporaryPlan -> {
            List<Attraction> propositions = getAttractionPropositions(attractionIndex, temporaryPlan, dayIndex);
            removePropositionsFromAttractions(temporaryPlan, propositions);
            return propositions;
        }).orElse(Collections.emptyList());
    }

    private void removePropositionsFromAttractions(TemporaryPlan temporaryPlan, List<Attraction> propositions) {
        List<Attraction> spareAttractionsWithoutPropositions =  temporaryPlan.getSpareAttractions().stream().filter(attraction -> !propositions.contains(attraction)).collect(toList());
        temporaryPlan.setSpareAttractions(spareAttractionsWithoutPropositions);
        planService.save(temporaryPlan);
    }

    // todo fix index out of bounds
    private List<Attraction> getAttractionPropositions(int attractionIndex, TemporaryPlan temporaryPlan, int dayIndex) {
        Plan plan = temporaryPlan.getPlan();
        List<Attraction> attractions  = getAttractionsAtRequestedDay(dayIndex, plan);

        return Optional.of(attractionIndex - 1).filter(i -> i >= 0).flatMap(from ->
               Optional.of(attractionIndex + 1).filter(i -> i < attractions.size()).map(to ->
                routeCreator.getAttractionPropositions(
                        attractions.get(from),
                        attractions.get(to),
                        temporaryPlan.getSpareAttractions(),
                        temporaryPlan.getPreferences()))).orElse(Collections.emptyList());
    }

    private List<Attraction> getAttractionsAtRequestedDay(int dayIndex, Plan plan) {
        return plan.getDays().get(dayIndex).getAttractions().stream().map(RoutedAttraction::getAttraction).collect(toList());
    }

}