package pl.edu.agh.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.edu.agh.model.data.Plan;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface PlanRepository extends MongoRepository<Plan, String>{
    Optional<Plan> findById(String id);
    List<Plan> findAll();
    Plan save(Plan plan);
    boolean deleteById(String id);
    Plan findByCity(String city);
    List<Plan> findByAuthor(String author);
}
