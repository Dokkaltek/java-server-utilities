package io.github.dokkaltek.helper;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Subclass of {@link ArrayList} that brings some convenience methods to the class to operate on the first and last
 * elements of the list apart from stream operations.
 * @param <T> The parametrized type of the list.
 */
@NoArgsConstructor
public class WrapperList<T> extends ArrayList<T> {

    /**
     * Capacity constructor.
     * @param initialCapacity The initial capacity to add to the list.
     */
    public WrapperList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that creates a list from an already existing one.
     * @param collection The collection to add to this list.
     */
    public WrapperList(Collection<? extends T> collection) {
        super(collection);
    }

    /**
     * Constructor that creates a list from an iterable.
     * @param iterable The iterable with the elements to add to this list.
     */
    public WrapperList(Iterable<? extends T> iterable) {
        super(StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()));
    }

    /**
     * Constructor that creates a list from a series of elements.
     * @param elements The elements to add to this list.
     */
    @SafeVarargs
    public WrapperList(T... elements) {
        super(Arrays.asList(elements));
    }

    /**
     * Gets the first element of the list.
     * @return The first element of the list if it has one, otherwise throws an exception.
     * @throws IndexOutOfBoundsException If the list is empty.
     */
    public T first() {
        return get(0);
    }

    /**
     * Gets the first element of the list.
     * @return The first element of the list if it has one, otherwise null.
     */
    public T firstOrNull() {
        int size = size();
        if (size == 0)
            return null;
        return get(0);
    }

    /**
     * Gets the last element of the list.
     * @return The last element of the list if it has one, otherwise throws an exception.
     * @throws IndexOutOfBoundsException if the list is empty.
     */
    public T last() {
        return get(size() - 1);
    }

    /**
     * Gets the last element of the list.
     * @return The last element of the list if it has one, otherwise null.
     */
    public T lastOrNull() {
        int size = size();
        if (size == 0)
            return null;

        return get(size - 1);
    }

    /**
     * Gets the index of the last element of the list.
     * Same as <code>list.size() - 1</code>.
     * @return The last element of the list if there was any, otherwise it will return -1.
     */
    public int lastIndex() {
        return size() - 1;
    }

    /**
     * Adds the element at the start of the array.
     */
    public void addToHead(T element) {
        add(0, element);
    }

    /**
     * Removes the first element of the list.
     * @return The removed element if it exists, otherwise null.
     */
    public T removeFirst() {
        int size = size();
        if (size == 0) {
            return null;
        }
        return remove(0);
    }

    /**
     * Removes the last element of the list.
     * @return The removed element if it exists, otherwise null.
     */
    public T removeLast() {
        int size = size();
        if (size == 0) {
            return null;
        }
        return remove(size() - 1);
    }

    /**
     * Keeps the elements that fulfill a predicate. The list is modified regardless, so you don't need to store the
     * result of the method and can chain it.
     * @param predicate The condition to keep the elements.
     * @return The list with the removed elements.
     */
    public WrapperList<T> filter(Predicate<? super T> predicate) {
        removeIf(t -> !predicate.test(t));
        return this;
    }

    /**
     * Finds the first result that matches a condition.
     * @param predicate The condition to match the elements.
     * @return The first result that matches the predicate.
     */
    public Optional<T> findFirstMatch(Predicate<? super T> predicate) {
        for (T element : this) {
            if (predicate.test(element)) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    /**
     * Maps each element of the {@link WrapperList} to another type using the specified function in an interator.
     * @param mapper The mapping operation to perform.
     * @return The result of the map operation.
     */
    public <R> WrapperList<R> map(Function<? super T, ? extends R> mapper) {
        Iterator<T> iterator = iterator();
        WrapperList<R> newList = new WrapperList<>(size());
        while (iterator.hasNext()) {
            newList.add(mapper.apply(iterator.next()));
        }
        return newList;
    }

    /**
     * Maps each element of the {@link WrapperList} to another type using the specified function using streams API
     * and returns the resulting {@link Stream}.
     * @param mapper The mapping operation to perform.
     * @return The result of the map operation.
     */
    public <R> Stream<R> mapToStream(Function<? super T, ? extends R> mapper) {
        return this.stream().map(mapper);
    }

    /**
     * Reduces the elements of the {@link WrapperList} to a single object using streams API.
     * @param accumulator The reducer operation to perform.
     * @return The result of the map operation.
     */
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return this.stream().reduce(accumulator);
    }

    /**
     * Creates a {@link WrapperList} from an array of elements with an initial capacity of the number of elements.
     * @param elements The elements to add to the {@link WrapperList}.
     * @return The {@link WrapperList} with the elements.
     * @throws NullPointerException If the elements array is null.
     */
    @SafeVarargs
    public static <T> WrapperList<T> of(T... elements) {
        WrapperList<T> list = new WrapperList<>(elements.length);
        list.addAll(Arrays.asList(elements));
        return list;
    }

    /**
     * Creates a {@link WrapperList} from a collection with an initial capacity of the number of elements in it.
     * @param collection The collection that we will add to the {@link WrapperList}.
     * @return The {@link WrapperList} with the elements of the collection.
     * @throws NullPointerException If the input collection is null.
     */
    public static <T> WrapperList<T> from(Collection<? extends T> collection) {
        WrapperList<T> list = new WrapperList<>(collection.size());
        list.addAll(collection);
        return list;
    }
}
