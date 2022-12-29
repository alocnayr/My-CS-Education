package rtkornitsky.hw2;

import edu.princeton.cs.algs4.StopwatchCPU;

public class PerformanceSpecialQueue {
	public static void main(String[] args) {
		System.out.println("N\tDeqLargest\tSwap");
		for (int N = 65536; N <= 1045876; N *= 2) {
			SpecialQueue sq = new SpecialQueue();
			
			for (int i = 1; i <= N; i++) {
				sq.enqueue(i);
			}
			
			// run 100 times to be able to get some timing results.
			// You should be able to determine that timing doubles
			// as the problem size doubles
			StopwatchCPU watch = new StopwatchCPU();
			for (int i = 0; i < 100; i++) {
				sq.dequeueLargest();
			}
			double timeLargest = watch.elapsedTime();
			
			// this code is so blazing fast (at O(1)) that I have to
			// run it 10,000,000 times to be able to get the timer 
			// to detect time (since precision is 15.625ms threshold)
			// you should be able to determine that timing is independent
			// of N.
			watch = new StopwatchCPU();
			for (int i = 0; i < 10000000; i++) {
				sq.swapEndPoints();
			}
			double timeSwap = watch.elapsedTime();
			
			System.out.println(String.format("%d\t%.6f\t%.6f", N, timeLargest, timeSwap));
		}
	}
}
