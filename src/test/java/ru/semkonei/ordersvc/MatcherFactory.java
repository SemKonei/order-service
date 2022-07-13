package ru.semkonei.ordersvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherFactory {
    public static <T> Matcher<T> usingIgnoringFieldsComparator(String... ignoringFields) {
        return new Matcher<>(ignoringFields);
    }

    public static class Matcher<T>{
        private final String[] ignoredFields;

        private Matcher(String... ignoredFields) {
            this.ignoredFields = ignoredFields;
        }

        public void assertMatch(T actual, T expected) {
            assertThat(actual).usingRecursiveComparison().ignoringFields(ignoredFields).isEqualTo(expected);
        }

        @SafeVarargs
        public final void assertMatch(Iterable<T> actual, T... expected) {
            assertMatch(actual, List.of(expected));
        }

        public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
            assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(ignoredFields).isEqualTo(expected);
        }
    }
}
