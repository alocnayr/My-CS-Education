package rtkornitsky.hw2;

import edu.princeton.cs.algs4.StopwatchCPU;

public class PerformanceRangeList {
	public static void main(String[] args) {
		System.out.println("N\tFalse\tElapsed\tTrue\tResult");
		for (int N = 1024; N <= 65536; N*= 2) {
			RangeList rl1 = new RangeList();
			RangeList rl2 = new RangeList();
			for (int i = 0; i < N; i++) {
				rl1.add(4*i+1);
				rl2.add(4*i-1);
				rl2.add(4*i);
				rl2.add(4*i+1);
			}
			rl1.add(5*N+1);   // one more in the first one,

			// here is a failure for subsetOf, but only in the final one.
			StopwatchCPU now = new StopwatchCPU();
			boolean resultFail = false;
			for (int i = 0; i < 1000; i++) {
				resultFail = rl1.subsetOf(rl2);
			}
			double elapsedFail = now.elapsedTime();

			// here is a positive situation, where rl1 is a subset of rl2 while rl2 
			// has only a single range node
			rl1 = new RangeList();
			rl2 = new RangeList();
			for (int i =  0; i < N; i++) {
				rl1.add(2*i+1);
			}
			for (int i = 1; i < 3*N; i++) {
				rl2.add(i);
			}
			now = new StopwatchCPU();
			boolean resultTrue = true;
			for (int i = 0; i < 1000; i++) {
				resultTrue = rl1.subsetOf(rl2);
			}
			double elapsedTrue = now.elapsedTime();

			System.out.println(String.format("%d\t%s\t%.4f\t%s\t%.4f", N, resultFail, elapsedFail, resultTrue, elapsedTrue));
		}
	}
}
