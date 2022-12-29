package rtkornitsky.hw3;

public class SortTrials {

	public static void main(String[] args) {
		System.out.println("N\tSelect\tQuick\tHeap\tMerge\tInsert");

		for (int N = 128; N <= 16384; N *= 2) {

			int ms = 0;
			for (int i = 0; i < 100; i++) {
				Exchangeable[] msArray = Exchangeable.create(N);

				Merge.sort(msArray);

				int numberExchanges = 0;
				for (int j = 0; j < N; j++) {
					numberExchanges += msArray[j].getExchangeCount();
				}
				numberExchanges = numberExchanges / 2;
				if (numberExchanges > ms) {
					ms = numberExchanges;
				}
			}

			int qs = 0;
			for (int i = 0; i < 100; i++) {
				Exchangeable[] qsArray = Exchangeable.create(N);

				Quick.sort(qsArray);

				int numberExchanges = 0;
				for (int j = 0; j < N; j++) {
					numberExchanges += qsArray[j].getExchangeCount();
				}
				numberExchanges = numberExchanges / 2;
				if (numberExchanges > qs) {
					qs = numberExchanges;
				} else {

				}

			}
			int ss = 0;
			for (int i = 0; i < 100; i++) {
				Exchangeable[] ssArray = Exchangeable.create(N);

				Selection.sort(ssArray);

				int numberExchanges = 0;
				for (int j = 0; j < N; j++) {
					numberExchanges += ssArray[j].getExchangeCount();
				}
				numberExchanges = numberExchanges / 2;
				if (numberExchanges > ss) {
					ss = numberExchanges;
				} else { // put an else in here
				}
			}

			int hs = 0;
			for (int i = 0; i < 100; i++) {
				Exchangeable[] hsArray = Exchangeable.create(N);

				HeapSort.sort(hsArray);

				int numberExchanges = 0;
				for (int j = 0; j < N; j++) {
					numberExchanges += hsArray[j].getExchangeCount();
				}
				numberExchanges = numberExchanges / 2;
				if (numberExchanges > hs) {
					hs = numberExchanges;
				}
			}
			//int ceilingExch;
			int is = 0;
			if(N<4096) {
				for (int i = 0; i < 100; i++) {
					Exchangeable[] isArray = Exchangeable.create(N);
					Insertion.sort(isArray);
					int numberExchanges = 0;
					for (int j = 0; j < N; j++) {
						numberExchanges += isArray[j].getExchangeCount();
					}
					numberExchanges = numberExchanges/2;
					if (numberExchanges > is) {
						is = numberExchanges;
					}
				}
			}

			if (N >= 4096) {
				System.out.println(N + "\t" + ss + "\t" + qs + "\t" + hs + "\t"
						+ ms + "\t" + "*");
			} else {
				System.out.println(N + "\t" + ss + "\t" + qs + "\t" + hs + "\t"
						+ ms + "\t" + is);
		
			}
		}
	}
}
