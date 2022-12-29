package rtkornitsky.hw3;
//DO NOT CHANGE THE CONTENTS OF THIS CLASS...

/**
 * @author Sedgewick
 * @author Heineman for instrumentation
 */
public class Insertion {

	/**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
	public static void sort(Exchangeable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);   
            }
        }
    }
	
   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Exchangeable v, Exchangeable w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Exchangeable[] a, int i, int j) {
    	Exchangeable swap = a[i];
    	a[i] = a[j];
        a[j] = swap;
    	a[i].incrementExchangeCount();   // if same, count twice
        a[j].incrementExchangeCount();
    }
    
}