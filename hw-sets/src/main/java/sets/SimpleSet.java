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

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {

  // TODO: fill in and document the representation
  //       Make sure to include the representation invariant (RI)
  //       and the abstraction function (AF).

  // RI: Exactly one of the fields (finite or complementOfFinite)is null, and the other is
  // non-null.
  // Abstraction Function
  // AF (this): represents either a finite set, where this.finite contains its elements and
  //            this.complementOfFinite is null, or if a set is infinite, this.complementOfFinite
  //            represents the excluded elements of the infinite set and this.finite is null.

  // FIELDS
  // CHANGE FIELD NAMES TO BE MORE
  private final FiniteSet finite;
  private final FiniteSet complementOfFinite;


  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
      this.finite = FiniteSet.of(vals);
      this.complementOfFinite = null;
    // TODO: implement this
  }

  // Constructs a Simple Set with complementOfFinite Set


  // private constructor that we only use in the complement methods???
  private SimpleSet(FiniteSet finite, Boolean isAFinite) {
    if (isAFinite) {
      this.finite = finite;
      this.complementOfFinite = null;
    } else {
      this.complementOfFinite = finite;
      this.finite = null;
    }
  }

  // HINT: feel free to create other constructors!



//  public SimpleSet(float[] vals) {
//
//  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;

    SimpleSet other = (SimpleSet) o;

    if (this.finite != null) {
      return this.finite.equals(other.finite);
    } else {
      return this.complementOfFinite.equals(other.complementOfFinite);
    }

    // TODO: replace this with a correct check
  }

  @Override
  public int hashCode() {
    if (this.finite != null) {
      return this.finite.hashCode();
    } else {
      return this.complementOfFinite.hashCode();
    }
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    // TODO: implement this
    // in size() we have two cases. If this.finite != null, that means the set we are representing
    // is finite. It is represented through this.finite (a FiniteSet). To find the size of the
    // set, we can employ the size() method from FiniteSet. In that case, we would just return the
    // number of points in the set through the size of this.finite.
    // Case 2: our if condition does not hold, which means that this.finite is null
    // Since our rep invariant states that one of our fields is null and the other is non null
    // we know that this.complement is not null. Therefore, in this case, we are representing
    // an infinite, and our FiniteSet field represents the finite set of elements that are
    // excluded from the infinite set. Since we have an infinite set, our postcondition states
    // that we should return infinity. Thus, we return a float that represents positive
    // infinity.

    if (this.finite != null) {
      return this.finite.size();
    }
    return Float.POSITIVE_INFINITY;  // TODO: you should replace this value
  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers. These
   *     floats will be turned into strings in the standard manner (the same as
   *     done by, e.g., String.valueOf(float)).
   */
  public String toString() {
    // TODO: implement this with a loop. document its invariant
    //       a StringBuilder may be useful for creating the string

    // Why this works
    // In toString, we take in different cases, and determine our output from that.
    // If our finite set is empty, we set buf = "{}", if we have an infinite set that does not
    // exclude any elements, we set buf = "R". If this represents a finite set, we can
    // store the values in the set in a List (points) using the getPoints() method from finiteSet.
    // If we this is infinite, we can store the values of the complementOfFinite in List points.
    // Our invariant states that buf is R if this is infinite, buf is {} if this is empty.
    // Otherwise, buf = {points[0], ..., points[i - 1], and if it is an infinite set there is a
    // "R \ " at the beginning. This holds before we enter the loop because points[0] to
    // points[-1] would represent a set with nothing in it. Therefore, we would only have {,
    // which is exactly what buf is before we enter the loop. The loop invariant holds each time
    // because we append new points (up until points[i-1]) and increment i (the index).
    // Lastly, the loop holds when we exit because i = length, which means points[0] to points[i-1]
    // represents every element in the set.
    // from there, if we are dealing with a finite set or exclusion of a finite set, we append a
    // curly brace at the end.
    // Then, we return buf.toString (our String).

    StringBuilder buf = new StringBuilder();

    boolean emptyOrInfinite = true; // represents whether we are returning an empty/infinite set
                                    // or a set with actual elements
    int length = 0;
    int i = 0;
    List<Float> points;
    if (this.finite != null) {
      if (this.finite.size() == 0) {
        buf.append("{}");
        points = null;
      } else {
        length = this.finite.size();
        points = this.finite.getPoints();
        buf.append("{");
        emptyOrInfinite = false;
      }
    } else if (this.complementOfFinite.size() == 0) {
      buf.append("R");
      points = null;
    } else {
      emptyOrInfinite = false;
      length = this.complementOfFinite.size();
      points = this.complementOfFinite.getPoints();
      buf.append("R \\ {");
    }

    // Inv: The buf has R if it is an infinite set, buf = {} if its an empty set.
    // Otherwise, buf = {points[0], ..., points[i - 1], and if it is an infinite set there is a
    // "R \ " at the beginning.
    while(i != length) {
      buf.append(String.valueOf(points.get(i)) + ", ");
      i = i + 1;
    }

    if (emptyOrInfinite != true) {
      buf.delete(buf.length() - 2, buf.length());
      buf.append("}");
    }

    return buf.toString();  // TODO: you should replace this value
  }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  public SimpleSet complement() {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
    SimpleSet complementSet; // create a SimpleSet to store our complement

    // Case 1: this.finite is not null. This means that we are representing a finite set. The
    // complement of a finite set would be an infinite set that excludes the elements in the
    // original finite set. We can actually represent this using a SimpleSet by setting its
    // complementOfFinite field equal to the original finite set (which is what we are creating
    // inside the branch).
    // Case 2: the other (else) case is when this.finite is null. Since one of our fields has to
    // be null and the other must be non-null, we can imply that its complementOfFinite field
    // is non-null. Therefore, this is an infinite set that excludes a finite set of elements
    // (which is represented through this.complementOfFinite). The complement of an infinite set
    // that excludes a finite set would simply the finite set (complementOfFinite).
    // Therefore, we can create a new SimpleSet object that sets this.finite equal
    // the complementOfFinite (creates a finite set representation).

    if (this.finite != null) {
      complementSet = new SimpleSet(this.finite, false);
    } else {
      complementSet = new SimpleSet(this.complementOfFinite, true);
    }

    // Switch the values of this.finite and this.complementOfFinite and return
    return complementSet;   // TODO: you should replace this value
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  public SimpleSet union(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
    SimpleSet unionSet; // a SimpleSet that represents the union of this and other

    // Case 1: if this represents a finite set and other represents a finite set.
    //         Since we're dealing with two finite sets, we can use the union method
    //         from the FiniteSet class to take the union of these two SimpleSets.
    // Case 2: if this represents a finite set and other represents an infinite set.
    //         We're dealing with one finite set (this) and one infinite set (other),
    //         the union of the sets would be what is in this, but not in other (because
    //         other represents all the values that are NOT in the infinite set).
    //         However, that would give us an infinite set, so should instead think about
    //         what elements will NOT be included in the set and set our complementOfFinite
    //         equal to that when we create a SimpleSet. Thus, we would actually want to figure
    //         out what is in other but NOT in this (to figure out what elements will not be
    //         included in the union). So, we would implement other - this via the difference
    //         method in FiniteSet.
    // Case 3: if this represents an infinite set and other represents a finite set.
    //         The union of these sets would again be infinite, so we would want to focus
    //         on finding the elements that will not be included in the union. This would
    //         be the elements that are in this but not in other. Thus, we can use the difference
    //         method from FiniteSet (this - other) to determine what our complementOfFinite set
    //         will be in our new SimpleSet.
    // Case 4: if this and other both represent infinite sets.
    //         The union of these sets would be infinite, so we would want to find the complement
    //         of the set, which would be finite. We can find these values by seeing what values
    //         occur in both other.complementOfFinite and this.complementOfFinite (the values that
    //         do NOT occur in either set). Thus, we can use the intersection method from
    //         FiniteSet. This would represent that values that aren't in the union of the sets
    //         , so we can set that equal to the complementOfFinite in our new SimpleSet
    //         that we return.
    if ((this.finite != null) && (other.finite != null)) {
      unionSet = new SimpleSet(this.finite.union(other.finite), true);
    } else if ((this.finite != null) && (other.finite == null)) {
      unionSet = new SimpleSet(other.complementOfFinite.difference(this.finite), false);
    } else if ((this.finite == null) && (other.finite != null)) {
      unionSet = new SimpleSet(this.complementOfFinite.difference(other.finite), false);
    } else {
      unionSet = new SimpleSet(other.complementOfFinite.intersection(this.complementOfFinite),
              false);
    }

    return unionSet;  // TODO: you should replace this value
  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersected with other
   */
  public SimpleSet intersection(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct
    // NOTE: There is more than one correct way to implement this.
    SimpleSet intersectionSet; // SimpleSet that represents the intersection of two sets

    // Case 1: if this is finite and other is finite.
    // Since we have two finite sets, we can use the intersection method from FiniteSet to
    // find their intersection. Then, we can represent that with a finite field in our SimpleSet
    // that we return.
    // Case 2: if this is finite and other is infinite.
    // We want to see what elements in this.finite and not in other.complementOfFinite (because
    // other.complementOfFinite represents the numbers that are not in the infinite set).
    // Therefore, we can use the difference method to calculate this - other. Since,
    // the intersection of a finite and infinite set would be a finite set, we can
    // represent that difference with a finite field in our SimpleSet that we return.
    // Case 3: if this is infinite and other is finite.
    // Using the reasoning from case 2, the intersection would be the elements that are in
    // other.finite but not in this.complementOfFinite. Thus, we would use the difference method
    // to calculate other - this. This would represent a finite field in our SimpleSet that
    // we return.
    // Case 4: if this is infinite and other is infinite.
    // The intersection of two infinite sets would also be an infinite set. Thus, it would make
    // more sense to represent this as a complementOfFinite (an infinite set that excludes
    // a finite set). To figure out what elements will be excluded we can take the union
    // of the complements of this and other. We can then represent that as a complementOfFinite
    // field (so that it represents the excluded set) in our SimpleSet that we return.
    if (this.finite != null) {
      if (other.finite != null) {
        intersectionSet = new SimpleSet(this.finite.intersection(other.finite), true);
      } else {
        intersectionSet = new SimpleSet(this.finite.difference(other.complementOfFinite), true);
      }
    } else {
      if (other.finite != null) {
        intersectionSet = new SimpleSet(other.finite.difference(this.complementOfFinite), true);
      } else {
        intersectionSet = new SimpleSet(other.complementOfFinite.union(this.complementOfFinite), false);
      }
    }

    return intersectionSet;  // TODO: you should replace this value
  }

  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  public SimpleSet difference(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct
    // NOTE: There is more than one correct way to implement this.
    SimpleSet differenceSet; // A SimpleSet that represents the difference of two sets

    // Case 1: this is finite and other is finite
    // Since we have two finite sets, we can employ the difference method from FiniteSet
    // to find their difference. We can then represent that as a finite set in our new
    // SimpleSet that we return.
    // Case 2: this is finite and other is infinite.
    // The elements that are in finite but not in infinite, would be the same thing as
    // the union of this.finite and the elements that are not in the infinite set
    // (other.complementOfFinite). We can represent that intersection as a finite set
    // in our SimpleSet that we return.
    // Case 3: this is infinite and other is finite.
    // The elements that are in this but not in other would represent a set that is infinite.
    // Therefore, we should represent the excluded elements of the difference in our
    // complementOfFinite. The "excluded elements" would be the elements that are not in this
    // and are in other. This represents the union of this.finite and
    // other.complementOfFinite (elements excluded from the other, which is an infinite set).
    // We can represent that union as a complementOfFinite in our new SimpleSet, since it
    // excludes the elements we want.
    // Case 4: this is infinite and other is infinite.
    // The elements that are in this and not in other would be a finite set (because there
    // are a lot of (infinite) amount of elements that are in this and in other). We know that
    // the elements that are not in other are represented by other.complementOfFinite. We also
    // want the elements that are NOT in this.complementOfFinite (In other words, we want
    // elements from this). To find elements that are in other.complementOfFinite and
    // elements that are not in this.complementOfFinite, we can use the difference method
    // (other.complementOfFinite - this.complementOfFinite). We can then represent that value
    // as a finite in our new SimpleSet.

    if (this.finite != null) {
      if (other.finite != null) {
        differenceSet = new SimpleSet(this.finite.difference(other.finite), true);
      } else {
        differenceSet = new SimpleSet(this.finite.intersection(other.complementOfFinite), true);
      }
    } else {
      if (other.finite != null) {
        differenceSet = new SimpleSet(other.finite.union(this.complementOfFinite), false);
      } else {
        differenceSet = new SimpleSet(other.complementOfFinite.difference(this.complementOfFinite), true);
      }
    }

    return differenceSet;  // TODO: you should replace this value
  }

}
