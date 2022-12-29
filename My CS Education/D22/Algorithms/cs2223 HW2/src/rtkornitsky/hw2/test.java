package rtkornitsky.hw2;

public class test {

	public static void main(String[] args) {
		int Largest = 0;
		System.out.println();

		        //Test: Largest Inside
		        SpecialQueue sq2 = new SpecialQueue();

		        sq2.enqueue(6);
		        sq2.enqueue(3);
		        sq2.enqueue(5);
		        sq2.enqueue(4);
		        sq2.dequeue();

		        Largest = sq2.dequeueLargest();
		        System.out.println("Largest 1: Should be 5 is " + Largest);

		        Largest = sq2.dequeueLargest();
		        System.out.println("Largest 2: Should be 4 is " + Largest);

		        System.out.println("Count:   N Should be 1 is " + sq2.size());
		        System.out.println();

		        //Test2: Largest First
		        SpecialQueue sq3 = new SpecialQueue();

		        sq3.enqueue(6);
		        sq3.enqueue(3);
		        sq3.enqueue(5);
		        sq3.enqueue(4);

		        Largest = sq3.dequeueLargest();
		        System.out.println("Largest 1: Should be 6 is " + Largest);

		        Largest = sq3.dequeueLargest();
		        System.out.println("Largest 2: Should be 5 is " + Largest);

		        System.out.println("Count:   N Should be 2 is " + sq3.size());
		        System.out.println();

		        //Test2: Largest Last
		        SpecialQueue sq4 = new SpecialQueue();

		        sq4.enqueue(6);
		        sq4.enqueue(3);
		        sq4.enqueue(5);
		        sq4.enqueue(4);
		        sq4.enqueue(7);
		        sq4.enqueue(8);

		        Largest = sq4.dequeueLargest();
		        System.out.println("Largest 1: Should be 8 is " + Largest);

		        Largest = sq4.dequeueLargest();
		        System.out.println("Largest 2: Should be 7 is " + Largest);

		        System.out.println("Count:   N Should be 4 is " + sq4.size());
		        System.out.println(); 
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		//Here is the test for swapEndPoints

		SpecialQueue sq5 = new SpecialQueue();

		        sq5.enqueue(6);
		        sq5.enqueue(3);
		        sq5.enqueue(5);
		        sq5.enqueue(4);

		        System.out.println("Before Swap: Should be { 6, 3, 5, 4 } is " + sq5.printPoints());

		        sq5.swapEndPoints();

		        System.out.println("After  Swap: Should be { 4, 3, 5, 6 } is " + sq5.printPoints()); 
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	
}
