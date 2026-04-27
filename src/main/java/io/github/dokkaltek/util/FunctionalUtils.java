package io.github.dokkaltek.util;

import io.github.dokkaltek.exception.GenericException;
import io.github.dokkaltek.helper.ResultChain;
import io.github.dokkaltek.interfaces.QuadConsumer;
import io.github.dokkaltek.interfaces.QuadFunction;
import io.github.dokkaltek.interfaces.QuinConsumer;
import io.github.dokkaltek.interfaces.QuinFunction;
import io.github.dokkaltek.interfaces.TriConsumer;
import io.github.dokkaltek.interfaces.TriFunction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.dokkaltek.util.StringUtils.isBlankOrNull;

/**
 * Utility class for functional programming related operations.
 * The methods here are aimed to make some conditionals more functional and help avoid some complexity in code.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FunctionalUtils {

    /**
     * If the passed object is null, it returns the passed value, otherwise it returns the passed object.
     * @param object The object to check for null.
     * @param defaultValue The value to return if the object is null.
     * @return The object if it is not null, otherwise the value.
     * @param <T> The type of the object.
     */
    public static <T> T ifNullThenReturn(T object, T defaultValue) {
        if (object == null)
            return defaultValue;
        return object;
    }

    /**
     * If the passed object is null, it throws the passed exception (for example, a {@link GenericException}).
     * @param object The object to check for null.
     * @param ex The exception to throw if the object is null.
     */
    public static void ifNullThenThrow(Object object, RuntimeException ex) {
        if (object == null)
            throw ex;
    }

    /**
     * If the passed object is null, it runs the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is null.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifNullThenRun(Object object, Runnable function) {
        if (object == null)
            function.run();

        return new ResultChain();
    }

    /**
     * If the passed object is not null, it runs the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is not null.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifNotNullThenRun(Object object, Runnable function) {
        if (object != null)
            function.run();
        return new ResultChain();
    }

    /**
     * If the passed object is null, it supplies the value returned by the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is null.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, or the object otherwise.
     */
    public static <T> ResultChain ifNullThenSupplyChain(T object, Supplier<T> function) {
        if (object == null)
            return new ResultChain(function.get());
        return new ResultChain(object);
    }

    /**
     * If the passed object is null, it supplies the value returned by the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is null.
     * @return The result of the function if it was executed, or the object otherwise.
     */
    public static <T> T ifNullThenSupply(T object, Supplier<T> function) {
        if (object == null)
            return function.get();
        return object;
    }

    /**
     * If the passed object is not null, it supplies the value returned by the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is not null.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, or null otherwise.
     */
    public static <T> ResultChain ifNotNullThenSupplyChain(Object object, Supplier<T> function) {
        if (object != null)
            return new ResultChain(function.get());
        return new ResultChain(null);
    }

    /**
     * If the passed object is not null, it supplies the value returned by the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is not null.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T> T ifNotNullThenSupply(Object object, Supplier<T> function) {
        if (object != null)
            return function.get();
        return null;
    }

    /**
     * If the passed object is not null, it applies a function to the passed value and
     * returns the result of the function.
     * @param object The object to check for null.
     * @param function The function to apply to the object if it is not null.
     * @return The result of the function if it was executed, otherwise null.
     */
    public static <T, R> R ifNotNullThenApply(T object, Function<? super T, R> function) {
        if (object != null)
            return function.apply(object);
        return null;
    }

    /**
     * If the passed objects are not null, it applies a function to the passed value and
     * returns the result of the function.
     * @param object The object to check for null.
     * @param object2 The second object to check for null.
     * @param function The function to apply to the object if it is not null.
     * @return The result of the function if it was executed, otherwise null.
     */
    public static <T,R,S> S ifNotNullThenApply(T object, R object2, BiFunction<? super T, ? super R, S> function) {
        if (object != null && object2 != null)
            return function.apply(object, object2);
        return null;
    }

    /**
     * If the passed objects are not null, it applies a function to the passed value and
     * returns the result of the function.
     * @param object The object to check for null.
     * @param object2 The second object to check for null.
     * @param object3 The third object to check for null.
     * @param function The function to apply to the object if it is not null.
     * @return The result of the function if it was executed, otherwise null.
     */
    public static <T,R,Y,S> S ifNotNullThenApply(T object, R object2, Y object3,
                                                 TriFunction<? super T, ? super R, ? super Y, S> function) {
        if (object != null && object2 != null && object3 != null)
            return function.apply(object, object2, object3);
        return null;
    }

    /**
     * If the passed objects are not null, it applies a function to the passed value and
     * returns the result of the function.
     * @param object The object to check for null.
     * @param object2 The second object to check for null.
     * @param object3 The third object to check for null.
     * @param function The function to apply to the object if it is not null.
     * @return The result of the function if it was executed, otherwise null.
     */
    public static <T,R,Y,W,S> S ifNotNullThenApply(T object, R object2, Y object3, W object4,
                                                 QuadFunction<? super T, ? super R, ? super Y,? super W, S> function) {
        if (object != null && object2 != null && object3 != null && object4 != null)
            return function.apply(object, object2, object3, object4);
        return null;
    }

    /**
     * If the passed objects are not null, it applies a function to the passed value and
     * returns the result of the function.
     * @param object The object to check for null.
     * @param object2 The second object to check for null.
     * @param object3 The third object to check for null.
     * @param function The function to apply to the object if it is not null.
     * @return The result of the function if it was executed, otherwise null.
     */
    public static <T,R,Y,W,Z,S> S ifNotNullThenApply(T object, R object2, Y object3, W object4, Z object5,
                                  QuinFunction<? super T, ? super R, ? super Y, ? super W, ? super Z, S> function) {
        if (object != null && object2 != null && object3 != null && object4 != null && object5 != null)
            return function.apply(object, object2, object3, object4, object5);
        return null;
    }

    /**
     * If the passed object is not null, it applies the value to the given consumer.
     * @param object The object to check for null.
     * @param consumer The function to apply to the object if it is not null.
     */
    public static <T> void ifNotNullThenConsume(T object, Consumer<? super T> consumer) {
        if (object != null)
            consumer.accept(object);
    }

    /**
     * If the passed object is not null, it applies the value to the given consumer.
     * @param object The object to check for null.
     * @param object2 The second object to check for null.
     * @param consumer The function to apply to the object if it is not null.
     */
    public static <T,R> void ifNotNullThenConsume(T object, R object2, BiConsumer<? super T, ? super R> consumer) {
        if (object != null && object2 != null)
            consumer.accept(object, object2);
    }

    /**
     * If the passed object is not null, it applies the value to the given consumer.
     * @param object The object to check for null.
     * @param object2 The second object to check for null.
     * @param object3 The third object to check for null.
     * @param consumer The function to apply to the object if it is not null.
     */
    public static <T,R,Y> void ifNotNullThenConsume(T object, R object2, Y object3,
                                                 TriConsumer<? super T, ? super R, ? super Y> consumer) {
        if (object != null && object2 != null && object3 != null)
            consumer.accept(object, object2, object3);
    }

    /**
     * If the passed object is not null, it applies the value to the given consumer.
     * @param object The object to check for null.
     * @param object2 The second object to check for null.
     * @param object3 The third object to check for null.
     * @param object4 The fourth object to check for null.
     * @param consumer The function to apply to the object if it is not null.
     */
    public static <T,R,Y,W> void ifNotNullThenConsume(T object, R object2, Y object3, W object4,
                                                 QuadConsumer<? super T, ? super R, ? super Y,? super W> consumer) {
        if (object != null && object2 != null && object3 != null && object4 != null)
            consumer.accept(object, object2, object3, object4);
    }

    /**
     * If the passed object is not null, it applies the value to the given consumer.
     * @param object The object to check for null.
     * @param object2 The second object to check for null.
     * @param object3 The third object to check for null.
     * @param object4 The fourth object to check for null.
     * @param object5 The fifth object to check for null.
     * @param consumer The function to apply to the object if it is not null.
     */
    public static <T,R,Y,W,Z> void ifNotNullThenConsume(T object, R object2, Y object3, W object4, Z object5,
                                       QuinConsumer<? super T, ? super R, ? super Y, ? super W, ? super Z> consumer) {
        if (object != null && object2 != null && object3 != null && object4 != null && object5 != null)
            consumer.accept(object, object2, object3, object4, object5);
    }

    /**
     * If the passed string is null or blank, it returns the passed value, otherwise it returns the passed string.
     * @param string The string to check for null or blank.
     * @param defaultValue The value to return if the string is null or blank.
     * @return The string if it is not null or blank, otherwise the value.
     */
    public static String ifBlankOrNullThenReturn(String string, String defaultValue) {
        if (isBlankOrNull(string))
            return defaultValue;
        return string;
    }

    /**
     * If the passed string is null or blank, it throws the passed exception (for example, a {@link GenericException}).
     * @param string The string to check for null or blank.
     * @param ex The exception to throw if the string is null or blank.
     */
    public static void ifBlankOrNullThenThrow(String string, RuntimeException ex) {
        if (isBlankOrNull(string))
            throw ex;
    }

    /**
     * If the passed string is not blank or null, it runs the passed function.
     * @param string The string to check for blank or null.
     * @param function The function to run if the object is null or blank.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifBlankOrNullThenRun(String string, Runnable function) {
        if (isBlankOrNull(string))
            function.run();
        return new ResultChain();
    }

    /**
     * If the passed string is not blank or null, it supplies the value returned by the passed function.
     * @param string The string to check for blank or null.
     * @param function The function to run if the object is null or blank.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed.
     */
    public static <T> ResultChain ifBlankOrNullThenSupplyChain(String string, Supplier<T> function) {
        if (isBlankOrNull(string))
            return new ResultChain(function.get());
        return new ResultChain(null);
    }

    /**
     * If the passed string is not blank or null, it supplies the value returned by the passed function.
     * @param string The string to check for blank or null.
     * @param function The function to run if the object is null or blank.
     * @return The result of the function if it was executed.
     */
    public static <T> T ifBlankOrNullThenSupply(String string, Supplier<T> function) {
        if (isBlankOrNull(string))
            return function.get();
        return null;
    }

    /**
     * If the passed string is not blank or null, it applies a function to the passed value and
     * returns the result of the function.
     * @param string The string to check for blank or null.
     * @param function The function to apply to the string if it is not blank or null.
     * @return The result of the function if it was executed, otherwise null.
     */
    public static <R> R ifNotBlankOrNullThenApply(String string, Function<? super String, R> function) {
        if (!isBlankOrNull(string))
            return function.apply(string);
        return null;
    }

    /**
     * If the passed string is not blank or null, it applies a function to the passed value and
     * returns the result of the function.
     * @param string The string to check for blank or null.
     * @param function The function to apply to the string if it is not blank or null.
     */
    public static ResultChain ifNotBlankOrNullThenRun(String string, Runnable function) {
        if (!isBlankOrNull(string))
            function.run();
        return new ResultChain();
    }

    /**
     * If the passed collection is null or empty, it returns the passed value, otherwise it returns the passed string.
     * @param collection The collection to check for null or empty.
     * @param defaultValue The value to return if the collection is null or empty.
     * @return The collection if it is not null or empty, otherwise the default value.
     */
    public static <T> Collection<T> ifEmptyOrNullThenReturn(Collection<T> collection, Collection<T> defaultValue) {
        if (collection == null || collection.isEmpty())
            return defaultValue;
        return collection;
    }

    /**
     * If the passed collection is null or empty, it throws the passed exception (for example, a
     * {@link GenericException}).
     * @param collection The collection to check for null or empty.
     * @param ex The exception to throw if the collection is null or empty.
     */
    public static <T> void ifEmptyOrNullThenThrow(Collection<T> collection, RuntimeException ex) {
        if (collection == null || collection.isEmpty())
            throw ex;
    }

    /**
     * If the passed collection is not empty or null, it runs the passed function.
     * @param collection The collection to check for empty or null.
     * @param function The function to run if the object is null or empty.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static <T> ResultChain ifEmptyOrNullThenRun(Collection<T> collection, Runnable function) {
        if (collection == null || collection.isEmpty())
            function.run();
        return new ResultChain();
    }

    /**
     * If the passed collection is not empty or null, it supplies the value returned by the passed function,
     * otherwise it returns the collection.
     * @param collection The collection to check for empty or null.
     * @param function The function to run if the object is null or empty.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, otherwise it returns an empty collection.
     */
    public static <T> ResultChain ifEmptyOrNullThenSupplyChain(Collection<T> collection, Supplier<?> function) {
        if (collection == null || collection.isEmpty())
            return new ResultChain(function.get());
        return new ResultChain(null);
    }

    /**
     * If the passed collection is not empty or null, it supplies the value returned by the passed function,
     * otherwise it returns the collection.
     * @param collection The collection to check for empty or null.
     * @param function The function to run if the object is null or empty.
     * @return The result of the function if it was executed, otherwise it returns an empty collection.
     */
    public static <T, Y> Y ifEmptyOrNullThenSupply(Collection<T> collection, Supplier<Y> function) {
        if (collection == null || collection.isEmpty())
            return function.get();
        return null;
    }

    /**
     * If the passed collection is not null or empty, it applies a function to the passed value and
     * returns the result of the function.
     * @param collection The collection to check for empty or null.
     * @param function The function to apply to the collection if it is not empty or null.
     * @return The result of the function if it was executed, otherwise null.
     */
    public static <T, R> R ifNotEmptyOrNullThenApply(Collection<T> collection,
                                                     Function<? super Collection<T>, R> function) {
        if (collection != null && !collection.isEmpty())
            return function.apply(collection);
        return null;
    }

    /**
     * Returns the value if the condition is true, otherwise null.
     * @param condition The condition to check.
     * @param value The value to return if true.
     * @return The value if the condition is true, otherwise null.
     * @param <T> The type of the value.
     */
    public static <T> T ifTrueThenReturn(boolean condition, T value) {
        if (condition)
            return value;
        return null;
    }

    /**
     * Returns the value if the condition is true, otherwise the default value.
     * @param condition The condition to check.
     * @param value The value to return if true.
     * @param defaultValue The value to return if false.
     * @return The value if the condition is true, otherwise the default value.
     * @param <T> The type of the value.
     */
    public static <T> T ifTrueThenReturn(boolean condition, T value, T defaultValue) {
        if (condition)
            return value;
        return defaultValue;
    }

    /**
     * Returns the value if the condition is false, otherwise null.
     * @param condition The condition to check.
     * @param value The value to return if false.
     * @return The value if the condition is false, otherwise null.
     * @param <T> The type of the value.
     */
    public static <T> T ifFalseThenReturn(boolean condition, T value) {
        if (!condition)
            return value;
        return null;
    }

    /**
     * Returns the value if the condition is false, otherwise the default value.
     * @param condition The condition to check.
     * @param value The value to return if false.
     * @param defaultValue The value to return if true.
     * @return The value if the condition is false, otherwise the default value.
     * @param <T> The type of the value.
     */
    public static <T> T ifFalseThenReturn(boolean condition, T value, T defaultValue) {
        if (!condition)
            return value;
        return defaultValue;
    }

    /**
     * Throws a {@link RuntimeException} if the condition is true (for example, a {@link GenericException}).
     * @param condition The condition to check.
     * @param ex The exception to throw if the condition is true.
     */
    public static void ifTrueThenThrow(boolean condition, RuntimeException ex) {
        if (condition)
            throw ex;
    }

    /**
     * Throws a {@link RuntimeException} if the condition is false (for example, a {@link GenericException}).
     * @param condition The condition to check.
     * @param ex The exception to throw if the condition is false.
     */
    public static void ifFalseThenThrow(boolean condition, RuntimeException ex) {
        if (!condition)
            throw ex;
    }

    /**
     * If the passed condition is true, it runs the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is true.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifTrueThenRun(boolean condition, Runnable function) {
        if (condition)
            function.run();
        return new ResultChain();
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run for the value if the condition is true.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, or null otherwise.
     */
    public static <T> ResultChain ifTrueThenSupplyChain(boolean condition, Supplier<T> function) {
        if (condition)
            return new ResultChain(function.get());
        return new ResultChain(null);
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run for the value if the condition is true.
     * @param defaultValue The default value to return if the condition is false.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, or null otherwise.
     */
    public static <T> ResultChain ifTrueThenSupplyChain(boolean condition, Supplier<T> function, T defaultValue) {
        if (condition)
            return new ResultChain(function.get());
        return new ResultChain(defaultValue);
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T> T ifTrueThenSupply(boolean condition, Supplier<T> function) {
        if (condition)
            return function.get();
        return null;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is true.
     * @param defaultValue The default value to return if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T> T ifTrueThenSupply(boolean condition, Supplier<T> function, T defaultValue) {
        if (condition)
            return function.get();
        return defaultValue;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param function The function to run if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R> T ifTrueThenApply(boolean condition, R object, Function<? super R, T> function) {
        if (condition)
            return function.apply(object);
        return null;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param function The function to run if the condition is true.
     * @param defaultValue The default value to return if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R> T ifTrueThenApply(boolean condition, R object, Function<? super R, T> function,
                                           T defaultValue) {
        if (condition)
            return function.apply(object);
        return defaultValue;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param function The function to run if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S> T ifTrueThenApply(boolean condition, R object, S object2,
                                              BiFunction<? super R,? super S, T> function) {
        if (condition)
            return function.apply(object, object2);
        return null;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param function The function to run if the condition is true.
     * @param defaultValue The default value to return if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S> T ifTrueThenApply(boolean condition, R object, S object2,
                                              BiFunction<? super R,? super S, T> function,
                                              T defaultValue) {
        if (condition)
            return function.apply(object, object2);
        return defaultValue;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param function The function to run if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U> T ifTrueThenApply(boolean condition, R object, S object2, U object3,
                                              TriFunction<? super R,? super S, ? super U, T> function) {
        if (condition)
            return function.apply(object, object2, object3);
        return null;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param function The function to run if the condition is true.
     * @param defaultValue The default value to return if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U> T ifTrueThenApply(boolean condition, R object, S object2, U object3,
                                                 TriFunction<? super R,? super S, ? super U, T> function,
                                                 T defaultValue) {
        if (condition)
            return function.apply(object, object2, object3);
        return defaultValue;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param function The function to run if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U, V> T ifTrueThenApply(boolean condition, R object, S object2, U object3, V object4,
                                                 QuadFunction<? super R, ? super S, ? super U, ? super V, T> function) {
        if (condition)
            return function.apply(object, object2, object3, object4);
        return null;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param function The function to run if the condition is true.
     * @param defaultValue The default value to return if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U, V> T ifTrueThenApply(boolean condition, R object, S object2, U object3, V object4,
                                                 QuadFunction<? super R, ? super S, ? super U, ? super V, T> function,
                                                 T defaultValue) {
        if (condition)
            return function.apply(object, object2, object3, object4);
        return defaultValue;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param object5 The fifth object to pass to the function.
     * @param function The function to run if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U, V, W> T ifTrueThenApply(boolean condition, R object, S object2, U object3, V object4,
                                                    W object5, QuinFunction<? super R, ? super S, ? super U,
                                                                                  ? super V, ? super W, T> function) {
        if (condition)
            return function.apply(object, object2, object3, object4, object5);
        return null;
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param object5 The fifth object to pass to the function.
     * @param function The function to run if the condition is true.
     * @param defaultValue The default value to return if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U, V, W> T ifTrueThenApply(boolean condition, R object, S object2, U object3, V object4,
                                                       W object5, QuinFunction<? super R, ? super S, ? super U,
                                                       ? super V, ? super W, T> function, T defaultValue) {
        if (condition)
            return function.apply(object, object2, object3, object4, object5);
        return defaultValue;
    }

    /**
     * If the passed condition is true, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param consumer The consumer to run if the condition is true.
     */
    public static <T> void ifTrueThenConsume(boolean condition, T object, Consumer<? super T> consumer) {
        if (condition)
            consumer.accept(object);
    }

    /**
     * If the passed condition is true, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param consumer The consumer to run if the condition is true.
     */
    public static <T, R> void ifTrueThenConsume(boolean condition, T object, R object2,
                                                BiConsumer<? super T, ? super R> consumer) {
        if (condition)
            consumer.accept(object, object2);
    }

    /**
     * If the passed condition is true, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param consumer The consumer to run if the condition is true.
     */
    public static <T, R, S> void ifTrueThenConsume(boolean condition, T object, R object2, S object3,
                                                TriConsumer<? super T, ? super R, ? super S> consumer) {
        if (condition)
            consumer.accept(object, object2, object3);
    }

    /**
     * If the passed condition is true, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param consumer The consumer to run if the condition is true.
     */
    public static <T, R, S, U> void ifTrueThenConsume(boolean condition, T object, R object2, S object3, U object4,
                                                   QuadConsumer<? super T, ? super R, ? super S, ? super U> consumer) {
        if (condition)
            consumer.accept(object, object2, object3, object4);
    }

    /**
     * If the passed condition is true, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param object5 The fifth object to pass to the function.
     * @param consumer The consumer to run if the condition is true.
     */
    public static <T, R, S, U, V> void ifTrueThenConsume(boolean condition, T object, R object2, S object3, U object4,
                                                  V object5, QuinConsumer<? super T, ? super R, ? super S, ? super U,
                                                                                            ? super V> consumer) {
        if (condition)
            consumer.accept(object, object2, object3, object4, object5);
    }

    /**
     * If the passed condition is false, it runs the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is false.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifFalseThenRun(boolean condition, Runnable function) {
        if (!condition)
            function.run();
        return new ResultChain();
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is false.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, or null otherwise.
     */
    public static <T> ResultChain ifFalseThenSupplyChain(boolean condition, Supplier<T> function) {
        if (!condition)
            return new ResultChain(function.get());
        return new ResultChain(null);
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run for the value if the condition is false.
     * @param defaultValue The default value to return if the condition is true.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, or null otherwise.
     */
    public static <T> ResultChain ifFalseThenSupplyChain(boolean condition, Supplier<T> function, T defaultValue) {
        if (!condition)
            return new ResultChain(function.get());
        return new ResultChain(defaultValue);
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run for the value if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T> T ifFalseThenSupply(boolean condition, Supplier<T> function) {
        if (!condition)
            return function.get();
        return null;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run for the value if the condition is false.
     * @param defaultValue The default value to return if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T> T ifFalseThenSupply(boolean condition, Supplier<T> function, T defaultValue) {
        if (!condition)
            return function.get();
        return defaultValue;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param function The function to run if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R> T ifFalseThenApply(boolean condition, R object, Function<? super R, T> function) {
        if (!condition)
            return function.apply(object);
        return null;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param function The function to run if the condition is false.
     * @param defaultValue The default value to return if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R> T ifFalseThenApply(boolean condition, R object, Function<? super R, T> function,
                                            T defaultValue) {
        if (!condition)
            return function.apply(object);
        return defaultValue;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param function The function to run if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S> T ifFalseThenApply(boolean condition, R object, S object2,
                                              BiFunction<? super R,? super S, T> function) {
        if (!condition)
            return function.apply(object, object2);
        return null;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param function The function to run if the condition is false.
     * @param defaultValue The default value to return if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S> T ifFalseThenApply(boolean condition, R object, S object2,
                                               BiFunction<? super R,? super S, T> function, T defaultValue) {
        if (!condition)
            return function.apply(object, object2);
        return defaultValue;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param function The function to run if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U> T ifFalseThenApply(boolean condition, R object, S object2, U object3,
                                                 TriFunction<? super R,? super S, ? super U, T> function) {
        if (!condition)
            return function.apply(object, object2, object3);
        return null;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param function The function to run if the condition is false.
     * @param defaultValue The default value to return if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U> T ifFalseThenApply(boolean condition, R object, S object2, U object3,
                                                  TriFunction<? super R,? super S, ? super U, T> function,
                                                  T defaultValue) {
        if (!condition)
            return function.apply(object, object2, object3);
        return defaultValue;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param function The function to run if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U, V> T ifFalseThenApply(boolean condition, R object, S object2, U object3, V object4,
                                             QuadFunction<? super R, ? super S, ? super U, ? super V, T> function) {
        if (!condition)
            return function.apply(object, object2, object3, object4);
        return null;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param function The function to run if the condition is false.
     * @param defaultValue The default value to return if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U, V> T ifFalseThenApply(boolean condition, R object, S object2, U object3, V object4,
                                                QuadFunction<? super R, ? super S, ? super U, ? super V, T> function,
                                                T defaultValue) {
        if (!condition)
            return function.apply(object, object2, object3, object4);
        return defaultValue;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param object5 The fifth object to pass to the function.
     * @param function The function to run if the condition is false.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U, V, W> T ifFalseThenApply(boolean condition, R object, S object2, U object3, V object4,
                                                       W object5, QuinFunction<? super R, ? super S, ? super U,
                                                       ? super V, ? super W, T> function) {
        if (!condition)
            return function.apply(object, object2, object3, object4, object5);
        return null;
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param object5 The fifth object to pass to the function.
     * @param function The function to run if the condition is false.
     * @param defaultValue The default value to return if the condition is true.
     * @return The result of the function if it was executed, or null otherwise.
     */
    public static <T, R, S, U, V, W> T ifFalseThenApply(boolean condition, R object, S object2, U object3, V object4,
                                                        W object5, QuinFunction<? super R, ? super S, ? super U,
                                                        ? super V, ? super W, T> function, T defaultValue) {
        if (!condition)
            return function.apply(object, object2, object3, object4, object5);
        return defaultValue;
    }

    /**
     * If the passed condition is false, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param consumer The consumer to run if the condition is false.
     */
    public static <T> void ifFalseThenConsume(boolean condition, T object, Consumer<? super T> consumer) {
        if (!condition)
            consumer.accept(object);
    }

    /**
     * If the passed condition is false, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param consumer The consumer to run if the condition is false.
     */
    public static <T, R> void ifFalseThenConsume(boolean condition, T object, R object2,
                                                BiConsumer<? super T, ? super R> consumer) {
        if (!condition)
            consumer.accept(object, object2);
    }

    /**
     * If the passed condition is false, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param consumer The consumer to run if the condition is false.
     */
    public static <T, R, S> void ifFalseThenConsume(boolean condition, T object, R object2, S object3,
                                                   TriConsumer<? super T, ? super R, ? super S> consumer) {
        if (!condition)
            consumer.accept(object, object2, object3);
    }

    /**
     * If the passed condition is false, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param consumer The consumer to run if the condition is false.
     */
    public static <T, R, S, U> void ifFalseThenConsume(boolean condition, T object, R object2, S object3, U object4,
                                                  QuadConsumer<? super T, ? super R, ? super S, ? super U> consumer) {
        if (!condition)
            consumer.accept(object, object2, object3, object4);
    }

    /**
     * If the passed condition is false, it consumes the passed object with the given function.
     * @param condition The condition to check.
     * @param object The object to pass to the function.
     * @param object2 The second object to pass to the function.
     * @param object3 The third object to pass to the function.
     * @param object4 The fourth object to pass to the function.
     * @param object5 The fifth object to pass to the function.
     * @param consumer The consumer to run if the condition is false.
     */
    public static <T, R, S, U, V> void ifFalseThenConsume(boolean condition, T object, R object2, S object3, U object4,
                                                         V object5, QuinConsumer<? super T, ? super R, ? super S,
                                                                                ? super U, ? super V> consumer) {
        if (!condition)
            consumer.accept(object, object2, object3, object4, object5);
    }
}
