package rtkornitsky.hw3;
//DO NOT CHANGE THE CONTENTS OF THIS CLASS...

// Instrumented MergeSort 
public class Merge {
    
	static Exchangeable aux[];
	
    public static void sort(Exchangeable[] a) {
    	aux = new Exchangeable[a.length];
    	sort (a, 0, a.length-1);
    }
    
    // recursive helper function
    static void sort (Exchangeable[] a, int lo, int hi) {
    	if (hi <= lo) return;
    	
    	int mid = lo + (hi - lo)/2;
    	
    	sort(a, lo, mid);
    	sort(a, mid+1, hi);
    	merge(a, lo, mid, hi);
    }
    
    // merge sorted results a[lo..mid] with a[mid+1..hi] back into a
    static void merge (Exchangeable[] a, int lo, int mid, int hi) {
    	int left = lo;     // starting index into left sorted sub-array
    	int right = mid+1;  // starting index into right sorted sub-array
    	
    	// copy a[lo..hi] into aux[lo..hi]
    	for (int k = lo; k <= hi; k++) {
    		aux[k] = a[k];
    		aux[k].incrementExchangeCount();  // half of the exchange...
    	}
    	
    	// now comes the merge. Something you might simulate with flashcards
    	// drawn from two stack piles. This is the heart of mergesort. 
    	for (int k = lo; k <= hi; k++) {
    		if       (left > mid)                   { a[k] = aux[right++]; }
    		else if  (right > hi)                   { a[k] = aux[left++];  }
    		else if  (less(aux[right], aux[left]))  { a[k] = aux[right++]; }
    		else                                    { a[k] = aux[left++];  }
    		a[k].incrementExchangeCount();    // ... and completed
    	}
    }
    
   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Exchangeable v, Exchangeable w) {
        return v.compareTo(w) < 0;
    }

}