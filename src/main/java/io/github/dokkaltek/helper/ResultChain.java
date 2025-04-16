package io.github.dokkaltek.helper;

import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Helper class for chaining functions.
 */
@Data
public class ResultChain {
    private WrapperList<Object> results;

    /**
     * Default constructor.
     */
    public ResultChain() {
        this.results = new WrapperList<>();
    }

    /**
     * Constructor with first argument and first result.
     * @param firstResult The first result.
     */
    public ResultChain(Object firstResult) {
        this.results = new WrapperList<>(firstResult);
    }

    /**
     * Runs a function with the results stored so far.
     * @param function The function to run with the results list.
     * @return The current ResultChain.
     */
    public <T> ResultChain withResultsRun(Function<? super List<T>, Void> function) {
        List<T> resultsCopy = new WrapperList<>((List<T>) this.results);
        function.apply(resultsCopy);
        return this;
    }

    /**
     * Performs the operation using the results stored as input and store the new result.
     * @param function The function to run.
     * @return The current ResultChain.
     */
    public <T, R> ResultChain withResultsSupply(Function<? super List<T>, R> function) {
        List<T> resultsCopy = new WrapperList<>((List<T>) this.results);
        results.add(function.apply(resultsCopy));
        return this;
    }
    
    /**
     * Performs the given operation and stores the result.
     * @param function The operation to perform.
     * @return The current ResultChain.
     */
    public <T> ResultChain thenSupply(Supplier<T> function) {
        results.add(function.get());
        return this;
    }

    /**
     * Performs the given operation and stores the result.
     * @param function The operation to perform.
     * @return The current ResultChain.
     */
    public ResultChain thenRun(Runnable function) {
        function.run();
        return this;
    }

    /**
     * Returns a stream of the stored results.
     * @return A stream of the stored results.
     * @param <T> The parametrized type of the stream.
     */
    public <T> Stream<T> stream() {
        return results.stream().map(item -> (T) item);
    }

    /**
     * Maps each result using the given function and updates the result list.
     * @param mapper The mapping operation to perform.
     * @return The current ResultChain.
     */
    public <T, R> ResultChain map(Function<? super T, ? extends R> mapper) {
        for (int i = 0; i < this.results.size(); i++) {
            results.set(i, mapper.apply((T) results.get(i)));
        }
        return this;
    }

    /**
     * Maps the last result using the given function and updates the result.
     * @param mapper The mapping operation to perform.
     * @return The current ResultChain.
     */
    public <T, R> ResultChain mapLast(Function<? super T, ? extends R> mapper) {
        if (results.isEmpty())
            return this;
        results.set(results.lastIndex(), mapper.apply((T) results.last()));
        return this;
    }

    /**
     * Maps the last result using the given function and updates the result.
     * @param mapper The mapping operation to perform.
     * @return The current ResultChain.
     */
    public <T, R> ResultChain mapFirst(Function<? super T, ? extends R> mapper) {
        if (results.isEmpty())
            return this;
        results.set(0, mapper.apply((T) results.first()));
        return this;
    }

    /**
     * Maps each result using the given function and updates the result list.
     * @param index The index of the entry to map.
     * @param mapper The mapping operation to perform.
     * @return The current ResultChain.
     */
    public <T, R> ResultChain mapResultAtIndex(int index, Function<? super T, ? extends R> mapper) {
        results.set(index, mapper.apply((T) results.get(index)));
        return this;
    }

    /**
     * Performs an operation for each element of the list.
     * @param function The function to perform.
     * @return The current ResultChain.
     */
    public <T> ResultChain forEach(Function<? super T, Void> function) {
        for (Object result : this.results) {
            function.apply((T) result);
        }
        return this;
    }

    /**
     * Runs some code using the last result.
     * @param function The function to perform with the last result.
     * @return The current ResultChain.
     */
    public <T> ResultChain withLastRun(Function<? super T, Void> function) {
        if (results.isEmpty())
            return this;
        function.apply((T) results.last());
        return this;
    }

    /**
     * Performs an operation with the result at the given index.
     * @param index The index of the result to use.
     * @param function The operation to perform using the result at the given index as input.
     * @return The current ResultChain.
     */
    public <T> ResultChain withResultAtIndexRun(int index, Function<? super T, Void> function) {
        function.apply((T) results.get(index));
        return this;
    }

    /**
     * Performs an operation with the last result, and adds the result of the operation to the list.
     * @param function The operation to perform using the last entry as input.
     * @return The current ResultChain.
     */
    public <T, R> ResultChain withLastSupply(Function<? super T, ? extends R> function) {
        if (results.isEmpty())
            return this;
        results.add(function.apply((T) results.last()));
        return this;
    }

    /**
     * Performs an operation with the result at the given index, and adds the result of the operation to the list.
     * @param index The index of the result to use.
     * @param function The operation to perform using the result at the given index as input.
     * @return The current ResultChain.
     * @param <T> The parametrized type of the result.
     */
    public <T, R> ResultChain withResultAtIndexSupply(int index, Function<? super T, ? extends R> function) {
        results.add(function.apply((T) results.get(index)));
        return this;
    }

    /**
     * Gets the first result stored in the ResultChain.
     * @return The first result.
     * @throws IndexOutOfBoundsException If the list is empty.
     */
    public <T> T firstResult() {
        return (T) results.first();
    }

    /**
     * Gets the first result.
     * @return The first result if it exists, or null otherwise.
     */
    public <T> T firstOrNull() {
        return (T) results.firstOrNull();
    }

    /**
     * Gets the last result.
     * @return The last result.
     * @throws IndexOutOfBoundsException If the list is empty.
     */
    public <T> T lastResult() {
        return (T) results.last();
    }

    /**
     * Gets the last result.
     * @return The last result if it exists, or null otherwise.
     */
    public <T> T lastOrNull() {
        return (T) results.lastOrNull();
    }

    /**
     * Counts the results stored in the ResultChain.
     * @return The count of the results stored.
     */
    public int resultSize() {
        return results.size();
    }

    /**
     * Gets all the stored results as a {@link WrapperList}.
     * @return All the stored results.
     */
    public <T> WrapperList<T> asList() {
        return (WrapperList<T>) results;
    }

    /**
     * Gets all the stored results as a {@link HashSet}.
     * @return All the stored results.
     */
    public <T> Set<T> asSet() {
        return new HashSet<>((Collection<? extends T>) results);
    }

}
