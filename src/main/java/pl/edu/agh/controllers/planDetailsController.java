package pl.edu.agh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.model.data.Plan;
import pl.edu.agh.services.PlanServiceImpl;

import java.util.NoSuchElementException;

@RestController
public class planDetailsController {

    @Autowired
    PlanServiceImpl service;

    @RequestMapping("/planDetails/{id}")
    Plan getById(@PathVariable("id") String id) {
        return service.findById(id).orElseThrow(() -> new NoSuchElementException("No such plan: " + id));
    }


}
