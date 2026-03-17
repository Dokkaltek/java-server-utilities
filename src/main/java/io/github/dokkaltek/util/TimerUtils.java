package io.github.dokkaltek.util;

import io.github.dokkaltek.helper.Duo;
import io.github.dokkaltek.interfaces.QuadConsumer;
import io.github.dokkaltek.interfaces.QuadFunction;
import io.github.dokkaltek.interfaces.QuinConsumer;
import io.github.dokkaltek.interfaces.QuinFunction;
import io.github.dokkaltek.interfaces.TriConsumer;
import io.github.dokkaltek.interfaces.TriFunction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.Supplier;

/**
 * Utility class to time code blocks.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimerUtils {

    /**
     * Times a code block.
     * @param runnable The code block to time.
     * @return The time taken in milliseconds.
     */
    public static long time(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Times a code block.
     * @param runnable The code block to time.
     * @param time A consumer to accept the time taken in milliseconds.
     */
    public static void time(Runnable runnable, LongConsumer time) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        time.accept(System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param supplier The code block to time.
     * @return A {@link Duo} containing the result and the time taken in milliseconds.
     */
    public static <T> Duo<T, Long> time(Supplier<T> supplier) {
        long startTime = System.currentTimeMillis();
        T result = supplier.get();
        return Duo.ofNullable(result, System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param supplier The code block to time.
     * @param time A consumer to accept the time taken in milliseconds.
     * @return a {@link Duo} containing the result and the time taken in milliseconds.
     */
    public static <T> T time(Supplier<T> supplier, LongConsumer time) {
        long startTime = System.currentTimeMillis();
        T result = supplier.get();
        time.accept(System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The input parameter.
     * @param consumer The code block to time.
     * @return The time taken in milliseconds.
     */
    public static <T> long timeConsumer(T input, Consumer<T> consumer) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input);
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The input parameter.
     * @param consumer The code block to time.
     * @param time A consumer to accept the time taken in milliseconds.
     */
    public static <T> void timeConsumer(T input, Consumer<T> consumer, LongConsumer time) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input);
        time.accept(System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The first input parameter.
     * @param input2 The second input parameter.
     * @param consumer The code block to time.
     * @return The time taken in milliseconds.
     */
    public static <T, R> long timeConsumer(T input, R input2, BiConsumer<T, R> consumer) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input, input2);
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The first input parameter.
     * @param input2 The second input parameter.
     * @param consumer The code block to time.
     * @param time A consumer to accept the time taken in milliseconds.
     */
    public static <T, R> void timeConsumer(T input, R input2, BiConsumer<T, R> consumer, LongConsumer time) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input, input2);
        time.accept(System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The first input parameter.
     * @param input2 The second input parameter.
     * @param input3 The third input parameter.
     * @param consumer The code block to time.
     * @return The time taken in milliseconds.
     */
    public static <T, R, S> long timeConsumer(T input, R input2, S input3, TriConsumer<T, R, S> consumer) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input, input2, input3);
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The first input parameter.
     * @param input2 The second input parameter.
     * @param input3 The third input parameter.
     * @param consumer The code block to time.
     * @param time A consumer to accept the time taken in milliseconds.
     */
    public static <T, R, S> void timeConsumer(T input, R input2, S input3, TriConsumer<T, R, S> consumer,
                                              LongConsumer time) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input, input2, input3);
        time.accept(System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The first input parameter.
     * @param input2 The second input parameter.
     * @param input3 The third input parameter.
     * @param input4 The fourth input parameter.
     * @param consumer The code block to time.
     * @return The time taken in milliseconds.
     */
    public static <T, R, S, U> long timeConsumer(T input, R input2, S input3, U input4,
                                                 QuadConsumer<T, R, S, U> consumer) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input, input2, input3, input4);
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The first input parameter.
     * @param input2 The second input parameter.
     * @param input3 The third input parameter.
     * @param input4 The fourth input parameter.
     * @param consumer The code block to time.
     * @param time A consumer to accept the time taken in milliseconds.
     */
    public static <T, R, S, U> void timeConsumer(T input, R input2, S input3, U input4,
                                                 QuadConsumer<T, R, S, U> consumer,
                                              LongConsumer time) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input, input2, input3, input4);
        time.accept(System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The first input parameter.
     * @param input2 The second input parameter.
     * @param input3 The third input parameter.
     * @param input4 The fourth input parameter.
     * @param input5 The fifth input parameter.
     * @param consumer The code block to time.
     * @return The time taken in milliseconds.
     */
    public static <T, R, S, U, V> long timeConsumer(T input, R input2, S input3, U input4, V input5,
                                                 QuinConsumer<T, R, S, U, V> consumer) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input, input2, input3, input4, input5);
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The first input parameter.
     * @param input2 The second input parameter.
     * @param input3 The third input parameter.
     * @param input4 The fourth input parameter.
     * @param input5 The fifth input parameter.
     * @param consumer The code block to time.
     * @param time A consumer to accept the time taken in milliseconds.
     */
    public static <T, R, S, U, V> void timeConsumer(T input, R input2, S input3, U input4, V input5,
                                                 QuinConsumer<T, R, S, U, V> consumer,
                                                 LongConsumer time) {
        long startTime = System.currentTimeMillis();
        consumer.accept(input, input2, input3, input4, input5);
        time.accept(System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param function The function to time.
     * @return The time taken in milliseconds.
     */
    public static <T, S> Duo<S, Long> timeFunction(T input, Function<T, S> function) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input);
        return Duo.ofNullable(result, System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param function The function to time.
     * @param time A consumer to accept the time taken in milliseconds.
     * @return The time taken in milliseconds.
     */
    public static <T, S> S timeFunction(T input, Function<T, S> function, LongConsumer time) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input);
        time.accept(System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param input2 The second function input parameter.
     * @param function the function to time.
     * @return The time taken in milliseconds.
     */
    public static <T, R, S> Duo<S, Long> timeFunction(T input, R input2, BiFunction<T, R, S> function) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input, input2);
        return Duo.ofNullable(result, System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param input2 The second function input parameter.
     * @param function the function to time.
     * @param time A consumer to accept the time taken in milliseconds.
     * @return The time taken in milliseconds.
     */
    public static <T, R, S> S timeFunction(T input, R input2, BiFunction<T, R, S> function,
                                              LongConsumer time) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input, input2);
        time.accept(System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param input2 The second function input parameter.
     * @param input3 The third function input parameter.
     * @param function the function to time.
     * @return The time taken in milliseconds.
     */
    public static <T, R, V, S> Duo<S, Long> timeFunction(T input, R input2, V input3, TriFunction<T, R, V, S> function) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input, input2, input3);
        return Duo.ofNullable(result, System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param input2 The second function input parameter.
     * @param input3 The third function input parameter.
     * @param function the function to time.
     * @param time A consumer to accept the time taken in milliseconds.
     * @return The time taken in milliseconds.
     */
    public static <T, R, V, S> S timeFunction(T input, R input2, V input3, TriFunction<T, R, V, S> function,
                                                 LongConsumer time) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input, input2, input3);
        time.accept(System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param input2 The second function input parameter.
     * @param input3 The third function input parameter.
     * @param input4 The fourth function input parameter.
     * @param function the function to time.
     * @return The time taken in milliseconds.
     */
    public static <T, R, V, W, S> Duo<S, Long> timeFunction(T input, R input2, V input3, W input4,
                                                    QuadFunction<T, R, V, W, S> function) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input, input2, input3, input4);
        return Duo.ofNullable(result, System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param input2 The second function input parameter.
     * @param input3 The third function input parameter.
     * @param input4 The fourth function input parameter.
     * @param function the function to time.
     * @param time A consumer to accept the time taken in milliseconds.
     * @return The time taken in milliseconds.
     */
    public static <T, R, V, W, S> S timeFunction(T input, R input2, V input3, W input4,
                                                    QuadFunction<T, R, V, W, S> function, LongConsumer time) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input, input2, input3, input4);
        time.accept(System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param input2 The second function input parameter.
     * @param input3 The third function input parameter.
     * @param input4 The fourth function input parameter.
     * @param input5 The fifth function input parameter.
     * @param function the function to time.
     * @return The time taken in milliseconds.
     */
    public static <T, R, V, W, X, S> Duo<S, Long> timeFunction(T input, R input2, V input3, W input4, X input5,
                                                       QuinFunction<T, R, V, W, X, S> function) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input, input2, input3, input4, input5);
        return Duo.ofNullable(result, System.currentTimeMillis() - startTime);
    }

    /**
     * Times a code block and returns the result and the time taken.
     * @param input The function input parameter.
     * @param input2 The second function input parameter.
     * @param input3 The third function input parameter.
     * @param input4 The fourth function input parameter.
     * @param input5 The fifth function input parameter.
     * @param function the function to time.
     * @param time A consumer to accept the time taken in milliseconds.
     * @return The time taken in milliseconds.
     */
    public static <T, R, V, W, X, S> S timeFunction(T input, R input2, V input3, W input4, X input5,
                                                       QuinFunction<T, R, V, W, X, S> function,
                                                       LongConsumer time) {
        long startTime = System.currentTimeMillis();
        S result = function.apply(input, input2, input3, input4, input5);
        time.accept(System.currentTimeMillis() - startTime);
        return result;
    }
}
