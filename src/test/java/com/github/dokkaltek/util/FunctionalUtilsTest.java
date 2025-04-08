package com.github.dokkaltek.util;

import com.github.dokkaltek.exception.ResultNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.dokkaltek.samples.SamplePojo;

import java.util.function.Supplier;

import static com.github.dokkaltek.constant.literal.SpecialChars.EMPTY_STRING;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link FunctionalUtils} class.
 */
class FunctionalUtilsTest {
    private static final String DEFAULT_VALUE = "default";
    private static final String INITIAL_VALUE = "initialValue";

    /**
     * Tests {@link FunctionalUtils#ifNullThenReturn(Object, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value when the object is null")
    void testIfNullThenReturn() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifNullThenReturn(null, DEFAULT_VALUE));
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifNullThenReturn(INITIAL_VALUE, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifNullThenThrow(Object, RuntimeException)} method.
     */
    @Test
    @DisplayName("Test throwing an exception when the object is null")
    void testIfNullThenThrow() {
        ResultNotFoundException exception = new ResultNotFoundException();
        assertThrows(ResultNotFoundException.class, () -> FunctionalUtils.ifNullThenThrow(null, exception));
        assertDoesNotThrow(() -> FunctionalUtils.ifNullThenThrow(INITIAL_VALUE, exception));
    }

