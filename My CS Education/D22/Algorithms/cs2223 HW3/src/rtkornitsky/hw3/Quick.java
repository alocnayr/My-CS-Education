package rtkornitsky.hw3;
//DO NOT CHANGE THE CONTENTS OF THIS CLASS...

import edu.princeton.cs.algs4.StdRandom;

/**
 *  The <tt>Quick</tt> class provides static methods for sorting an
 *  array and selecting the ith smallest element in an array using quicksort.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  The following is also quite useful:
 *  
 *  http://homepages.math.uic.edu/~leon/cs-mcs401-r07/handouts/quicksort-continued.pdf
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author George Heineman [modified for this homework]
 */
public class Quick {

	int numExchanges;
	
    /** Copied from StdRandom.shuffle(). Bringing here so you can see the exchanges... */
    public static void shuffle(Exchangeable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + StdRandom.uniform(N-i);     // between i and N-1
            exch(a, i, r);
        }
    }
    
    /**
     * Rearranges the array in ascending order, using the natural order.
     */
    public static void sort(Exchangeable[] a) {
    	// QuickSort shuffles to avoid O(N^2) behavior if mostly sorted. To provide 
    	// a fair comparison, we STILL shuffle and make sure to account for exch() there as well.
        shuffle(a);    
        sort(a, 0, a.length - 1);
    }

    /** quicksort the subarray from a[lo] to a[hi] */
    static void sort(Exchangeable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        int loc = partition(a, lo, hi);
        sort(a, lo, loc-1);
        sort(a, loc+1, hi);
    }

    /** partition the subarray a[lo..hi] so that a[lo..right-1] <= a[right] <= a[right+1..hi]
     * and return the index right. */
    static int partition(Exchangeable[] a, int lo, int hi) {
        int left = lo;
        int right = hi + 1;
        Exchangeable v = a[lo];  
        while (true) { 

            // find item on lo to swap
            while (less(a[++left], v))
                if (left == hi) break;

            // find item on hi to swap
            while (less(v, a[--right]))
                if (right == lo) break;     

            // check if pointers cross
            if (left >= right) break;

            exch(a, left, right);
        }

        // put partitioning item v at a[right]
        exch(a, lo, right);

        // now, a[lo .. right-1] <= a[right] <= a[right+1 .. hi]
        return right;
    }

   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    /** is v < w ? */
    static boolean less(Exchangeable v, Exchangeable w) {
        return v.compareTo(w) < 0;
    }
        
    /** exchange a[i] and a[j] and be sure to increment counts for both objects. */
    static void exch(Exchangeable[] a, int i, int j) {
    	Exchangeable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    	a[i].incrementExchangeCount();   // if same, count twice
        a[j].incrementExchangeCount();
    }

}

