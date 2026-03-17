package io.github.dokkaltek.util;

import io.github.dokkaltek.helper.Duo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.LongConsumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link TimerUtils} class.
 */
class TimerUtilsTest {

    /**
     * Test {@link TimerUtils#time(Runnable)} method.
     */
    @Test
    @DisplayName("Test getting execution time of runnable")
    void testTimeRunnable() {
        assertNotNull(TimerUtils.time(() -> StringUtils.concatenate("t", "u")));
    }

    /**
     * Test {@link TimerUtils#time(Runnable)} method.
     */
    @Test
    @DisplayName("Test getting execution time of runnable with time consumer")
    void testTimeRunnableWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        assertEquals("test", TimerUtils.time(() -> StringUtils.concatenate("te", "st"),
                time -> resultMap.put(1, time)));
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#time(Runnable)} method.
     */
    @Test
    @DisplayName("Test getting execution time of supplier")
    void testTimeSupplier() {
        Duo<String, Long> result = TimerUtils.time(() -> StringUtils.concatenate("te", "st"));
        assertEquals("test", result.first());
        assertNotNull(result.second());
    }

    /**
     * Test {@link TimerUtils#time(Runnable)} method.
     */
    @Test
    @DisplayName("Test getting execution time of supplier")
    void testTimeSupplierWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        String result = TimerUtils.time(() -> StringUtils.concatenate("te", "st"),
                time -> resultMap.put(1, time));
        assertEquals("test", result);
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, java.util.function.Consumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of supplier")
    void testTimeConsumer() {
        assertDoesNotThrow(() -> TimerUtils.timeConsumer("test",
                (String value) -> StringUtils.concatenate("te", "st")));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, java.util.function.Consumer, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of consumer with time consumer")
    void testTimeConsumerWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        TimerUtils.timeConsumer("test",
                (String value) -> StringUtils.concatenate(value, "1"),
                time -> resultMap.put(1, time));
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, Object, java.util.function.BiConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of biconsumer")
    void testTimeBiConsumer() {
        assertDoesNotThrow(() -> TimerUtils.timeConsumer("te", "st",
                StringUtils::concatenate));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, Object, java.util.function.BiConsumer, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of biconsumer with time consumer")
    void testTimeBiConsumerWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        TimerUtils.timeConsumer("te", "st",
                StringUtils::concatenate,
                time -> resultMap.put(1, time));
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, Object, Object, io.github.dokkaltek.interfaces.TriConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of triconsumer")
    void testTimeTriConsumer() {
        assertDoesNotThrow(() -> TimerUtils.timeConsumer("t", "e", "st",
                StringUtils::concatenate));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, Object, Object, io.github.dokkaltek.interfaces.TriConsumer, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of triconsumer with time consumer")
    void testTimeTriConsumerWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        TimerUtils.timeConsumer("t", "e", "st",
                StringUtils::concatenate,
                time -> resultMap.put(1, time));
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, Object, Object, Object, io.github.dokkaltek.interfaces.QuadConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of quadconsumer")
    void testTimeQuadConsumer() {
        assertDoesNotThrow(() -> TimerUtils.timeConsumer("t", "e", "s", "t",
                StringUtils::concatenate));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, Object, Object, Object, io.github.dokkaltek.interfaces.QuadConsumer, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of quadconsumer with time consumer")
    void testTimeQuadConsumerWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        TimerUtils.timeConsumer("t", "e", "s", "t",
                StringUtils::concatenate,
                time -> resultMap.put(1, time));
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, Object, Object, Object, Object, io.github.dokkaltek.interfaces.QuinConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of quinconsumer")
    void testTimeQuinConsumer() {
        assertDoesNotThrow(() -> TimerUtils.timeConsumer("t", "e", "s", "t", "ing",
                StringUtils::concatenate));
    }

    /**
     * Test {@link TimerUtils#timeConsumer(Object, Object, Object, Object, Object, io.github.dokkaltek.interfaces.QuinConsumer, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of quinconsumer with time consumer")
    void testTimeQuinConsumerWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        TimerUtils.timeConsumer("t", "e", "s", "t", "ing",
                StringUtils::concatenate,
                time -> resultMap.put(1, time));
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, java.util.function.Function)} method.
     */
    @Test
    @DisplayName("Test getting execution time of function")
    void testTimeFunction() {
        Duo<String, Long> result = TimerUtils.timeFunction("test",
                (String input) -> StringUtils.concatenate(input, "ing"));
        assertEquals("testing", result.first());
        assertNotNull(result.second());
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, java.util.function.Function, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of function with time consumer")
    void testTimeFunctionWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        String result = TimerUtils.timeFunction("test",
                (String input) -> StringUtils.concatenate(input, "ing"),
                time -> resultMap.put(1, time));
        assertEquals("testing", result);
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, Object, java.util.function.BiFunction)} method.
     */
    @Test
    @DisplayName("Test getting execution time of bifunction")
    void testTimeBiFunction() {
        Duo<String, Long> result = TimerUtils.timeFunction("test", "ing",
                StringUtils::concatenate);
        assertEquals("testing", result.first());
        assertNotNull(result.second());
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, Object, java.util.function.BiFunction, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of bifunction with time consumer")
    void testTimeBiFunctionWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        String result = TimerUtils.timeFunction("test", "ing",
                StringUtils::concatenate,
                time -> resultMap.put(1, time));
        assertEquals("testing", result);
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, Object, Object, io.github.dokkaltek.interfaces.TriFunction)} method.
     */
    @Test
    @DisplayName("Test getting execution time of trifunction")
    void testTimeTriFunction() {
        Duo<String, Long> result = TimerUtils.timeFunction("t", "e", "sting",
                StringUtils::concatenate);
        assertEquals("testing", result.first());
        assertNotNull(result.second());
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, Object, Object, io.github.dokkaltek.interfaces.TriFunction, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of trifunction with time consumer")
    void testTimeTriFunctionWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        String result = TimerUtils.timeFunction("t", "e", "sting",
                StringUtils::concatenate,
                time -> resultMap.put(1, time));
        assertEquals("testing", result);
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, Object, Object, Object, io.github.dokkaltek.interfaces.QuadFunction)} method.
     */
    @Test
    @DisplayName("Test getting execution time of quadfunction")
    void testTimeQuadFunction() {
        Duo<String, Long> result = TimerUtils.timeFunction("t", "e", "s", "ting",
                StringUtils::concatenate);
        assertEquals("testing", result.first());
        assertNotNull(result.second());
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, Object, Object, Object, io.github.dokkaltek.interfaces.QuadFunction, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of quadfunction with time consumer")
    void testTimeQuadFunctionWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        String result = TimerUtils.timeFunction("t", "e", "s", "ting",
                StringUtils::concatenate,
                time -> resultMap.put(1, time));
        assertEquals("testing", result);
        assertNotNull(resultMap.get(1));
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, Object, Object, Object, Object, io.github.dokkaltek.interfaces.QuinFunction)} method.
     */
    @Test
    @DisplayName("Test getting execution time of quinfunction")
    void testTimeQuinFunction() {
        Duo<String, Long> result = TimerUtils.timeFunction("t", "e", "s", "t", "ing",
                StringUtils::concatenate);
        assertEquals("testing", result.first());
        assertNotNull(result.second());
    }

    /**
     * Test {@link TimerUtils#timeFunction(Object, Object, Object, Object, Object, io.github.dokkaltek.interfaces.QuinFunction, LongConsumer)} method.
     */
    @Test
    @DisplayName("Test getting execution time of quinfunction with time consumer")
    void testTimeQuinFunctionWithConsumer() {
        Map<Integer, Long> resultMap = new HashMap<>();
        String result = TimerUtils.timeFunction("t", "e", "s", "t", "ing",
                StringUtils::concatenate,
                time -> resultMap.put(1, time));
        assertEquals("testing", result);
        assertNotNull(resultMap.get(1));
    }
}
