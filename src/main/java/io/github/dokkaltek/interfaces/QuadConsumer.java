package io.github.dokkaltek.interfaces;

import java.util.Objects;

/**
 * Functional interface with 4 parameters.
 */
@FunctionalInterface
public interface QuadConsumer<T,R,S,U> {
    void accept(T t, R r, S s, U u);

    /**
     * Returns a composed consumer that first applies this consumer to its input, and then applies the after consumer
     * to the result. If evaluation of either consumer throws an exception, it is relayed to the caller of the
     * composed consumer.
     * @param after – the consumer to apply after this consumer is applied
     * @return a composed consumer that first applies this consumer and then applies the after consumer
     * @throws NullPointerException – if after is null
     */
    default QuadConsumer<T, R, S, U> andThen(QuadConsumer<? super T, ? super R, ? super S, ? super U> after) {
        Objects.requireNonNull(after);

        return (t, r, s, u) -> {
            accept(t, r, s, u);
            after.accept(t, r, s, u);
        };
    }
}
