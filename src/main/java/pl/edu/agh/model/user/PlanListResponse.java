package pl.edu.agh.model.user;

import pl.edu.agh.model.data.Plan;

import java.util.List;

/**
 * Created by Roksana on 2016-06-14.
 */
public class PlanListResponse {
    private Boolean created = false;
    private List<Plan> plans = null;

    private PlanListResponse(Boolean created, List<Plan> plans) {
        this.created = created;
        this.plans = plans;
    }

    public static PlanListResponse success(List<Plan> plans) {
        return new PlanListResponse(true, plans);
    }

    public static PlanListResponse failed() {
        return new PlanListResponse(false, null);
    }

    public Boolean getCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
}
