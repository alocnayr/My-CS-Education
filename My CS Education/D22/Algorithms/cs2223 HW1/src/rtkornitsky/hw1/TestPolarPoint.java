package rtkornitsky.hw1;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import org.junit.Test;

import algs.hw1.fixed.Sorting;

public class TestPolarPoint {

	@Test
	public void testByQuadrant() {
		Point q1 = new Point(10, 10);
		Point q2 = new Point(-10, 10);
		Point q3 = new Point(-10, -10);
		Point q4 = new Point(10, -10);
		
		assertEquals(1, Sorting.compareCartesianByQuadrant.compare(q4,q1));
		assertEquals(1, Sorting.compareCartesianByQuadrant.compare(q4,q2));
		assertEquals(1, Sorting.compareCartesianByQuadrant.compare(q4,q3));
		
		assertEquals(1, Sorting.compareCartesianByQuadrant.compare(q3,q1));
		assertEquals(1, Sorting.compareCartesianByQuadrant.compare(q3,q2));
		assertEquals(-1, Sorting.compareCartesianByQuadrant.compare(q3,q4));
		
		assertEquals(1, Sorting.compareCartesianByQuadrant.compare(q2,q1));
		assertEquals(-1, Sorting.compareCartesianByQuadrant.compare(q2,q3));
		assertEquals(-1, Sorting.compareCartesianByQuadrant.compare(q2,q4));
		
		assertEquals(-1, Sorting.compareCartesianByQuadrant.compare(q1,q2));
		assertEquals(-1, Sorting.compareCartesianByQuadrant.compare(q1,q3));
		assertEquals(-1, Sorting.compareCartesianByQuadrant.compare(q1,q4));
		
		assertEquals(0, Sorting.compareCartesianByQuadrant.compare(q1,q1));
		assertEquals(0, Sorting.compareCartesianByQuadrant.compare(q2,q2));
		assertEquals(0, Sorting.compareCartesianByQuadrant.compare(q3,q3));
		assertEquals(0, Sorting.compareCartesianByQuadrant.compare(q4,q4));
	}
}
