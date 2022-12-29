package rtkornitsky.hw3;

// Proper HeapSort from Sedgewick, 4ed
// observe that it doesn't use swim operation -- can you see why?
public class HeapSort {

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * @param a the array to be sorted
	 */
	public static void sort(Exchangeable[] a) {
		int N = a.length;
		
		// create heap
		for (int k = N/2; k >= 1; k--) {
			sink(a, k, N);
		}
		
		// modify array in place, moving largest item to rightmost spot
		while (N > 1) {
			exch(a, 1, N--);
			sink(a, 1, N);
		}
	}

	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	static void sink(Exchangeable[] a, int k, int N) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(a, j, j+1)) j++;
			if (!less(a, k, j)) break;
			exch(a, k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for comparisons and swaps.
	 * Indices are "off-by-one" to support 1-based indexing.
	 ***************************************************************************/
	static boolean less(Exchangeable[] a, int i, int j) {
		return a[i-1].compareTo(a[j-1]) < 0;
	}

	static void exch(Exchangeable[] a, int i, int j) {
		Exchangeable swap = a[i-1];
		a[i-1] = a[j-1];
		a[j-1] = swap;
    	a[i-1].incrementExchangeCount();   // if same, count twice
        a[j-1].incrementExchangeCount();
	}

}