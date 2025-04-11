package com.github.dokkaltek.util;

import com.github.dokkaltek.exception.GenericException;
import com.github.dokkaltek.helper.ResultChain;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.function.Supplier;

import static com.github.dokkaltek.util.StringUtils.isBlankOrNull;

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
        if (object == null) {
            return defaultValue;
        }
        return object;
    }

    /**
     * If the passed object is null, it throws the passed exception (for example, a {@link GenericException}).
     * @param object The object to check for null.
     * @param ex The exception to throw if the object is null.
     */
    public static void ifNullThenThrow(Object object, RuntimeException ex) {
        if (object == null) {
            throw ex;
        }
    }

    /**
     * If the passed object is null, it runs the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is null.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifNullThenRun(Object object, Runnable function) {
        if (object == null) {
            function.run();
        }
        return new ResultChain();
    }

    /**
     * If the passed object is not null, it runs the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is not null.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifNotNullThenRun(Object object, Runnable function) {
        if (object != null) {
            function.run();
        }
        return new ResultChain();
    }

    /**
     * If the passed object is null, it supplies the value returned by the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is null.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, otherwise it returns the input object.
     */
    public static <T> ResultChain ifNullThenSupply(Object object, Supplier<T> function) {
        if (object == null) {
            return new ResultChain(function.get());
        }
        return new ResultChain();
    }

    /**
     * If the passed object is not null, it supplies the value returned by the passed function.
     * @param object The object to check for null.
     * @param function The function to run if the object is not null.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed.
     */
    public static <T> ResultChain ifNotNullThenSupply(Object object, Supplier<T> function) {
        if (object != null) {
            return new ResultChain(function.get());
        }
        return new ResultChain();
    }

    /**
     * If the passed string is null or blank, it returns the passed value, otherwise it returns the passed string.
     * @param string The string to check for null or blank.
     * @param defaultValue The value to return if the string is null or blank.
     * @return The string if it is not null or blank, otherwise the value.
     */
    public static String ifBlankOrNullThenReturn(String string, String defaultValue) {
        if (isBlankOrNull(string)) {
            return defaultValue;
        }
        return string;
    }

    /**
     * If the passed string is null or blank, it throws the passed exception (for example, a {@link GenericException}).
     * @param string The string to check for null or blank.
     * @param ex The exception to throw if the string is null or blank.
     */
    public static void ifBlankOrNullThenThrow(String string, RuntimeException ex) {
        if (isBlankOrNull(string)) {
            throw ex;
        }
    }

    /**
     * If the passed string is not blank or null, it runs the passed function.
     * @param string The string to check for blank or null.
     * @param function The function to run if the object is null or blank.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifBlankOrNullThenRun(String string, Runnable function) {
        if (isBlankOrNull(string)) {
            function.run();
        }
        return new ResultChain();
    }

    /**
     * If the passed string is not blank or null, it supplies the value returned by the passed function.
     * @param string The string to check for blank or null.
     * @param function The function to run if the object is null or blank.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed.
     */
    public static <T> ResultChain ifBlankOrNullThenSupply(String string, Supplier<T> function) {
        if (isBlankOrNull(string)) {
            return new ResultChain(function.get());
        }
        return new ResultChain();
    }

    /**
     * If the passed collection is null or empty, it returns the passed value, otherwise it returns the passed string.
     * @param collection The collection to check for null or empty.
     * @param defaultValue The value to return if the collection is null or empty.
     * @return The collection if it is not null or empty, otherwise the default value.
     */
    public static <T> Collection<T> ifEmptyOrNullThenReturn(Collection<T> collection, Collection<T> defaultValue) {
        if (collection == null || collection.isEmpty()) {
            return defaultValue;
        }

        return collection;
    }

    /**
     * If the passed collection is null or empty, it throws the passed exception (for example, a
     * {@link GenericException}).
     * @param collection The collection to check for null or empty.
     * @param ex The exception to throw if the collection is null or empty.
     */
    public static <T> void ifEmptyOrNullThenThrow(Collection<T> collection, RuntimeException ex) {
        if (collection == null || collection.isEmpty()) {
            throw ex;
        }
    }

    /**
     * If the passed collection is not empty or null, it runs the passed function.
     * @param collection The collection to check for empty or null.
     * @param function The function to run if the object is null or empty.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static <T> ResultChain ifEmptyOrNullThenRun(Collection<T> collection, Runnable function) {
        if (collection == null || collection.isEmpty()) {
            function.run();
        }
        return new ResultChain();
    }

    /**
     * If the passed collection is not empty or null, it supplies the value returned by the passed function,
     * otherwise it returns the collection.
     * @param collection The collection to check for empty or null.
     * @param function The function to run if the object is null or empty.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed, otherwise it returns the input collection.
     */
    public static <T> ResultChain ifEmptyOrNullThenSupply(Collection<T> collection, Supplier<T> function) {
        if (collection == null || collection.isEmpty()) {
            return new ResultChain(function.get());
        }
        return new ResultChain();
    }

    /**
     * Returns the value if the condition is true, otherwise null.
     * @param condition The condition to check.
     * @param value The value to return if true.
     * @return The value if the condition is true, otherwise null.
     * @param <T> The type of the value.
     */
    public static <T> T ifTrueThenReturn(boolean condition, T value) {
        if (condition) {
            return value;
        }
        return null;
    }

    /**
     * Returns the value if the condition is false, otherwise null.
     * @param condition The condition to check.
     * @param value The value to return if false.
     * @return The value if the condition is false, otherwise null.
     * @param <T> The type of the value.
     */
    public static <T> T ifFalseThenReturn(boolean condition, T value) {
        if (!condition) {
            return value;
        }
        return null;
    }

    /**
     * Throws a {@link RuntimeException} if the condition is true (for example, a {@link GenericException}).
     * @param condition The condition to check.
     * @param ex The exception to throw if the condition is true.
     */
    public static void ifTrueThenThrow(boolean condition, RuntimeException ex) {
        if (condition) {
            throw ex;
        }
    }

    /**
     * Throws a {@link RuntimeException} if the condition is false (for example, a {@link GenericException}).
     * @param condition The condition to check.
     * @param ex The exception to throw if the condition is false.
     */
    public static void ifFalseThenThrow(boolean condition, RuntimeException ex) {
        if (!condition) {
            throw ex;
        }
    }

    /**
     * If the passed condition is true, it runs the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is true.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifTrueThenRun(boolean condition, Runnable function) {
        if (condition) {
            function.run();
        }
        return new ResultChain();
    }

    /**
     * If the passed condition is true, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is true.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed.
     */
    public static <T> ResultChain ifTrueThenSupply(boolean condition, Supplier<T> function) {
        if (condition) {
            return new ResultChain(function.get());
        }
        return new ResultChain();
    }

    /**
     * If the passed condition is false, it runs the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is false.
     * @return A {@link ResultChain} that allows chaining more methods.
     */
    public static ResultChain ifFalseThenRun(boolean condition, Runnable function) {
        if (!condition) {
            function.run();
        }
        return new ResultChain();
    }

    /**
     * If the passed condition is false, it supplies the value returned by the passed function.
     * @param condition The condition to check.
     * @param function The function to run if the condition is false.
     * @return A {@link ResultChain} that allows chaining more methods, and holds the result of the function if
     * it was executed.
     */
    public static <T> ResultChain ifFalseThenSupply(boolean condition, Supplier<T> function) {
        if (!condition) {
            return new ResultChain(function.get());
        }
        return new ResultChain();
    }
}
