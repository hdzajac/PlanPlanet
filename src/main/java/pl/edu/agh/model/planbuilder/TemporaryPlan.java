package pl.edu.agh.model.planbuilder;

import org.springframework.data.annotation.Id;
import pl.edu.agh.model.data.Attraction;
import pl.edu.agh.model.data.Plan;
import pl.edu.agh.model.data.Preferences;

import java.util.List;

public class TemporaryPlan {
    @Id
    private String id;
    private Plan plan;
    private List<Attraction> spareAttractions;
    private Preferences preferences;

    public TemporaryPlan(Plan plan, List<Attraction> spareAttractions, Preferences preferences) {
        this.plan = plan;
        this.spareAttractions = spareAttractions;
        this.preferences = preferences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Attraction> getSpareAttractions() {
        return spareAttractions;
    }

    public void setSpareAttractions(List<Attraction> spareAttractions) {
        this.spareAttractions = spareAttractions;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
}