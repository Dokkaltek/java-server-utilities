package io.github.dokkaltek.helper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Wrapper class to hold two objects of any given type.
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Duo<T, R> {
    private static final String NULL_ERROR_MESSAGE = "object passed to a Duo class was null.";
    private final T first;
    private final R second;

    public static <X, Y> Duo<X, Y> of(X first, Y second) {
        if (first == null) {
            throw new NullPointerException("First " + NULL_ERROR_MESSAGE);
        }
        if (second == null) {
            throw new NullPointerException("Second " + NULL_ERROR_MESSAGE);
        }
        return new Duo<>(first, second);
    }

    /**
     * Nullable builder, which accepts null values. The non-nullable version should be used instead for most cases.
     * @param first The first object.
     * @param second The second object.
     * @return The built {@link Duo}.
     */
    public static <X, Y> Duo<X, Y> ofNullable(X first, Y second) {
        return new Duo<>(first, second);
    }

    /**
     * Returns an empty {@link Duo} object.
     * @return The empty {@link Duo}.
     */
    public static <X, Y> Duo<X, Y> empty() {
        return new Duo<>(null, null);
    }

    /**
     * Checks if both values of the {@link Duo} are null.
     * @return True if both values are null, false otherwise.
     */
    public boolean isEmpty() {
        return first == null && second == null;
    }

    /**
     * Getter for the first object.
     * @return The first object.
     */
    public T first() {
        return first;
    }

    /**
     * Getter for the second object.
     * @return The second object.
     */
    public R second() {
        return second;
    }
}
