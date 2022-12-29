package rtkornitsky.hw3;
// DO NOT CHANGE THE CONTENTS OF THIS CLASS...

import edu.princeton.cs.algs4.StdRandom;

/**
 * Purpose of this object is to record a value that can be sorted, but also record how many times
 * the individual element was exchanged with another during a Comparison-based sort.
 */
public class Exchangeable implements Comparable<Exchangeable> {

	final Integer value;    // Value stored by this integer
	int count;              // how many times it was exchanged.
	
	public Exchangeable(int v) {
		value = v;
	}

	/** Delegate compareTo to our inner value. */
	public int compareTo(Exchangeable other) {
		return value.compareTo(other.value);
	}
	
	/** Determine if two ExchangeableInteger objects are equal based on their value. */
	public boolean equals(Object o) {
		if (o == null) { return false; }
		if (o instanceof Exchangeable) {
			Exchangeable other = (Exchangeable) o;
			return value.equals(other.value);
		}
		return false;
	}
	
	/**Record that element was exchanged. */
	public void incrementExchangeCount() { count++;	}
	
	/** Return # of times element was exchanged. */
	public int getExchangeCount() { return count; }
	
	/** Helper method to generate initial array of N random values between [0 and 16777216). */
	public static Exchangeable[] create (int N) {
		Exchangeable[] A = new Exchangeable[N];
		for (int i = 0; i < N; i++) {
			A[i] = new Exchangeable(StdRandom.uniform(16777216));
		}
		return A;
	}
}
