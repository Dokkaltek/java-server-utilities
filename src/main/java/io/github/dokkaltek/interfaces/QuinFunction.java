package io.github.dokkaltek.interfaces;

import java.util.Objects;
import java.util.function.Function;

/**
 * Functional interface with 5 parameters
 */
@FunctionalInterface
public interface QuinFunction<T,R,S,X,Z,Y> {
    Y apply(T t, R r, S s, X x, Z z);

    /**
     * Returns a composed function that first applies this function to its input, and then applies the after function
     * to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     * @param after – the function to apply after this function is applied
     * <V> – the type of output of the after function, and of the composed function
     * @return a composed function that first applies this function and then applies the after function
     * @throws NullPointerException – if after is null
     */
    default <V> QuinFunction<T, R, S, X, Z, V> andThen(Function<? super Y, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, R r, S s, X x, Z z) -> after.apply(apply(t, r, s, x, z));
    }
}
