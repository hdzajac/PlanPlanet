package pl.edu.agh.model.matching;

import pl.edu.agh.model.data.Location;

public interface LocationResolver {
    Location resolve(String address);
}
