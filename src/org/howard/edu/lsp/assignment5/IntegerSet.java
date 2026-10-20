package org.howard.edu.lsp.assignment5;

import java.util.Objects;
import java.util.TreeSet;

/**
 * A mathematical set of integers. Duplicate values are never stored.
 *
 * <p>Internally the elements are kept in a {@link TreeSet}, which rejects
 * duplicates automatically and keeps the elements in ascending order. That
 * makes {@link #toString()}, {@link #largest()}, and {@link #smallest()}
 * straightforward. The internal collection is private and is never exposed,
 * so callers can only change a set through its public methods.</p>
 *
 * <p>Every method that returns an {@code IntegerSet} builds a brand-new
 * object and leaves both operands unchanged.</p>
 */
public class IntegerSet {

    /** Backing storage; sorted and duplicate-free. Never exposed to callers. */
    private final TreeSet<Integer> elements = new TreeSet<>();

    /**
     * Creates a new, empty set.
     */
    public IntegerSet() {
    }

    /**
     * Removes every element from this set, leaving it empty.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Returns the number of elements in this set.
     *
     * @return the count of distinct elements
     */
    public int length() {
        return elements.size();
    }

    /**
     * Compares this set with another set. Two sets are equal when they
     * contain exactly the same elements, regardless of insertion order.
     *
     * @param b the set to compare with
     * @return {@code true} if both sets hold the same elements;
     *         {@code false} otherwise, including when {@code b} is null
     */
    public boolean equals(IntegerSet b) {
        if (b == null) {
            return false;
        }
        return this == b || elements.equals(b.elements);
    }

    /**
     * Compares this set with an arbitrary object. Returns {@code true} only
     * if the object is an {@code IntegerSet} with the same elements. This
     * keeps equality consistent when the argument's static type is
     * {@code Object}.
     *
     * @param obj the object to compare with
     * @return {@code true} if {@code obj} is an equal {@code IntegerSet}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerSet && equals((IntegerSet) obj);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return a hash code based on the elements of this set
     */
    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    /**
     * Tests whether a value is in this set.
     *
     * @param value the value to look for
     * @return {@code true} if the value is present
     */
    public boolean contains(int value) {
        return elements.contains(value);
    }

    /**
     * Returns the largest element in this set.
     *
     * @return the maximum value
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Cannot find largest of an empty set");
        }
        return elements.last();
    }

    /**
     * Returns the smallest element in this set.
     *
     * @return the minimum value
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Cannot find smallest of an empty set");
        }
        return elements.first();
    }

    /**
     * Adds a value to this set. If the value is already present, nothing
     * happens.
     *
     * @param item the value to add
     */
    public void add(int item) {
        elements.add(item);
    }

    /**
     * Removes a value from this set. If the value is absent, nothing
     * happens.
     *
     * @param item the value to remove
     */
    public void remove(int item) {
        elements.remove(item);
    }

    /**
     * Returns a new set containing the elements found in either set.
     *
     * @param b the other set; must not be null
     * @return a new set holding the union of this set and {@code b}
     * @throws NullPointerException if {@code b} is null
     */
    public IntegerSet union(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        IntegerSet result = copyOf(this);
        result.elements.addAll(b.elements);
        return result;
    }

    /**
     * Returns a new set containing only the elements found in both sets.
     *
     * @param b the other set; must not be null
     * @return a new set holding the intersection of this set and {@code b}
     * @throws NullPointerException if {@code b} is null
     */
    public IntegerSet intersect(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        IntegerSet result = copyOf(this);
        result.elements.retainAll(b.elements);
        return result;
    }

    /**
     * Returns a new set containing the elements of this set that are not in
     * {@code b}.
     *
     * @param b the set whose elements are excluded; must not be null
     * @return a new set holding {@code this - b}
     * @throws NullPointerException if {@code b} is null
     */
    public IntegerSet diff(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        IntegerSet result = copyOf(this);
        result.elements.removeAll(b.elements);
        return result;
    }

    /**
     * Returns a new set containing the elements of {@code b} that are not in
     * this set. This is the assignment-specific definition of complement,
     * i.e. {@code b - this}.
     *
     * @param b the other set; must not be null
     * @return a new set holding {@code b - this}
     * @throws NullPointerException if {@code b} is null
     */
    public IntegerSet complement(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        return b.diff(this);
    }

    /**
     * Tests whether every element of this set is also in {@code b}. An
     * empty set is a subset of every set.
     *
     * @param b the possible superset; must not be null
     * @return {@code true} if this set is a subset of {@code b}
     * @throws NullPointerException if {@code b} is null
     */
    public boolean isSubsetOf(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        return b.elements.containsAll(elements);
    }

    /**
     * Tests whether this set and {@code b} share no elements. Empty sets are
     * disjoint from every set.
     *
     * @param b the other set; must not be null
     * @return {@code true} if the two sets have no common element
     * @throws NullPointerException if {@code b} is null
     */
    public boolean isDisjoint(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        for (int value : elements) {
            if (b.elements.contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a new set containing the elements that appear in exactly one
     * of the two sets.
     *
     * @param b the other set; must not be null
     * @return a new set holding the symmetric difference
     * @throws NullPointerException if {@code b} is null
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        IntegerSet result = diff(b);
        result.elements.addAll(b.diff(this).elements);
        return result;
    }

    /**
     * Tests whether this set has no elements.
     *
     * @return {@code true} if the set is empty
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns the elements in ascending order, separated by a comma and one
     * space, inside square brackets, for example {@code [-3, 0, 8, 15]}. An
     * empty set yields {@code []}.
     *
     * @return the formatted string representation
     */
    @Override
    public String toString() {
        return elements.toString();
    }

    /**
     * Builds an independent copy of a set.
     *
     * @param source the set to copy
     * @return a new set with the same elements
     */
    private static IntegerSet copyOf(IntegerSet source) {
        IntegerSet copy = new IntegerSet();
        copy.elements.addAll(source.elements);
        return copy;
    }
}
