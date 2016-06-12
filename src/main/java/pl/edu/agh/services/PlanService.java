package pl.edu.agh.services;

import pl.edu.agh.model.data.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanService {
    Optional<Plan> findById(String id);
    List<Plan> findAll();
    Plan save(Plan plan);
    boolean deleteById(String id);
    Plan findByCity(String city);

}
