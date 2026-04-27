package io.github.dokkaltek.util;

import io.github.dokkaltek.exception.ResultNotFoundException;
import io.github.dokkaltek.helper.ResultChain;
import io.github.dokkaltek.helper.WrapperList;
import io.github.dokkaltek.interfaces.QuadConsumer;
import io.github.dokkaltek.interfaces.QuadFunction;
import io.github.dokkaltek.interfaces.QuinConsumer;
import io.github.dokkaltek.interfaces.QuinFunction;
import io.github.dokkaltek.interfaces.TriConsumer;
import io.github.dokkaltek.interfaces.TriFunction;
import io.github.dokkaltek.samples.SamplePojo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.dokkaltek.constant.literal.SpecialChars.EMPTY_STRING;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
     * Tests {@link FunctionalUtils#ifNullThenSupplyChain(Object, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the input is null")
    void testIfNullThenSupplyChain() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifNullThenSupplyChain(null, () -> DEFAULT_VALUE).get());
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifNullThenSupplyChain(INITIAL_VALUE, () -> DEFAULT_VALUE).get());
    }

    /**
     * Tests {@link FunctionalUtils#ifNullThenSupply(Object, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a value when the input is null")
    void testIfNullThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifNullThenSupply(null, () -> DEFAULT_VALUE));
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifNullThenSupply(INITIAL_VALUE, () -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenSupplyChain(Object, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the input is not null")
    void testIfNotNullThenSupplyChain() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifNotNullThenSupplyChain(INITIAL_VALUE, () -> DEFAULT_VALUE).get());
        ResultChain result = FunctionalUtils.ifNotNullThenSupplyChain(null, () -> DEFAULT_VALUE);
        assertNull(result.firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenSupply(Object, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a value when the input is not null")
    void testIfNotNullThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifNotNullThenSupply(INITIAL_VALUE, () -> DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifNotNullThenSupply(null, () -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenApply(Object, Function)} method.
     */
    @Test
    @DisplayName("Test returning a value after checking the input is not null (1 param)")
    void testIfNotNullThenApply1Param() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifNotNullThenApply(INITIAL_VALUE, (String value) -> value));
        assertNull(FunctionalUtils.ifNotNullThenApply(null, (String value) -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenApply(Object, Object, BiFunction)} method.
     */
    @Test
    @DisplayName("Test returning a value after checking the input is not null (2 params)")
    void testIfNotNullThenApply2Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifNotNullThenApply(INITIAL_VALUE, INITIAL_VALUE,
                        (String value1, String value2) -> value1 + value2));
        assertNull(FunctionalUtils.ifNotNullThenApply(null, INITIAL_VALUE,
                (String value1, String value2) -> value1 + value2));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenApply(Object, Object, Object, TriFunction)} method.
     */
    @Test
    @DisplayName("Test returning a value after checking the input is not null (3 params)")
    void testIfNotNullThenApply3Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifNotNullThenApply(INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        StringUtils::concatenate));
        assertNull(FunctionalUtils.ifNotNullThenApply(null, INITIAL_VALUE, INITIAL_VALUE,
                (String value1, String value2, String value3) -> value1 + value2 + value3));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenApply(Object, Object, Object, Object, QuadFunction)} method.
     */
    @Test
    @DisplayName("Test returning a value after checking the input is not null (4 params)")
    void testIfNotNullThenApply4Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifNotNullThenApply(INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        StringUtils::concatenate));
        assertNull(FunctionalUtils.ifNotNullThenApply(null, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                (String value1, String value2, String value3, String value4) -> value1 + value2 + value3 + value4));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenApply(Object, Object, Object, Object, Object, QuinFunction)} method.
     */
    @Test
    @DisplayName("Test returning a value after checking the input is not null (5 params)")
    void testIfNotNullThenApply5Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifNotNullThenApply(INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifNotNullThenApply(null, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, null,
                (String value1, String value2, String value3, String value4, String value5) ->
                        value1 + value2 + value3 + value4 + value5));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenConsume(Object, Consumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function after checking the input is not null (1 param)")
    void testIfNotNullThenConsume1Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifNotNullThenConsume(INITIAL_VALUE, (String value) ->
                StringUtils.concatenate(value, INITIAL_VALUE)));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenConsume(Object, Object, BiConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function after checking the input is not null (2 param)")
    void testIfNotNullThenConsume2Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifNotNullThenConsume(INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenConsume(Object, Object, Object, TriConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function after checking the input is not null (3 param)")
    void testIfNotNullThenConsume3Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifNotNullThenConsume(INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenConsume(Object, Object, Object, Object, QuadConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function after checking the input is not null (4 param)")
    void testIfNotNullThenConsume4Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifNotNullThenConsume(INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotNullThenConsume(Object, Object, Object, Object, Object, QuinConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function after checking the input is not null (5 param)")
    void testIfNotNullThenConsume5Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifNotNullThenConsume(INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
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
     * Tests {@link FunctionalUtils#ifBlankOrNullThenSupplyChain(String, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the string is blank or null")
    void testIfBlankOrNullThenSupplyChain() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifBlankOrNullThenSupplyChain(null, () -> DEFAULT_VALUE).firstResult());
        ResultChain result = FunctionalUtils.ifBlankOrNullThenSupplyChain(INITIAL_VALUE, () -> DEFAULT_VALUE);
        assertNull(result.firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifBlankOrNullThenSupply(String, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a value when the string is blank or null")
    void testIfBlankOrNullThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifBlankOrNullThenSupply(null, () -> DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifBlankOrNullThenSupply(INITIAL_VALUE, () -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotBlankOrNullThenApply(String, Function)} method.
     */
    @Test
    @DisplayName("Test returning a value after mapping it when the input is not blank or null")
    void testIfNotBlankOrNullThenApply() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifNotBlankOrNullThenApply(INITIAL_VALUE, (String value) -> value));
        assertNull(FunctionalUtils.ifNotBlankOrNullThenApply(null, (String value) -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotBlankOrNullThenRun(String, Runnable)} method.
     */
    @Test
    @DisplayName("Test running a function if the input is not blank or null")
    void testIfNotBlankOrNullThenRun() {
        List<String> emptyList = new ArrayList<>(2);
        assertNotNull(FunctionalUtils.ifNotBlankOrNullThenRun(INITIAL_VALUE, () -> emptyList.add(INITIAL_VALUE)));
        assertNotNull(FunctionalUtils.ifNotBlankOrNullThenRun(null, () ->emptyList.add(DEFAULT_VALUE)));
        assertNotNull(FunctionalUtils.ifNotBlankOrNullThenRun(EMPTY_STRING, () ->emptyList.add(DEFAULT_VALUE)));
        assertEquals(INITIAL_VALUE, emptyList.get(0));
        assertEquals(1, emptyList.size());
    }

    /**
     * Tests {@link FunctionalUtils#ifEmptyOrNullThenReturn(Collection, Collection)} method.
     */
    @Test
    @DisplayName("Test returning a default value when a collection is empty or null")
    void testIfEmptyOrNullThenReturn() {
        List<String> defaultList = WrapperList.of(DEFAULT_VALUE);
        assertEquals(defaultList, FunctionalUtils.ifEmptyOrNullThenReturn(Collections.emptyList(), defaultList));
        assertEquals(defaultList, FunctionalUtils.ifEmptyOrNullThenReturn(null, defaultList));
        assertEquals(WrapperList.of(INITIAL_VALUE),
                FunctionalUtils.ifEmptyOrNullThenReturn(WrapperList.of(INITIAL_VALUE), defaultList));
    }

    /**
     * Tests {@link FunctionalUtils#ifEmptyOrNullThenThrow(Collection, RuntimeException)} method.
     */
    @Test
    @DisplayName("Test throwing an exception when a collection is empty or null")
    void testIfEmptyOrNullThenThrow() {
        ResultNotFoundException exception = new ResultNotFoundException();
        List<String> emptyList = Collections.emptyList();
        assertThrows(ResultNotFoundException.class,
                () -> FunctionalUtils.ifEmptyOrNullThenThrow(emptyList, exception));
        assertThrows(ResultNotFoundException.class,
                () -> FunctionalUtils.ifEmptyOrNullThenThrow(null, exception));
        assertDoesNotThrow(() -> FunctionalUtils.ifEmptyOrNullThenThrow(WrapperList.of(INITIAL_VALUE), exception));
    }

    /**
     * Tests {@link FunctionalUtils#ifEmptyOrNullThenRun(Collection, Runnable)} method.
     */
    @Test
    @DisplayName("Test running a function when the collection is empty or null")
    void testIfEmptyOrNullThenRun() {
        SamplePojo input = new SamplePojo();
        FunctionalUtils.ifEmptyOrNullThenRun(null, () -> input.setName(DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, input.getName());

        FunctionalUtils.ifEmptyOrNullThenRun(WrapperList.of(INITIAL_VALUE), () -> input.setName(DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, input.getName());
    }

    /**
     * Tests {@link FunctionalUtils#ifEmptyOrNullThenSupplyChain(Collection, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the collection is empty or null")
    void testIfEmptyOrNullThenSupplyChain() {
        List<String> initialList = WrapperList.of(INITIAL_VALUE);
        List<String> emptyList = Collections.emptyList();
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifEmptyOrNullThenSupplyChain(null, () -> DEFAULT_VALUE).firstResult());
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifEmptyOrNullThenSupplyChain(emptyList, () -> DEFAULT_VALUE).firstResult());
        ResultChain result = FunctionalUtils.ifEmptyOrNullThenSupplyChain(initialList, () -> DEFAULT_VALUE);
        assertNull(result.firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifEmptyOrNullThenSupply(Collection, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a value when the collection is empty or null")
    void testIfEmptyOrNullThenSupply() {
        List<String> initialList = WrapperList.of(INITIAL_VALUE);
        List<String> emptyList = Collections.emptyList();
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifEmptyOrNullThenSupply(null, () -> DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifEmptyOrNullThenSupply(emptyList, () -> DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifEmptyOrNullThenSupply(initialList, () -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifNotEmptyOrNullThenApply(Collection, Function)} method.
     */
    @Test
    @DisplayName("Test returning a value after mapping it when the collection is not empty or null")
    void testIfNotEmptyOrNullThenApply() {
        List<String> initialList = WrapperList.of(INITIAL_VALUE);
        List<String> emptyList = Collections.emptyList();
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifNotEmptyOrNullThenApply(initialList,
                value -> DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifNotEmptyOrNullThenApply(emptyList, value -> DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifNotEmptyOrNullThenApply(null, value -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenReturn(boolean, Object)} method.
     */
    @Test
    @DisplayName("Test returning a value when the condition is true")
    void testIfTrueThenReturn() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenReturn(true, DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifTrueThenReturn(false, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenReturn(boolean, Object, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value when the condition is true")
    void testIfTrueThenReturnWithDefaultValue() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifTrueThenReturn(true, INITIAL_VALUE, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenReturn(false, INITIAL_VALUE, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenReturn(boolean, Object)} method.
     */
    @Test
    @DisplayName("Test returning a value when the condition is false")
    void testIfFalseThenReturn() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenReturn(false, DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifFalseThenReturn(true, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenReturn(boolean, Object, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value when the condition is false")
    void testIfFalseThenReturnWithDefaultValue() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifFalseThenReturn(false, INITIAL_VALUE, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenReturn(true, INITIAL_VALUE, DEFAULT_VALUE));
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
     * Tests {@link FunctionalUtils#ifTrueThenSupplyChain(boolean, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the condition is true")
    void testIfTrueThenSupplyChain() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenSupplyChain(true, () -> DEFAULT_VALUE).firstResult());
        ResultChain result = FunctionalUtils.ifTrueThenSupplyChain(false, () -> DEFAULT_VALUE);
        assertNull(result.firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenSupplyChain(boolean, Supplier, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default function value when the condition is true")
    void testIfTrueThenSupplyChainWithDefaultValue() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifTrueThenSupplyChain(true,
                () -> INITIAL_VALUE, DEFAULT_VALUE).firstResult());
        ResultChain result = FunctionalUtils.ifTrueThenSupplyChain(false, () -> INITIAL_VALUE, DEFAULT_VALUE);
        assertEquals(DEFAULT_VALUE, result.firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenSupply(boolean, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a value when the condition is true")
    void testIfTrueThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenSupply(true, () -> DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifTrueThenSupply(false, () -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenSupply(boolean, Supplier, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value when the condition is true")
    void testIfTrueThenSupplyWithDefaultValue() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifTrueThenSupply(true, () -> INITIAL_VALUE, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenSupply(false, () -> INITIAL_VALUE, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Function)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is true with the given params (1 param)")
    void testIfTrueThenApply1Param() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifTrueThenApply(true, INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifTrueThenApply(false, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Function, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is true with the given params (1 param)")
    void testIfTrueThenApply1ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifTrueThenApply(true, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenApply(false, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Object, BiFunction)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is true with the given params (2 param)")
    void testIfTrueThenApply2Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifTrueThenApply(true, INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifTrueThenApply(false,
                INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Object, BiFunction, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is true with the given params (2 param)")
    void testIfTrueThenApply2ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE, FunctionalUtils.ifTrueThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenApply(false, INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Object, Object, TriFunction)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is true with the given params (3 param)")
    void testIfTrueThenApply3Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifTrueThenApply(true, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        StringUtils::concatenate));
        assertNull(FunctionalUtils.ifTrueThenApply(false,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Object, Object, TriFunction, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is true with the given params (3 param)")
    void testIfTrueThenApply3ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifTrueThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenApply(false,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Object, Object, Object, QuadFunction)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is true with the given params (4 param)")
    void testIfTrueThenApply4Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifTrueThenApply(true, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifTrueThenApply(false,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Object, Object, Object, QuadFunction, Object)}
     * method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is true with the given params (4 param)")
    void testIfTrueThenApply4ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifTrueThenApply(true, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenApply(false,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,INITIAL_VALUE, StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Object, Object, Object, Object, QuinFunction)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is true with the given params (5 param)")
    void testIfTrueThenApply5Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifTrueThenApply(true, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifTrueThenApply(false,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenApply(boolean, Object, Object, Object, Object, Object, QuinFunction,
     * Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is true with the given params (5 param)")
    void testIfTrueThenApply5ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifTrueThenApply(true, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifTrueThenApply(false,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate,
                DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenConsume(boolean, Object, Consumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is true (1 param)")
    void testIfTrueThenConsume1Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifTrueThenConsume(true, INITIAL_VALUE,
                (String value) -> StringUtils.concatenate(value, INITIAL_VALUE)));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenConsume(boolean, Object, Object, BiConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is true (2 param)")
    void testIfTrueThenConsume2Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifTrueThenConsume(true, INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenConsume(boolean, Object, Object, Object, TriConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is true (3 param)")
    void testIfTrueThenConsume3Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifTrueThenConsume(true, INITIAL_VALUE, INITIAL_VALUE,
                INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenConsume(boolean, Object, Object, Object, Object, QuadConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is true (4 param)")
    void testIfTrueThenConsume4Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifTrueThenConsume(true, INITIAL_VALUE, INITIAL_VALUE,
                INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifTrueThenConsume(boolean, Object, Object, Object, Object, Object, QuinConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is true (5 param)")
    void testIfTrueThenConsume5Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifTrueThenConsume(true, INITIAL_VALUE, INITIAL_VALUE,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
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
     * Tests {@link FunctionalUtils#ifFalseThenSupplyChain(boolean, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a function value when the condition is false")
    void testIfFalseThenSupplyChain() {
        ResultChain result = FunctionalUtils.ifFalseThenSupplyChain(true, () -> DEFAULT_VALUE);
        assertNull(result.firstResult());
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenSupplyChain(false,
                () -> DEFAULT_VALUE).firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenSupplyChain(boolean, Supplier, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value when the condition is false")
    void testIfFalseThenSupplyChainWithDefaultValue() {
        ResultChain result = FunctionalUtils.ifFalseThenSupplyChain(true, () -> INITIAL_VALUE, DEFAULT_VALUE);
        assertEquals(DEFAULT_VALUE, result.firstResult());
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifFalseThenSupplyChain(false,
                () -> INITIAL_VALUE, DEFAULT_VALUE).firstResult());
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenSupply(boolean, Supplier)} method.
     */
    @Test
    @DisplayName("Test returning a value when the condition is false")
    void testIfFalseThenSupply() {
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenSupply(false, () -> DEFAULT_VALUE));
        assertNull(FunctionalUtils.ifFalseThenSupply(true, () -> DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenSupply(boolean, Supplier, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value when the condition is false")
    void testIfFalseThenSupplyWithDefaultValue() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifFalseThenSupply(false, () -> INITIAL_VALUE, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenSupply(true, () -> INITIAL_VALUE, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Function)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is false with the given params (1 param)")
    void testIfFalseThenApply1Param() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifFalseThenApply(true, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Function, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is false with the given params (1 param)")
    void testIfFalseThenApply1ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE, FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenApply(true, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Object, BiFunction)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is false with the given params (2 param)")
    void testIfFalseThenApply2Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifFalseThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Object, BiFunction, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is false with the given params (2 param)")
    void testIfFalseThenApply2ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, INITIAL_VALUE,
                        StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Object, Object, TriFunction)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is false with the given params (3 param)")
    void testIfFalseThenApply3Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        StringUtils::concatenate));
        assertNull(FunctionalUtils.ifFalseThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Object, Object, TriFunction, Object)} method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is false with the given params (3 param)")
    void testIfFalseThenApply3ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Object, Object, Object, QuadFunction)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is false with the given params (4 param)")
    void testIfFalseThenApply4Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifFalseThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Object, Object, Object, QuadFunction, Object)}
     * method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is false with the given params (4 param)")
    void testIfFalseThenApply4ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,INITIAL_VALUE, StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Object, Object, Object, Object, QuinFunction)} method.
     */
    @Test
    @DisplayName("Test running a function if condition is false with the given params (5 param)")
    void testIfFalseThenApply5Param() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
        assertNull(FunctionalUtils.ifFalseThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenApply(boolean, Object, Object, Object, Object, Object, QuinFunction)} method.
     */
    @Test
    @DisplayName("Test returning a default value if condition is false with the given params (5 param)")
    void testIfFalseThenApply5ParamWithDefaultValue() {
        assertEquals(INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE + INITIAL_VALUE,
                FunctionalUtils.ifFalseThenApply(false, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,
                        INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, FunctionalUtils.ifFalseThenApply(true,
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE,INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate, DEFAULT_VALUE));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenConsume(boolean, Object, Consumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is false (1 param)")
    void testIfFalseThenConsume1Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifFalseThenConsume(false, INITIAL_VALUE,
                (String value) -> StringUtils.concatenate(value, INITIAL_VALUE)));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenConsume(boolean, Object, Object, BiConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is false (2 param)")
    void testIfFalseThenConsume2Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifFalseThenConsume(false, INITIAL_VALUE, INITIAL_VALUE,
                StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenConsume(boolean, Object, Object, Object, TriConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is false (3 param)")
    void testIfFalseThenConsume3Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifFalseThenConsume(false, INITIAL_VALUE, INITIAL_VALUE, 
                INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenConsume(boolean, Object, Object, Object, Object, QuadConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is false (4 param)")
    void testIfFalseThenConsume4Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifFalseThenConsume(false, INITIAL_VALUE, INITIAL_VALUE, 
                INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }

    /**
     * Tests {@link FunctionalUtils#ifFalseThenConsume(boolean, Object, Object, Object, Object, Object, QuinConsumer)} method.
     */
    @Test
    @DisplayName("Test consuming a function if condition is false (5 param)")
    void testIfFalseThenConsume5Param() {
        assertDoesNotThrow(() -> FunctionalUtils.ifFalseThenConsume(false, INITIAL_VALUE, INITIAL_VALUE, 
                INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, StringUtils::concatenate));
    }
}