    /**
     * Tests {@link FunctionalUtils#ifNullThenRun(Object, Runnable)} method.
     */
    @Test
    @DisplayName("Test running a function when the value is null")
    void testIfNullThenRun() {
        SamplePojo input = new SamplePojo();
        FunctionalUtils.ifNullThenRun(null, () -> input.setName(DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, input.getName());

        FunctionalUtils.ifNullThenRun(INITIAL_VALUE, () -> input.setName(DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, input.getName());
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenRun(Object, Runnable)} method.
     */
    @Test
    @DisplayName("Test running a function when the value is not null")
    void testIfNotNullThenRun() {
        SamplePojo input = new SamplePojo();
        FunctionalUtils.ifNotNullThenRun(null, () -> input.setName(DEFAULT_VALUE));
        assertNull(input.getName());

        FunctionalUtils.ifNotNullThenRun(INITIAL_VALUE, () -> input.setName(DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, input.getName());
    }

    /**
     * Tests {@link FunctionalUtils#ifNullThenSupply(Object, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the input is null")
    void testIfNullThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifNullThenSupply(null, () -> DEFAULT_VALUE).firstResult());
        assertNull(FunctionalUtils.ifNullThenSupply(INITIAL_VALUE, () -> DEFAULT_VALUE).firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenSupply(Object, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the input is not null")
    void testIfNotNullThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifNotNullThenSupply(INITIAL_VALUE, () -> DEFAULT_VALUE).firstResult());
        assertNull(FunctionalUtils.ifNotNullThenSupply(null, () -> DEFAULT_VALUE).firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifBlankOrNullThenReturn(String, String)} method.
     */
    @Test
    @DisplayName("Test returning a default value when a string is blank or null")
    void testIfBlankOrNullThenReturn() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifBlankOrNullThenReturn(EMPTY_STRING, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifBlankOrNullThenReturn(" ", DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifBlankOrNullThenReturn(null, DEFAULT_VALUE));
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifBlankOrNullThenReturn(INITIAL_VALUE, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifBlankOrNullThenThrow(String, RuntimeException)} method.
     */
    @Test
    @DisplayName("Test throwing an exception when a string is blank or null")
    void testIfBlankOrNullThenThrow() {
        ResultNotFoundException exception = new ResultNotFoundException();
        assertThrows(ResultNotFoundException.class, () ->
                FunctionalUtils.ifBlankOrNullThenThrow(EMPTY_STRING, exception));
        assertThrows(ResultNotFoundException.class,
                () -> FunctionalUtils.ifBlankOrNullThenThrow(" ", exception));
        assertThrows(ResultNotFoundException.class,
                () -> FunctionalUtils.ifBlankOrNullThenThrow(null, exception));
        assertDoesNotThrow(() -> FunctionalUtils.ifBlankOrNullThenThrow(INITIAL_VALUE, exception));
    }

    /**
     * Tests {@link FunctionalUtils#ifBlankOrNullThenRun(String, Runnable)} method.
     */
    @Test
    @DisplayName("Test running a function when the string is blank or null")
    void testIfBlankOrNullThenRun() {
        SamplePojo input = new SamplePojo();
        FunctionalUtils.ifBlankOrNullThenRun(null, () -> input.setName(DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, input.getName());

        FunctionalUtils.ifBlankOrNullThenRun(INITIAL_VALUE, () -> input.setName(DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, input.getName());
    }

    /**
     * Tests {@link FunctionalUtils#ifBlankOrNullThenSupply(String, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the string is blank or null")
    void testIfBlankOrNullThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifBlankOrNullThenSupply(null, () -> DEFAULT_VALUE).firstResult());
        assertNull(FunctionalUtils.ifBlankOrNullThenSupply(INITIAL_VALUE, () -> DEFAULT_VALUE).firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenReturn(boolean, Object)} method.
     */
    @Test
    @SuppressWarnings("ConstantValue")
    @DisplayName("Test returning a value when the condition is true")
    void testIfTrueThenReturn() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenReturn(true, DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifTrueThenReturn(false, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenReturn(boolean, Object)} method.
     */
    @Test
    @SuppressWarnings("ConstantValue")
    @DisplayName("Test returning a value when the condition is false")
    void testIfFalseThenReturn() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenReturn(false, DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifFalseThenReturn(true, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenThrow(boolean, RuntimeException)} method.
     */
    @Test
    @DisplayName("Test throwing an exception when the condition is true")
    void testIfTrueThenThrow() {
        ResultNotFoundException exception = new ResultNotFoundException();
        assertThrows(ResultNotFoundException.class, () -> FunctionalUtils.ifTrueThenThrow(true, exception));
        assertDoesNotThrow(() -> FunctionalUtils.ifTrueThenThrow(false, exception));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenThrow(boolean, RuntimeException)} method.
     */
    @Test
    @DisplayName("Test throwing an exception when the condition is false")
    void testIfFalseThenThrow() {
        ResultNotFoundException exception = new ResultNotFoundException();
        assertThrows(ResultNotFoundException.class, () -> FunctionalUtils.ifFalseThenThrow(false, exception));
        assertDoesNotThrow(() -> FunctionalUtils.ifFalseThenThrow(true, exception));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenRun(boolean, Runnable)} method.
     */
    @Test
    @DisplayName("Test running a function when the condition is true")
    void testIfTrueThenRun() {
        SamplePojo input = new SamplePojo();
        FunctionalUtils.ifTrueThenRun(true, () -> input.setName(INITIAL_VALUE));
        assertEquals(INITIAL_VALUE, input.getName());

        FunctionalUtils.ifTrueThenRun(false, () -> input.setName(DEFAULT_VALUE));
        assertEquals(INITIAL_VALUE, input.getName());
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenSupply(boolean, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the condition is true")
    void testIfTrueThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenSupply(true, () -> DEFAULT_VALUE).firstResult());
        assertNull(FunctionalUtils.ifTrueThenSupply(false, () -> DEFAULT_VALUE).firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenRun(boolean, Runnable)} method.
     */
    @Test
    @DisplayName("Test running a function when the condition is false")
    void testIfFalseThenRun() {
        SamplePojo input = new SamplePojo();
        FunctionalUtils.ifFalseThenRun(false, () -> input.setName(INITIAL_VALUE));
        assertEquals(INITIAL_VALUE, input.getName());

        FunctionalUtils.ifFalseThenRun(true, () -> input.setName(DEFAULT_VALUE));
        assertEquals(INITIAL_VALUE, input.getName());
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenSupply(boolean, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the condition is false")
    void testIfFalseThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenSupply(false, () -> DEFAULT_VALUE).firstResult());
        assertNull(FunctionalUtils.ifFalseThenSupply(true, () -> DEFAULT_VALUE).firstResult());
    }
}
