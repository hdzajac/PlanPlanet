package pl.edu.agh.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.agh.model.planbuilder.TemporaryPlan;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface TemporaryPlanRepository extends CrudRepository<TemporaryPlan, String> {
    Optional<TemporaryPlan> findById(String id);
    TemporaryPlan save(TemporaryPlan plan);
    boolean deleteById(String id);
}