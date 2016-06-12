package pl.edu.agh;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;

public interface Helpers {
    default <T, C extends Collection<T>> Matcher<C> hasDistinctItems() {
        return new TypeSafeMatcher<C>() {
            @Override
            public void describeTo(final Description description) {
                description.appendText("collection should have distinct items");
            }

            @Override
            protected void describeMismatchSafely(final C item, final Description mismatchDescription) {
                mismatchDescription.appendText("had duplicates");
            }

            @Override
            protected boolean matchesSafely(final C item) {
                return new HashSet<>(item).size() == item.size();
            }
        };
    }

    default  Matcher<Duration> lastsBetween(Duration min, Duration max) {
        return new TypeSafeMatcher<Duration>() {
            @Override
            public void describeTo(final Description description) {
                description.appendText("duration should last between ")
                        .appendValue(min)
                        .appendText(" and ")
                        .appendValue(max);
            }

            @Override
            protected void describeMismatchSafely(final Duration item, final Description mismatchDescription) {
                mismatchDescription.appendText("lasts ")
                        .appendValue(item);
            }

            @Override
            protected boolean matchesSafely(final Duration duration) {
                return min.compareTo(duration) <= 0 && duration.compareTo(max) <= 0;
            }
        };
    }
}
