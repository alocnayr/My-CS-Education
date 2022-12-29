package rtkornitsky.hw4;

import algs.days.day10.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdRandom;

/** Copy to your USERID.hw4 package and make changes. */
public class Counting {
	
	/**
	 * This skeletal structure needs to be modified to solve this problem. Feel free to add code anywhere...
	 */
	public static void main(String[] args) {
		System.out.println("N\tMaxAVHt\tMaxAVDf\tMaxAVZr\tAVZero%\tMaxRBHt\tMaxRBDf\tMaxRBZr\tRBZero%");
		for (int N = 32; N <= 262144; N*= 2) {
			
			int NUMTRIAL= 100;
			int MaxAVHt = 0;
			int MaxAVDf = 0;
			int MaxAVZr = 0;
			int MaxRBHt = 0;
			int MaxRBDf = 0;
			int MaxRBZr = 0;
			
			for (int T = 0; T < NUMTRIAL; T++) {
				
				// This constructs the array of N-1 values (where N is a power of 2) and 
				// it uses StdRandom.setSeed() to ensure all students will get the same result
				int[] vals = new int[N-1];
				for (int i = 0; i < N-1; i++) {
					vals[i] = i+1;
				}
				StdRandom.setSeed(T);
				StdRandom.shuffle(vals);
				
				// note: Insert the integers in vals into a new AVL or RedBlack Tree in order from left to right
				
				AVL<Integer> aAVL = new AVL<Integer>();
				RedBlackBST<Integer, Integer> aRBT = new RedBlackBST<>();
				
				for(int v : vals) {
					aAVL.insert(v);
					aRBT.put(v,v);
				}
				
				edu.princeton.cs.algs4.SeparateChainingHashST<Integer, Integer> sepChainHashAVLtree = aAVL.counts();
				
				int MaxAVHt_current = aAVL.height();
				int MaxAVDf_current = aAVL.height() - aAVL.minDepth();
				int MaxAVZr_current = sepChainHashAVLtree.get(0);
				
				if(MaxAVHt < MaxAVHt_current) {
					MaxAVHt = MaxAVHt_current;
				}
				if(MaxAVDf < MaxAVDf_current) {
					MaxAVDf = MaxAVDf_current;
				}
				if(MaxAVZr < MaxAVZr_current) {
					MaxAVZr = MaxAVZr_current;
				}
				
				edu.princeton.cs.algs4.SeparateChainingHashST<Integer, Integer> sepChainHashRBtree = aRBT.counts();
				
				int MaxRBHt_current = aRBT.height();
				int MaxRBDf_current = aRBT.height() - aRBT.minDepth();
				int MaxRBZr_current = sepChainHashRBtree.get(0);
				
				if(MaxRBHt < MaxRBHt_current) {
					MaxRBHt = MaxRBHt_current;
				}
				if(MaxRBDf < MaxRBDf_current) {
					MaxRBDf = MaxRBDf_current;
				}
				if(MaxRBZr < MaxRBZr_current) {
					MaxRBZr = MaxRBZr_current;
				}
			}
				
				double AVZero = ((double)MaxAVZr / (double)(N-1))*100;
				double RBZero = ((double)MaxRBZr / (double)(N-1))*100;

				
				System.out.format("%d\t%d\t%d\t%d\t%.2f\t%d\t%d\t%d\t%.2f\n", N-1, MaxAVHt, MaxAVDf, MaxAVZr, AVZero,
						MaxRBHt, MaxRBDf, MaxRBZr, RBZero);
			}
		}
	}


