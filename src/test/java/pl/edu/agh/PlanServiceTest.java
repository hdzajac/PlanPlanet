package pl.edu.agh;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.model.data.Preferences;
import pl.edu.agh.model.data.Plan;
import pl.edu.agh.model.planbuilder.PlanBuilder;
import pl.edu.agh.model.routecreator.TimeInterval;
import pl.edu.agh.services.PlanServiceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PlanplanetApplication.class)
public class PlanServiceTest {

    @Autowired
    PlanBuilder builder;

    @Autowired
    PlanServiceImpl service;

    private static final double maxAttractionDistance = 10;
    private static final Duration duration = Duration.ofHours(4);
    private Preferences preferences;
    private TimeInterval timeInterval;

    @Before
    public void setUp(){
        preferences = new Preferences(maxAttractionDistance, duration.getSeconds());
        timeInterval = new TimeInterval(LocalDate.of(2016, 3, 1), LocalDate.of(2016, 3, 2));

    }

    @Test
    public void saveAndGetByIdTest(){

        Plan plan = builder.generatePlan(preferences,timeInterval,"olsztyn").getPlan();

        System.out.println(plan.toString());

        service.save(plan);

        List<Plan> temporaryPlan = service.findAll();

    }
}
