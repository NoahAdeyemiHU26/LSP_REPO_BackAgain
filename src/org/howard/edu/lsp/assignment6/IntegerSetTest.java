package org.howard.edu.lsp.assignment6;
 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
 
/**
 * Assignment 6: JUnit 5 tests for IntegerSet.
 *
 * Complete every TODO. Do not remove, rename, or disable test methods.
 * Each test must contain meaningful assertions and run independently.
 */
public class IntegerSetTest {
 
    // ===== clear() =====
 
    /**
     * Create a populated set; call clear(); assert that it is empty and its length is zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsPopulatedSet() {
        IntegerSet set = setOf(1, 2, 3);

        set.clear();

        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertEquals("[]", set.toString());
        assertFalse(set.contains(1));
    }
 
    /**
     * Clear an already empty set; verify it remains empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsEmptySet() {
        IntegerSet set = new IntegerSet();

        set.clear();

        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertEquals("[]", set.toString());
    }
 
    // ===== length() =====
 
    /**
     * Add repeated and unique integers; assert the number of distinct elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthCountsUniqueElements() {
        IntegerSet set = new IntegerSet();

        set.add(5);
        set.add(5);
        set.add(3);
        set.add(3);
        set.add(3);
        set.add(9);

        assertEquals(3, set.length());
        set.add(-1);
        assertEquals(4, set.length());
    }
 
    /**
     * Verify a new empty set has length zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthEmptySetLength() {
        IntegerSet set = new IntegerSet();

        assertEquals(0, set.length());
    }
 
    // ===== equals() =====
 
    /**
     * Construct sets with identical elements in different insertion orders; assert equality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsIgnoresInsertionOrder() {
        IntegerSet a = new IntegerSet();
        a.add(1);
        a.add(2);
        a.add(3);
        IntegerSet b = new IntegerSet();
        b.add(3);
        b.add(1);
        b.add(2);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }
 
    /**
     * Construct sets of equal length with different elements; assert inequality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsDetectsDifferentContents() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(1, 2, 4);

        assertEquals(a.length(), b.length());
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
    }
 
    /**
     * Verify two separately constructed empty sets compare equal.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsEmptySetsEqual() {
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }
 
    /**
     * Verify a set equals itself and differs from a set of another size.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsSameObjectAndDifferentSizes() {
        IntegerSet a = setOf(1, 2);
        IntegerSet larger = setOf(1, 2, 3);

        assertTrue(a.equals(a));
        assertFalse(a.equals(larger));
        assertFalse(larger.equals(a));
    }
 
    // ===== contains() =====
 
    /**
     * Verify contains() is true for a present element and false for an absent one.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsPresentAndAbsent() {
        IntegerSet set = setOf(10, 20, 30);

        assertTrue(set.contains(20));
        assertFalse(set.contains(25));
        assertFalse(set.contains(-10));
    }
 
    /**
     * Verify an empty set does not contain an arbitrary integer.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsEmptySetContainsNothing() {
        IntegerSet set = new IntegerSet();

        assertFalse(set.contains(0));
        assertFalse(set.contains(1));
        assertFalse(set.contains(-1));
        assertFalse(set.contains(Integer.MAX_VALUE));
    }
 
    // ===== largest() =====
 
    /**
     * Verify largest() returns the maximum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestNegativeAndPositiveValues() {
        IntegerSet mixed = setOf(-5, 0, 7, -20, 3);
        IntegerSet allNegative = setOf(-9, -3, -12);
        IntegerSet single = setOf(42);

        assertEquals(7, mixed.largest());
        assertEquals(-3, allNegative.largest());
        assertEquals(42, single.largest());
    }
 
    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestEmptySetThrowsException() {
        IntegerSet empty = new IntegerSet();

        assertThrows(IllegalStateException.class, () -> empty.largest());

        IntegerSet cleared = setOf(1, 2);
        cleared.clear();
        assertThrows(IllegalStateException.class, () -> cleared.largest());
    }
 
    // ===== smallest() =====
 
    /**
     * Verify smallest() returns the minimum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestNegativeAndPositiveValues() {
        IntegerSet mixed = setOf(-5, 0, 7, -20, 3);
        IntegerSet allPositive = setOf(9, 3, 12);
        IntegerSet single = setOf(-42);

        assertEquals(-20, mixed.smallest());
        assertEquals(3, allPositive.smallest());
        assertEquals(-42, single.smallest());
    }
 
    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestEmptySetThrowsException() {
        IntegerSet empty = new IntegerSet();

        assertThrows(IllegalStateException.class, () -> empty.smallest());

        IntegerSet cleared = setOf(1, 2);
        cleared.clear();
        assertThrows(IllegalStateException.class, () -> cleared.smallest());
    }
 
    // ===== add() =====
 
    /**
     * Add the same integer repeatedly; verify only one copy exists.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddIgnoresDuplicates() {
        IntegerSet set = new IntegerSet();

        set.add(4);
        set.add(4);
        set.add(4);

        assertEquals(1, set.length());
        assertTrue(set.contains(4));
        assertEquals("[4]", set.toString());
    }
 
    /**
     * Add Integer.MIN_VALUE, Integer.MAX_VALUE, and zero; verify all are represented.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddHandlesIntegerBoundaries() {
        IntegerSet set = new IntegerSet();

        set.add(Integer.MIN_VALUE);
        set.add(Integer.MAX_VALUE);
        set.add(0);

        assertEquals(3, set.length());
        assertTrue(set.contains(Integer.MIN_VALUE));
        assertTrue(set.contains(Integer.MAX_VALUE));
        assertTrue(set.contains(0));
        assertEquals(Integer.MIN_VALUE, set.smallest());
        assertEquals(Integer.MAX_VALUE, set.largest());
        assertEquals("[-2147483648, 0, 2147483647]", set.toString());
    }
 
    // ===== remove() =====
 
    /**
     * Remove a present element; verify it is absent and others remain.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveRemovesExistingElement() {
        IntegerSet set = setOf(1, 2, 3);

        set.remove(2);

        assertFalse(set.contains(2));
        assertTrue(set.contains(1));
        assertTrue(set.contains(3));
        assertEquals(2, set.length());
        assertEquals("[1, 3]", set.toString());
    }
 
    /**
     * Remove an absent element; verify the set is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveIgnoresAbsentElement() {
        IntegerSet set = setOf(1, 2);

        set.remove(99);

        assertEquals(2, set.length());
        assertEquals("[1, 2]", set.toString());

        IntegerSet empty = new IntegerSet();
        empty.remove(5);
        assertTrue(empty.isEmpty());
    }
 
    // ===== union() =====
 
    /**
     * Verify union() contains all distinct elements from two overlapping sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionOverlappingSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);

        IntegerSet result = a.union(b);

        assertEquals("[1, 2, 3, 4, 5]", result.toString());
        assertEquals(5, result.length());
        assertEquals("[1, 2, 3, 4, 5]", b.union(a).toString());
    }
 
    /**
     * Verify union with an empty set and with the same object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionEmptyAndSameOperand() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();

        IntegerSet withEmpty = a.union(empty);
        IntegerSet emptyFirst = empty.union(a);
        IntegerSet withSelf = a.union(a);

        assertEquals("[1, 2, 3]", withEmpty.toString());
        assertEquals("[1, 2, 3]", emptyFirst.toString());
        assertEquals("[1, 2, 3]", withSelf.toString());
        assertNotSame(a, withSelf);
        assertEquals("[1, 2, 3]", a.toString());
        assertTrue(empty.isEmpty());
    }
 
    /**
     * Verify both inputs remain unchanged; modify the result and verify the inputs are still unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testUnionDoesNotMutateInputs() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4);

        IntegerSet result = a.union(b);

        assertEquals("[1, 2, 3]", a.toString());
        assertEquals("[3, 4]", b.toString());

        result.add(100);
        result.remove(1);

        assertEquals("[2, 3, 4, 100]", result.toString());
        assertEquals("[1, 2, 3]", a.toString());
        assertEquals("[3, 4]", b.toString());
    }
 
    // ===== intersect() =====
 
    /**
     * Verify intersection contains exactly the shared elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectOverlappingSets() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);

        IntegerSet result = a.intersect(b);

        assertEquals("[3, 4]", result.toString());
        assertEquals(2, result.length());
        assertEquals("[3, 4]", b.intersect(a).toString());
    }
 
    /**
     * Verify disjoint intersection is empty and self-intersection is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectDisjointAndSameOperand() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet disjoint = setOf(7, 8, 9);

        IntegerSet noShared = a.intersect(disjoint);
        IntegerSet withSelf = a.intersect(a);

        assertTrue(noShared.isEmpty());
        assertEquals("[]", noShared.toString());
        assertEquals("[1, 2, 3]", withSelf.toString());
        assertNotSame(a, withSelf);
        assertEquals("[1, 2, 3]", a.toString());
    }
 
    /**
     * Modify the intersection result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testIntersectIndependentResult() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5);

        IntegerSet result = a.intersect(b);
        result.add(99);
        result.remove(3);

        assertEquals("[4, 99]", result.toString());
        assertEquals("[1, 2, 3, 4]", a.toString());
        assertEquals("[3, 4, 5]", b.toString());
    }
 
    // ===== diff() =====
 
    /**
     * Verify A.diff(B) returns elements in A but not B, and distinguish direction.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffDirectionMatters() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5);

        IntegerSet aMinusB = a.diff(b);
        IntegerSet bMinusA = b.diff(a);

        assertEquals("[1, 2]", aMinusB.toString());
        assertEquals("[5]", bMinusA.toString());
        assertFalse(aMinusB.equals(bMinusA));
    }
 
    /**
     * Verify difference with an empty set and difference with itself.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffEmptyAndSameOperand() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();

        assertEquals("[1, 2, 3]", a.diff(empty).toString());
        assertEquals("[]", empty.diff(a).toString());
        assertEquals("[]", a.diff(a).toString());
        assertEquals("[1, 2, 3]", a.toString());
    }
 
    /**
     * Modify the difference result and verify neither input changes.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testDiffIndependentResult() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5);

        IntegerSet result = a.diff(b);
        result.add(50);
        result.remove(1);

        assertEquals("[2, 50]", result.toString());
        assertEquals("[1, 2, 3, 4]", a.toString());
        assertEquals("[3, 4, 5]", b.toString());
    }
 
    // ===== complement() =====
 
    /**
     * Verify A.complement(B) returns elements in B but not A.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementReverseDifference() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);

        IntegerSet aComplementB = a.complement(b);
        IntegerSet bComplementA = b.complement(a);

        assertEquals("[4, 5]", aComplementB.toString());
        assertEquals("[1, 2]", bComplementA.toString());
        assertTrue(aComplementB.equals(b.diff(a)));
    }
 
    /**
     * Verify complement with an empty receiver and with an empty argument.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testComplementEmptyOperands() {
        IntegerSet empty = new IntegerSet();
        IntegerSet b = setOf(1, 2, 3);

        assertEquals("[1, 2, 3]", empty.complement(b).toString());
        assertEquals("[]", b.complement(empty).toString());
        assertEquals("[]", empty.complement(new IntegerSet()).toString());
    }
 
    /**
     * Modify the complement result and verify neither input changes.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementIndependentResult() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);

        IntegerSet result = a.complement(b);
        result.add(77);
        result.remove(4);

        assertEquals("[5, 77]", result.toString());
        assertEquals("[1, 2, 3]", a.toString());
        assertEquals("[3, 4, 5]", b.toString());
    }
 
    // ===== isSubsetOf() =====
 
    /**
     * Verify a proper subset is recognized.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfProperSubset() {
        IntegerSet small = setOf(2, 3);
        IntegerSet large = setOf(1, 2, 3, 4);

        assertTrue(small.isSubsetOf(large));
        assertFalse(large.isSubsetOf(small));
    }
 
    /**
     * Verify a set with an element missing from the other set is not a subset.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfNotSubset() {
        IntegerSet a = setOf(1, 2, 9);
        IntegerSet b = setOf(1, 2, 3, 4);

        assertFalse(a.isSubsetOf(b));
        assertFalse(setOf(10).isSubsetOf(setOf(1, 2, 3)));
    }
 
    /**
     * Verify equal sets are subsets of each other.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsSubsetOfEqualSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 2, 1);

        assertTrue(a.isSubsetOf(b));
        assertTrue(b.isSubsetOf(a));
        assertTrue(a.isSubsetOf(a));
    }
 
    /**
     * Verify the empty set is a subset of a nonempty set, but not vice versa.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfEmptyCases() {
        IntegerSet empty = new IntegerSet();
        IntegerSet nonEmpty = setOf(1, 2);

        assertTrue(empty.isSubsetOf(nonEmpty));
        assertFalse(nonEmpty.isSubsetOf(empty));
        assertTrue(empty.isSubsetOf(new IntegerSet()));
    }
 
    // ===== isDisjoint() =====
 
    /**
     * Verify disjoint sets return true.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointNoOverlap() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(4, 5, 6);

        assertTrue(a.isDisjoint(b));
        assertTrue(b.isDisjoint(a));
    }
 
    /**
     * Verify overlapping sets return false.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointOverlap() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);

        assertFalse(a.isDisjoint(b));
        assertFalse(b.isDisjoint(a));
    }
 
    /**
     * Verify disjointness with an empty set and with the same nonempty object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointEmptyAndSameOperand() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();

        assertTrue(a.isDisjoint(empty));
        assertTrue(empty.isDisjoint(a));
        assertFalse(a.isDisjoint(a));
    }
 
    // ===== symmetricDiff() =====
 
    /**
     * Verify symmetric difference contains elements appearing in exactly one set.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testSymmetricDiffPartiallyOverlappingSets() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);

        IntegerSet result = a.symmetricDiff(b);

        assertEquals("[1, 2, 5, 6]", result.toString());
        assertEquals(4, result.length());
        assertEquals("[1, 2, 5, 6]", b.symmetricDiff(a).toString());
    }
 
    /**
     * Verify symmetric difference of identical sets is empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIdenticalSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet copy = setOf(3, 2, 1);

        assertTrue(a.symmetricDiff(copy).isEmpty());
        assertEquals("[]", a.symmetricDiff(copy).toString());
        assertTrue(a.symmetricDiff(a).isEmpty());
    }
 
    /**
     * Verify behavior with an empty set and with disjoint sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffEmptyAndDisjointSets() {
        IntegerSet a = setOf(1, 2);
        IntegerSet empty = new IntegerSet();
        IntegerSet disjoint = setOf(3, 4);

        assertEquals("[1, 2]", a.symmetricDiff(empty).toString());
        assertEquals("[1, 2]", empty.symmetricDiff(a).toString());
        assertEquals("[1, 2, 3, 4]", a.symmetricDiff(disjoint).toString());
        assertTrue(empty.symmetricDiff(new IntegerSet()).isEmpty());
    }
 
    /**
     * Modify the result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIndependentResult() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);

        IntegerSet result = a.symmetricDiff(b);
        result.add(60);
        result.remove(1);

        assertEquals("[2, 4, 5, 60]", result.toString());
        assertEquals("[1, 2, 3]", a.toString());
        assertEquals("[3, 4, 5]", b.toString());
    }
 
    // ===== isEmpty() =====
 
    /**
     * Verify a newly constructed set is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyNewSet() {
        IntegerSet set = new IntegerSet();

        assertTrue(set.isEmpty());
    }
 
    /**
     * Verify a set with an element is not empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyPopulatedSet() {
        IntegerSet set = new IntegerSet();
        set.add(0);

        assertFalse(set.isEmpty());
    }
 
    /**
     * Clear a populated set and verify it is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyAfterClear() {
        IntegerSet set = setOf(1, 2, 3);
        assertFalse(set.isEmpty());

        set.clear();

        assertTrue(set.isEmpty());
    }
 
    // ===== toString() =====
 
    /**
     * Verify elements appear in ascending numeric order regardless of insertion order.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringAscendingOrder() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(-3);
        set.add(10);
        set.add(0);
        set.add(2);

        assertEquals("[-3, 0, 2, 5, 10]", set.toString());
    }
 
    /**
     * Verify an empty set formats as [].
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testToStringEmptyFormatting() {
        IntegerSet set = new IntegerSet();

        assertEquals("[]", set.toString());
    }
 
    /**
     * Verify comma-space formatting, brackets, and duplicate suppression.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringExactSpacingAndDuplicates() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(1);
        set.add(3);
        set.add(2);
        set.add(1);

        assertEquals("[1, 2, 3]", set.toString());
        assertEquals("[7]", setOf(7).toString());
    }

    // ===== helper =====

    /**
     * Builds a new set holding the given values. Each call returns a fresh
     * object, so no state is shared between tests.
     *
     * @param values the values to add
     * @return a new set containing the values
     */
    private static IntegerSet setOf(int... values) {
        IntegerSet set = new IntegerSet();
        for (int v : values) {
            set.add(v);
        }
        return set;
    }

}
