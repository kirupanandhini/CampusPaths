/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package sets;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * FiniteSetTest is a glassbox test of the FiniteSet class.
 */
public class FiniteSetTest {

  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.FiniteSet() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Test creating basic sets.
   */
  @Test
  public void testCreationEmptySet() {
    assertEquals(Arrays.asList(),
        FiniteSet.of(new float[0]).getPoints());
  }

  /**
   * Test creating basic sets.
   */
  @Test
  public void testCreationBasic() {
    assertEquals(Arrays.asList(1f),
        FiniteSet.of(new float[] {1}).getPoints());      // one item
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {1, 2}).getPoints());   // two items
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {2, 1}).getPoints());   // two out-of-order
  }

  /**
   * Test creating a set that contains a negative point.
   */
  @Test
  public void testCreationNegative() {
    assertEquals(Arrays.asList(-2f, 2f),
        FiniteSet.of(new float[] {2, -2}).getPoints());
  }

  // Note:
  // The following are sets used throughout the rest of the tests.

  /** An empty set. */
  private static FiniteSet S0 = FiniteSet.of(new float[0]);

  /** A "singleton" set. */
  private static FiniteSet S1 = FiniteSet.of(new float[] {1});

  /** A "complex" set. Or, a set that contains more than one value. */

  private static FiniteSet S2 = FiniteSet.of(new float[] {2});
  private static FiniteSet S3 = FiniteSet.of(new float[] {1, 2, 3, 4, 5});
  private static FiniteSet S4 = FiniteSet.of(new float[] {2, 4, 5, 7, 8});
  private static FiniteSet S5 = FiniteSet.of(new float[] {6, 7, 8, 9, 10});



  private static FiniteSet S12 = FiniteSet.of(new float[] {1, 2});



  // TODO: Feel free to initialize additional (private static) FiniteSet
  //       objects here if you plan to use more of them for the tests you
  //       need to implement below.

  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.equals() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Test set equality on an empty set.
   */
  @Test
  public void testEqualsEmptySet() {
    assertTrue(S0.equals(S0));
    assertFalse(S0.equals(S1));
    assertFalse(S0.equals(S12));
  }

  /**
   * Test set equality on a set containing one point.
   */
  @Test
  public void testEqualsSingleton() {
    assertFalse(S1.equals(S0));
    assertTrue(S1.equals(S1));
    assertFalse(S1.equals(S12));
  }

  /**
   * Test set equality on a set of multiple points.
   */
  @Test
  public void testEqualsComplexSet() {
    assertFalse(S12.equals(S0));
    assertFalse(S12.equals(S1));
    assertTrue(S12.equals(S12));
  }

  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.size() Test
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Test set size.
   */
  @Test
  public void testSize() {
    assertEquals(S0.size(), 0);
    assertEquals(S1.size(), 1);
    assertEquals(S12.size(), 2);
  }

  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.union() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the union of two finite sets.
   */
  // Union of a set with itself is the same set
  @Test
  public void testUnionWithItself() {
    // TODO: implement tests for FiniteSet.union()
    assertEquals(S4,
            S4.union(S4));
    assertEquals(S3,
            S3.union(S3));
    assertEquals(S3,
            S3.union(S3));

  }

  // Union of a set with an empty set is the same original set
  @Test
  public void testUnionWithEmpty() {
    assertEquals(S12,
            S1.union(S12));
    assertEquals(S12,
            S12.union(S1));
    assertEquals(S3, S1.union(S3));
  }

  // Union of two sets that share values
  @Test
  public void testUnionSharedValues() {
    assertEquals(FiniteSet.of(new float[] {1, 2, 3, 4, 5, 7, 8}), S3.union(S4));
    assertEquals(FiniteSet.of(new float[] {2, 4, 5, 6, 7, 8, 9, 10}), S5.union(S4));
  }

  // Union of two sets that don't share values
  @Test
  public void testUnionNoSharedValues() {
    assertEquals(FiniteSet.of(new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}), S5.union(S3));
    assertEquals(FiniteSet.of(new float[] {1, 6, 7, 8, 9, 10}), S5.union(S1));
  }

  // Checks sets that are not equal
  @Test
  public void testUnionNotEquals() {
    assertNotEquals(S5,
            S2.union(S1));
    assertNotEquals(S3,
            S1.union(S2));
  }


    ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.intersection() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the intersection of two finite sets.
   */

  // Intersection of a set with itself is the same set
  @Test
  public void testIntersectionWithItself() {
    // TODO: implement tests for FiniteSet.intersection()
    assertEquals(S4,
            S4.intersection(S4));
    assertEquals(S3,
            S3.intersection(S3));

  }

  // Intersection of a set with an empty set is an empty set
  @Test
  public void testIntersectionWithEmptySet() {
    assertEquals(S0,
            S0.intersection(S12));
    assertEquals(S0,
            S0.intersection(S5));

  }


  // Intersection of two sets that share values
  @Test
  public void testIntersectionWithSharedValues() {
    assertEquals(S1,
            S12.intersection(S1));
    assertEquals(FiniteSet.of(new float[] {2, 4, 5}),
            S3.intersection(S4));
    assertEquals(FiniteSet.of(new float[] {2, 4, 5}),
            S4.intersection(S3));



  }

  // Intersection of two sets that don't share values
  @Test
  public void testIntersectionWithoutSharedValues() {
    assertEquals(S0, S5.intersection(S3));
    assertEquals(S0, S1.intersection(S5));
  }

  // Checks the intersection or two sets that is not equal to the other
  @Test
  public void testIntersectionNotEquals() {
    assertNotEquals(S0, S4.intersection(S4));
    assertNotEquals(S0, S4.intersection(S5));
  }

  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.difference() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the difference of two finite sets.
   */

  // subsets (this is a subset of other) -> an empty set
  @Test
  public void testDifferenceSubset1() {
    assertEquals(S0,
            S1.difference(S12));
    assertEquals(S0, S1.difference(S3));
  }

  // subsets (other is a subset of this)
  @Test
  public void testDifferenceSubset2() {
    assertEquals(S2,
            S12.difference(S1));
  }

  // tests difference of two empty sets
  @Test
  public void testDifferenceTwoEmptySets() {
    assertEquals(S0,
            S0.difference(S0));
  }

  // tests difference of one empty set in both directions
  @Test
  public void testDifferenceOneEmptySet() {
    assertEquals(S0,
            S0.difference(S12));
    assertEquals(S12,
            S12.difference(S0));
  }

  // tests difference of two sets that share values
  @Test
  public void testDifferenceSharedValues() {
    assertEquals(FiniteSet.of(new float[] {1, 3}), S3.difference(S4));
    assertEquals(FiniteSet.of(new float[] {6, 9, 10}), S5.difference(S4));
    // two sets share values (other direction)
    assertEquals(FiniteSet.of(new float[] {7, 8}), S4.difference(S3));
    assertEquals(FiniteSet.of(new float[] {2, 4, 5}), S4.difference(S5));

  }

  // tests difference of two sets that don't share values
  @Test
  public void testDifferenceNotSharedValues() {
    assertEquals(S5, S5.difference(S3));
    // other direction
    assertEquals(S3, S3.difference(S5));

    assertEquals(S5, S5.difference(S1));
    assertEquals(S1, S1.difference(S5));

  }

}
