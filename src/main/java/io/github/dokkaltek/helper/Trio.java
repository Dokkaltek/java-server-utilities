package io.github.dokkaltek.helper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Wrapper class to hold three objects of any given type.
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trio<T, R, U> {
    private static final String NULL_ERROR_MESSAGE = "object passed to a Trio class was null.";
    private final T first;
    private final R second;
    private final U third;

    /**
     * Default builder, which doesn't accept null values.
     * @param first The first object.
     * @param second The second object.
     * @return The built {@link Trio}.
     */
    public Trio<T, R, U> of(T first, R second, U third) {
        if (first == null) {
            throw new NullPointerException("First " + NULL_ERROR_MESSAGE);
        }
        if (second == null) {
            throw new NullPointerException("Second " + NULL_ERROR_MESSAGE);
        }
        if (third == null) {
            throw new NullPointerException("Third " + NULL_ERROR_MESSAGE);
        }
        return new Trio<>(first, second, third);
    }

    /**
     * Nullable builder, which accepts null values. The non-nullable version should be used instead for most cases.
     * @param first The first object.
     * @param second The second object.
     * @return The built {@link Trio}.
     */
    public Trio<T, R, U> ofNullable(T first, R second, U third) {
        return new Trio<>(first, second, third);
    }

    /**
     * Returns an empty {@link Trio} object.
     * @return The empty {@link Trio}.
     */
    public Trio<T, R, U> empty() {
        return new Trio<>(null, null, null);
    }

    /**
     * Checks if both values of the {@link Trio} are null.
     * @return True if both values are null, false otherwise.
     */
    public boolean isEmpty() {
        return first == null && second == null && third == null;
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

    /**
     * Getter for the third object.
     * @return The third object.
     */
    public U third() {
        return third;
    }
}
