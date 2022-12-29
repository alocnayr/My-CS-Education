package rtkornitsky.hw3;

import algs.days.day10.SeparateChainingHashST;
import algs.days.day11.LinearProbingHashST;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class question4 {

	public static void main(String[] args) {

		System.out.println("N\tAvg.BST\t\t\tAvg.List\t\t\tAvg.OA");
		
		for (int N = 16384; N <= 1048576; N *= 2) {

			StopwatchCPU bstSTclock = new StopwatchCPU();
			
			for (int i = 0; i < 10; i++) {

				BST_SymbolTable aBSTst = new BST_SymbolTable();
				for (int j = 0; j < N / 2; j++) {
					int rand = StdRandom.uniform(N * 8);
					Integer randGet = aBSTst.get(rand);
					if (randGet != null) {
						aBSTst.put(rand, randGet + 1);
					} else {
						aBSTst.put(rand, 1);
					}

				}
			}

			double aBSTstTime = bstSTclock.elapsedTime() / (10.0 * N);

			StopwatchCPU linProbClock = new StopwatchCPU();
			for (int i = 0; i < 10; i++) {
				SeparateChainingHashST<Integer, Integer> aSepChainST = new SeparateChainingHashST<>();
				for (int j = 0; j < N / 2; j++) {
				
					int rand = StdRandom.uniform(N * 8);
					Integer randGet = aSepChainST.get(rand);
					if (randGet != null) {
						aSepChainST.put(rand, randGet + 1);
					} else {
						aSepChainST.put(rand, 1);
					}
				}
			}

			double aListProbSTtime = linProbClock.elapsedTime() / (10.0 * N);

			StopwatchCPU sepChainClock = new StopwatchCPU();
			for (int i = 0; i < 10; i++) {
				LinearProbingHashST<Integer, Integer> aLinProbST = new LinearProbingHashST<>(100000);
				for (int j = 0; j < N / 2; j++) {
					
					int rand = StdRandom.uniform(N * 8);
					Integer randGet = aLinProbST.get(rand);
					if (randGet != null) {
						aLinProbST.put(rand, randGet + 1);
					} else {
						aLinProbST.put(rand, 1);
					}
				}

			}
			double aSepChainTime = sepChainClock.elapsedTime() / (10.0 * N);

			System.out.println(N + "\t" + aBSTstTime + "\t" + aListProbSTtime + "\t" + aSepChainTime);

		}
	}
}
