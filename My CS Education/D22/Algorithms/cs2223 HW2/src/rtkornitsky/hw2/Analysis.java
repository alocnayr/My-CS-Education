package rtkornitsky.hw2;

/**
 * This is a more complicated version of a question you might see on the midterm.
 * 
 * Currently this code produces the following output:
  
     N          Value          Actual       Predicted
     2	             3	       0	       0
     4	            56	       0	       0
     8	           676	       0	       0
    16	          6864	       0	       0
    32	         64176	       0	       0
    64	        574848	       0	       0
   128	       5028672	       0	       0
   256	      43382528	       0	       0
   512	     370994944	       0	       0
  1024	    3153715200	       0	       0
  2048	   26690700288	       0	       0
  4096	  225104596992	       0	       0
  8192	 1892999442432	       0	       0
 16384	15879284621312	       0	       0


Your task is to modify the program so it produces the following

    N          Val      Actual   Predicted
     2	           0      c2      S(2)
     4	           1      c4      S(4)
     8	          11      c8      S(8)
    16	          70      ...     ...
    32	         354      ...     ...
    64	        1599      ...     ...
   128	        6813      ...     ...
   256	       28156      ...     ...
   512	      114524      ...     ...
  1024	      462013      ...     ...
  2048	     1856031      ...     ...
  4096	     7440258      ...     ...
  
 Where each of the c2, c4, c8, ... is replaced by an integer that reflects the actual number of times 
 that power() was invoked during proc(a, 0, a.length-1) for arrays, a, of size 2, 4, 8, 16, ...
 The final Predicted column reflects the prediction of S(N), which you should implement below
 in the model(n) method.

 Note for the bonus question: add a fifth column that shows your prediction of Val. Implement your 
 formula in the bonusModel(int n) method.

 */
public class Analysis {
	
	static int powerCounter = 0;

	/** 
	 * Helper method to compute base^exp as a long.
	 */
	static long power(int base, int exp) {
		powerCounter++;
		return (long) Math.pow(base, exp);
	}
	
	/**
	 * This is the method under review. Do not change this method.
	 */
	public static long proc(int[] A, int lo, int hi) {
		long v = power(A[lo], 2);
		if (lo == hi) { 
			return v + power(A[hi],2);
		}

		int m = (lo + hi) / 2;
		long total = proc(A, lo, m) + proc(A, m+1, hi);
		while (hi > lo) {
			total += power(A[hi], 2);
			lo += 2;
		}
		
		return total;
	}
	
	/**
	 * Complete your analysis of the code and modify this function to return the prediction
	 * of how many times Math.power() is called for an initial problem of size n.
	 */
	static long model(int n) {
		return (long)((Math.log(n)/Math.log(2)) * (n/2) + (3*n) - 1);    // FILL IN YOUR MODEL FORMULA
	}
	
	/**
	 * Bonus question. Can you come up with a (more complicated) formula that predicts
	 * the value, Value, or proc(a,0,n-1) when a is composed of the integers from 0 to n-1. 
	 */
	static long bonusModel(int n) {
		return 0;   // FILL IN WITH YOUR BONUS MODEL FORMULA
	}
	
	/** 
	 * Launch the experiment.
	 * 
	 * You will need to make some changes to this method
	 */
	public static void main(String[] args) {
		System.out.println("     N          Value          Actual       Predicted");
		for (int n = 2; n <= 16384; n *= 2) {
			
			int[] a = new int[n];
			for (int i = 0; i < n; i++) { a[i] = i; }
			powerCounter = 0;
			// initiate the request on an array of size n, containing values from 0 to n-1
			// using indices of lo=0 and hi=n-1
			long val = proc(a, 0, n-1);
			
			
			// WILL NEED TO FIX BELOW
			System.out.println(String.format("%6d\t%14d\t%8d\t%8d", n, val, powerCounter, model(n)));
		}
	}
}
