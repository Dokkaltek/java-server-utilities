package io.github.dokkaltek.interfaces;

import java.util.Objects;

/**
 * Functional interface with 5 parameters.
 */
@FunctionalInterface
public interface QuinConsumer<T,R,S,U,W> {
    void accept(T t, R r, S s, U u, W w);

    /**
     * Returns a composed consumer that first applies this consumer to its input, and then applies the after consumer
     * to the result. If evaluation of either consumer throws an exception, it is relayed to the caller of the
     * composed consumer.
     * @param after – the consumer to apply after this consumer is applied
     * @return a composed consumer that first applies this consumer and then applies the after consumer
     * @throws NullPointerException – if after is null
     */
    default QuinConsumer<T, R, S, U, W> andThen(QuinConsumer<? super T, ? super R, ? super S, ? super U,
            ? super W> after) {
        Objects.requireNonNull(after);

        return (t, r, s, u, w) -> {
            accept(t, r, s, u, w);
            after.accept(t, r, s, u, w);
        };
    }
}
