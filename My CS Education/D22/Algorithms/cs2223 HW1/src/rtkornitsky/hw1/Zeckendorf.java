package rtkornitsky.hw1;

import algs.days.day03.FixedCapacityStack;

/**
 * Use a Stack to compute the Zeckendorf representation for a number.
 * 
 * The Zeckendorf representation for 1,000,000 is
 * "10001010000000000010100000000" | | | | | | | | | 55 | | | | | | | 144 | |
 * 46368 | | | 121393 | 832040
 *
 * 1,000,000 = 832,040 + 121393 + 46368 + 144 + 55
 *
 * left-most digit is the most significant and the right-most is the least
 * significant.
 */
public class Zeckendorf {

	/**
	 * Return a Stack of integers containing (in reverse order) the Fibonacci
	 * numbers from 1 up to and including n.
	 * 
	 * Calling makeFibonacciStack(12) would return a stack with the following
	 * integers: 1, 2, 3, 5, 8
	 * 
	 * Note: while the first two Fibonacci numbers F0=0 and F1=1, the numbers that
	 * appear on the stack refer to F2 and higher, to avoid confusion.
	 */
	public static FixedCapacityStack<Long> makeFibonacciStack(long n) {

		FixedCapacityStack<Long> stack = new FixedCapacityStack<Long>(100);
		
		if(n==1) {
			stack.push(1L);
		}
		else {
		long n_minus_two = 1;
		long n_minus_one = 2;
		stack.push(n_minus_two);
		stack.push(n_minus_one);

		long next = n_minus_one + n_minus_two;
		while (next <= n) {
			stack.push(next);
			n_minus_two = n_minus_one;
			n_minus_one = next;

			next = n_minus_one + n_minus_two;
		}
		}

		return stack;
	}

	/**
	 * Compute the Zeckendorf representation for n, that is, the unique sequence of
	 * sums of Fibonacci numbers, where none are duplicated.
	 * 
	 * The first 7 Fibonacci numbers that make up the representation are: 1, 2, 3,
	 * 5, 8, 13, 21
	 * 
	 * The Zeckendorf representation for the numbers from 1 to 10 are:
	 * 
	 * 1 = 1 = 1 2 = 10 = 2 3 = 100 = 3 4 = 101 = 3 + 1 5 = 1000 = 5 6 = 1001 = 5 +
	 * 1 7 = 1010 = 5 + 2 8 = 10000 = 8 9 = 10001 = 8 + 1 10 = 10010 = 8 + 2 ...
	 * 
	 * This function should use makeFibonacciStack() to construct a stack containing
	 * (in reverse order) the Fibonacci numbers from F2 to Fn where Fn is the
	 * largest Fibonacci number smaller than or equal to n.
	 */
	public static String zeckendorfRepresentation(long n) {
		
		FixedCapacityStack<Long> stack = makeFibonacciStack(n);

		String zeckendorf = "";
		while (n >= 0 && !stack.isEmpty()) {
			long largest = stack.pop();
			if (largest <= n) {
				n = n - largest;
				zeckendorf = zeckendorf.concat("1");
			}
			else {
			zeckendorf = zeckendorf.concat("0");
			}
		}
		return zeckendorf;
	}

	/** No need to modify below this line. */
	public static void main(String[] args) {
		System.out.println("N\tZeck. Repr.");
		for (int i = 1; i <= 20; i++) {
			System.out.println(i + "\t" + zeckendorfRepresentation(i));

		}
	}
}
