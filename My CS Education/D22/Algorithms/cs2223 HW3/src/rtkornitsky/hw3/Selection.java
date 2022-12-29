package rtkornitsky.hw3;
//DO NOT CHANGE THE CONTENTS OF THIS CLASS...

// Sedgewick, 4ed
public class Selection {
	
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Exchangeable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i+1; j < N; j++) {
                if (less(a[j], a[min])) {
                	min = j;
                }
            }
            exch(a, i, min);
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