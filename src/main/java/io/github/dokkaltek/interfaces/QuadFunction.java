package io.github.dokkaltek.interfaces;

import java.util.Objects;
import java.util.function.Function;

/**
 * Functional interface with 4 parameters
 */
@FunctionalInterface
public interface QuadFunction<T,R,S,X,Y> {
    Y apply(T t, R r, S s, X x);

    /**
     * Returns a composed function that first applies this function to its input, and then applies the after function
     * to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     * @param after – the function to apply after this function is applied
     * <V> – the type of output of the after function, and of the composed function
     * @return a composed function that first applies this function and then applies the after function
     * @throws NullPointerException – if after is null
     */
    default <V> QuadFunction<T, R, S, X, V> andThen(Function<? super Y, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, R r, S s, X x) -> after.apply(apply(t, r, s, x));
    }
}
