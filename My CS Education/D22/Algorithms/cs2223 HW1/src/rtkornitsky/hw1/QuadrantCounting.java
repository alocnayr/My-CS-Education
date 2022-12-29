package rtkornitsky.hw1;

import java.awt.Point;

import algs.hw1.fixed.CartesianTrials;
import algs.hw1.fixed.OrderedArray;
import algs.hw1.fixed.Sorting;

/**
 * For this question, you are asked to write efficient count that computes the
 * number of Cartesian points in each quadrant.
 * 
 * ^ II | I | <--------------------------> | III | IV V
 *
 *
 * You can take advantage of the fact that the points are already sorted by
 * {@link Sorting#compareCartesianByQuadrant}.
 */
public class QuadrantCounting extends CartesianTrials {

	/**
	 * Return the count of Cartesian points in the given quadrant, assuming that the
	 * points have already been sorted in ascending order by
	 * {@link Sorting#compareCartesianByQuadrant}. Take advantage of this ordering
	 * to write a more efficient implementation than the naive, brute-force
	 * implementation
	 */
	@Override
	protected int countCartesiansInQuadrant(OrderedArray<Point> cartesians, int q) {
		if (q == 1) {

			int lo = 0;
			int hi = cartesians.length() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) / 2;
				Point rc = cartesians.get(mid);
				if (rc.x >= 0) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
			return hi + 1;

		}
		if (q == 2) {
			Point leftPoint = new Point(0, Integer.MAX_VALUE);
			Point rightPoint = new Point(Integer.MIN_VALUE, 0);

			// finding left edge

			int lo = 0;
			int hi = cartesians.length() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) / 2;
				int rc = Sorting.compareCartesianByQuadrant.compare(cartesians.get(mid), leftPoint);
				if (rc >= 0) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
			int leftEdge = lo;

			// finding right edge 
			
			lo = 0;
			hi = cartesians.length() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) / 2;
				int rc = Sorting.compareCartesianByQuadrant.compare(cartesians.get(mid), rightPoint);
				if (rc <= 0) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
			int rightEdge = hi;
			return rightEdge - leftEdge + 1;
		}
		if (q == 3) {

			Point leftPoint = new Point(Integer.MIN_VALUE, 0);
			Point rightPoint = new Point(0, Integer.MIN_VALUE);

			// finding left edge
			int lo = 0;
			int hi = cartesians.length() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) / 2;
				int rc = Sorting.compareCartesianByQuadrant.compare(cartesians.get(mid), leftPoint);
				if (rc >= 0) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
			int leftEdge = lo;

			//right edge 
			lo = 0;
			hi = cartesians.length() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) / 2;
				int rc = Sorting.compareCartesianByQuadrant.compare(cartesians.get(mid), rightPoint);
				if (rc <= 0) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
			int rightEdge = hi;
			return rightEdge - leftEdge + 1;
		}
		if (q == 4) {

			Point leftPoint = new Point(0, Integer.MIN_VALUE);

			int lo = 0;
			int hi = cartesians.length() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) / 2;
				int rc = Sorting.compareCartesianByQuadrant.compare(cartesians.get(mid), leftPoint);
				if (rc >= 0) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
			return cartesians.length() - hi - 1;

		}
		return 0;
	}

	/** Leave this function unchanged. */
	public static void main(String[] args) {
		new QuadrantCounting().runTrial();
	}
}

