package pl.edu.agh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.data.Plan;
import pl.edu.agh.repositories.PlanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService{

    private final PlanRepository repository;

    @Autowired
    PlanServiceImpl(PlanRepository planRepository){
        this.repository = planRepository;
    }


    @Override
    public Optional<Plan> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Plan> findAll() {
        return repository.findAll();
    }

    @Override
    public Plan save(Plan plan) {
        return repository.save(plan);
    }

    @Override
    public boolean deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Plan findByCity(String city) {
        return repository.findByCity(city);
    }

    @Override
    public List<Plan> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }
}
