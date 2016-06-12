package pl.edu.agh.model.routecreator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TimeInterval {
    private final LocalDate start;
    private final LocalDate end; // inclusive

    public TimeInterval(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int lengthInDays() {
        return (int) ChronoUnit.DAYS.between(getStart(), getEnd()) + 1;
    }
}
