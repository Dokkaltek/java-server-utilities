package io.github.dokkaltek.interfaces;

import java.util.Objects;

/**
 * Functional interface with 3 parameters.
 */
@FunctionalInterface
public interface TriConsumer<T,R,S> {
    void accept(T t, R r, S s);

    /**
     * Returns a composed consumer that first applies this consumer to its input, and then applies the after consumer
     * to the result. If evaluation of either consumer throws an exception, it is relayed to the caller of the
     * composed consumer.
     * @param after – the consumer to apply after this consumer is applied
     * @return a composed consumer that first applies this consumer and then applies the after consumer
     * @throws NullPointerException – if after is null
     */
    default TriConsumer<T, R, S> andThen(TriConsumer<? super T, ? super R, ? super S> after) {
        Objects.requireNonNull(after);

        return (t, r, s) -> {
            accept(t, r, s);
            after.accept(t, r, s);
        };
    }
}
