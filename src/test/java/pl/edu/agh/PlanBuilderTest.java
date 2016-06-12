package pl.edu.agh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.model.planbuilder.PlanBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PlanplanetApplication.class)
public class PlanBuilderTest {

    @Autowired
    PlanBuilder planBuilder;

    @Test
    public void spareAttractionsMakerTest(){
//
//
//        List<PlaceType> placeTypes = Arrays.asList(PlaceType.AMUSEMENT_PARK, PlaceType.AQUARIUM);
//        List<Attraction> all = Arrays.asList(new Attraction(new Location(12.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes),
//                                            new Attraction(new Location(13.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes),
//                                            new Attraction(new Location(14.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes),
//                                            new Attraction(new Location(15.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes),
//                                            new Attraction(new Location(16.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes),
//                                            new Attraction(new Location(17.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes));
//
//        List<Attraction> used = Arrays.asList(new Attraction(new Location(12.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes),
//                new Attraction(new Location(14.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes),
//                new Attraction(new Location(17.0,12.0),3.5, "atr1", Optional.of(Price.FREE), placeTypes));
//
//
//        List<Attraction> spare = planBuilder.createSpareAttractionsList(used,all);
//
//        spare.forEach(a -> System.out.println(a.toString()));

    }
}
