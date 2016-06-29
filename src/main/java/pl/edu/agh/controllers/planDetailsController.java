package pl.edu.agh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.model.data.Plan;
import pl.edu.agh.model.user.PlanListResponse;
import pl.edu.agh.services.PlanServiceImpl;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class planDetailsController {

    @Autowired
    PlanServiceImpl service;

    @RequestMapping("/planDetails/{id}")
    Plan getById(@PathVariable("id") String id) {
        return service.findById(id).orElseThrow(() -> new NoSuchElementException("No such plan: " + id));
    }

    @RequestMapping("/getPlans/{user}")
    ResponseEntity<PlanListResponse> getByUser(@PathVariable("user") String author) {
        List<Plan> plans = service.findByAuthor(author);
        return new ResponseEntity<>(PlanListResponse.success(plans), HttpStatus.OK);
    }


}
