package pl.edu.agh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.planbuilder.TemporaryPlan;
import pl.edu.agh.repositories.TemporaryPlanRepository;

import java.util.Optional;

@Service
public class TemporaryPlanService {
    private final TemporaryPlanRepository repository;

    @Autowired
    public TemporaryPlanService(TemporaryPlanRepository planRepository) {
        this.repository = planRepository;
    }


    public Optional<TemporaryPlan> findById(String id) {
        return repository.findById(id);
    }

    public TemporaryPlan save(TemporaryPlan plan) {
        return repository.save(plan);
    }

    public boolean deleteById(String id) {
        return repository.deleteById(id);
    }
}
