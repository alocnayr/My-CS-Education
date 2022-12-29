package rtkornitsky.hw2;

/**
 * This code creates RangeList only using odd numbers (which simplifies the
 * problem greatly). If your RangeList class. If you copy this file into your
 * own project, make sure that there are no extra import statements above, since
 * this is meant to test against YOUR RangeList class.
 * 
 * I created this to give students some ideas as to how to validate that their
 * individual methods are working as expected.
 */
public class PartialValidationRangeList {
	public static void main(String[] args) {
		// create your RangeList rl
		RangeList rl = new RangeList();

		rl.add(11);
		System.out.println("Must be 11: " + rl);
		rl.add(7);
		System.out.println("Must be 7,11: " + rl);
		rl.add(3);
		System.out.println("Must be 3,7,11: " + rl);

		RangeList rl2 = new RangeList();
		rl2.add(5);
		rl2.add(7);
		rl2.add(3);
		rl2.add(15);
		rl2.add(11);
		System.out.println("Must be 5: " + rl2.numberRanges());
		System.out.println("Must be 5: " + rl2.numberValues());
		System.out.println("Must be true: " + rl.subsetOf(rl2));
		System.out.println("Must be false: " + rl2.subsetOf(rl));
		System.out.println("Must be true: " + rl.contains(11));
		System.out.println("Must be false: " + rl.contains(15));

		rl2.remove(15);
		rl2.remove(5);
		System.out.println("Must be true: " + rl.equals(rl2));

		// clean up and reduce both RangeLists so they will become equal after removing
		// lots of values

		rl.add(99);
		rl.remove(11);
		rl.remove(3);
		rl.remove(7);

		rl2.remove(11);
		rl2.add(99);
		rl2.remove(3);
		rl2.remove(7);
		System.out.println("Must be true: " + rl.equals(rl2));

	}
}
