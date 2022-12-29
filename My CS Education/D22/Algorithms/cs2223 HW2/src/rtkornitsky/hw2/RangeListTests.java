package rtkornitsky.hw2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RangeListTests {

	@Test
	public void tests() {

		RangeList aList = new RangeList();
		aList.add(4);
		aList.add(2);
		aList.add(5);
		aList.add(0);

		assertEquals(5, aList.head.next.next.high);

		aList.add(10);
		aList.remove(4);

		assertFalse(aList.contains(4));

		assertEquals(4, aList.size);

		RangeList list2 = new RangeList();

		list2.add(0);
		list2.add(0);
		list2.add(0);
		list2.add(0);
		list2.add(0);
		assertTrue(list2.contains(0));
		assertFalse(list2.contains(1));
		assertEquals(1, list2.size);

		RangeList list3 = new RangeList();

		assertEquals(0, list3.size);
		assertFalse(list3.contains(6));
		assertFalse(list3.contains(0));

		// assertTrue(aList.equals(list2));

		// assertEquals(2, aList.head.low);

		// assertEquals(1, aList.size);
		// assertTrue(aList.contains(2));
		// assertTrue(!aList.contains(0));

	}

	@Test
	public void subsetTests() {

		RangeList bigList = new RangeList();

		for (int i = 0; i < 20; i++) {
			bigList.add(i);
		}
		assertEquals(20, bigList.size);
		for (int i = 0; i < 20; i++) {
			assertTrue(bigList.contains(i));
		}

		RangeList subset = new RangeList();

		for (int i = 0; i < 5; i++) {
			subset.add(i);
		}
		for (int i = 0; i < 5; i++) {
			assertTrue(subset.contains(i));
		}

		assertTrue(subset.subsetOf(bigList));
		assertTrue(subset.subsetOf(subset));

		assertEquals(1, bigList.numberRanges());

	}

	@Test
	public void numberRangesAndValuesTest() {

		RangeList holes = new RangeList();

		for (int i = 0; i < 20; i = 2 + i) {
			holes.add(i);
		}

		assertEquals(10, holes.numberRanges());
		assertEquals(10, holes.numberValues());

		RangeList oneGiantList = new RangeList();

		for (int i = 0; i < 20; i++) {
			oneGiantList.add(i);
		}
		assertEquals(1, oneGiantList.numberRanges());
		assertEquals(20, oneGiantList.numberValues());

		assertTrue(oneGiantList.contains(19));

		assertFalse(oneGiantList.remove(20));
		assertTrue(oneGiantList.remove(19));
		assertFalse(oneGiantList.contains(19));

		RangeList empty = new RangeList();

		assertEquals(0, empty.numberRanges());
		assertEquals(0, empty.numberValues());

		empty.add(2);
		empty.add(3);
		empty.add(4);

		empty.add(6);
		empty.add(7);
		empty.add(8);

		empty.add(0);

		empty.add(9);
		empty.add(10);

		assertEquals(3, empty.numberRanges());
		assertEquals(9, empty.numberValues());

		System.out.println(empty.toString());

		assertEquals(9, empty.size);

		empty.remove(10);
		assertEquals(3, empty.numberRanges());
		assertEquals(8, empty.numberValues());
		System.out.println(empty.toString());
		assertEquals(8, empty.size);

		empty.remove(7);
		assertEquals(7, empty.size);
		assertEquals(0, empty.head.high);
		assertEquals(2, empty.head.next.low);
		assertEquals(4, empty.head.next.high);
		assertEquals(6, empty.head.next.next.high);
		assertEquals(4, empty.numberRanges());
		assertFalse(empty.contains(7));

	    assertEquals(4, empty.numberRanges());
		empty.remove(8);
		empty.add(5);
		empty.remove(5);
		System.out.println(empty.toString());

		empty.remove(3);

		assertEquals(5, empty.size);

	}

	@Test
	public void equalsTests() {

		RangeList test1 = new RangeList();
		RangeList test2 = new RangeList();

		assertTrue(test1.equals(test2));
		assertTrue(test2.equals(test2));
		assertTrue(test2.equals(test1));

		test1.add(0);
		test2.add(0);

		assertTrue(test1.equals(test2));

		test1.add(2);
		test2.add(2);

		assertTrue(test1.equals(test2));
		
		test1.remove(2);
		assertTrue(!test1.contains(2));
		assertTrue(!test1.equals(test2));
		
		test1.remove(0);
		
		assertTrue(!test1.equals(test2));

	}

	@Test
	public void a() {

		RangeList empty = new RangeList();
		empty.add(2);
		empty.add(3);
		empty.add(4);

		empty.add(6);
		empty.add(7);
		empty.add(8);

		empty.add(0);

		empty.add(10);

		assertEquals(4, empty.numberRanges());
	}

	@Test
	public void removeTest() {

		RangeList aList = new RangeList();

		aList.add(0);
		aList.add(1);
		aList.add(2);

		assertTrue(aList.contains(2));
		assertEquals(3, aList.size);
		assertEquals(0, aList.head.low);
		assertEquals(2, aList.head.high);

		aList.remove(0);
		assertEquals(1, aList.head.low);
		assertEquals(2, aList.head.high);

		aList.remove(2);
		assertEquals(aList.head.low, aList.head.high);
		assertTrue(!aList.contains(2) && !aList.contains(0));

		aList.remove(1);
		assertEquals(0, aList.size);
		assertFalse(aList.contains(1));

		aList.add(0);
		aList.add(1);
		aList.add(2);

		assertTrue(aList.remove(1));
		assertTrue(!aList.contains(1));

	}

	@Test
	public void testEquals() {

		RangeList rl = new RangeList();
		RangeList rl2 = new RangeList();

		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));

		rl.add(2);
		rl2.add(2);
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));
		
		rl2.add(3);
		
		assertTrue(!rl.equals(rl2));

		rl.remove(2);

		assertTrue(!rl.equals(rl2));
		assertTrue(!rl2.equals(rl));

	}
	
	@Test
	public void testRemove() {
		
		RangeList tr = new RangeList();
		
		tr.add(1);
		tr.add(2);
		
		tr.add(5);
		
		tr.add(8);
		tr.add(9);
		tr.add(10);
		
		assertEquals(6, tr.size);
		
		tr.remove(5);
		
		assertEquals(5, tr.size);
		
		assertTrue(!tr.contains(5));
		
		tr.add(4);
		tr.add(12);
		tr.add(14);
		
		assertEquals(8, tr.size);
		
		tr.remove(12);
		assertEquals(7, tr.size);
		assertTrue(!tr.contains(12));
		assertEquals(2, tr.head.high);
		assertEquals(4, tr.head.next.high);
		assertEquals(10, tr.head.next.next.high);
		assertEquals(14, tr.head.next.next.next.high);
		
		tr.remove(14);
		assertTrue(!tr.contains(14));
		assertEquals(6, tr.size);
		assertEquals(2, tr.head.high);
		assertEquals(4, tr.head.next.high);
		assertEquals(10, tr.head.next.next.high);
		
		assertFalse(tr.remove(1000));
		tr.remove(4);
		tr.remove(8);
		tr.remove(9);
		tr.remove(2);
		tr.remove(10);
	    assertEquals(1, tr.size);
		tr.remove(1);
		assertEquals(0, tr.size);
		
		RangeList oneElt = new RangeList();
		
		oneElt.add(1);
		assertEquals(1, oneElt.size);
		assertTrue(oneElt.contains(1));
		oneElt.remove(1);
		assertFalse(oneElt.contains(1));
		assertEquals(0, oneElt.size);
		
		
		RangeList twoElt = new RangeList();
		
		twoElt.add(1);
		
		twoElt.add(3);
		
		assertTrue(twoElt.contains(1));
		assertTrue(twoElt.contains(3));
		assertEquals(2, twoElt.size);
		
		twoElt.remove(3);
		assertTrue(!twoElt.contains(3));
		assertEquals(1, twoElt.size);
		twoElt.remove(1);
		assertTrue(!twoElt.contains(1));
		assertEquals(0, twoElt.size);
		
		assertFalse(twoElt.remove(2));
		
		RangeList rl = new RangeList();
		RangeList rl2 = new RangeList();
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));
		rl.add(0);
		rl2.add(0);
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));
		rl.remove(0);
		rl2.remove(0);
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));
		for(int i=0; i<20; i++) {
			rl.add(i);
			rl2.add(i);
		}
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));
		
		rl.remove(15);
		assertTrue(!rl.equals(rl2));
		assertTrue(!rl2.equals(rl));
		
		rl2.remove(15);
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));
		rl.remove(19);
		rl.remove(18);
		rl.remove(17);
		rl.remove(16);
		rl2.remove(19);
		rl2.remove(18);
		rl2.remove(17);
		rl2.remove(16);
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));
		assertEquals(15, rl.numberValues());
		assertEquals(15, rl2.numberValues());
		assertEquals(1, rl.numberRanges());
		assertEquals(1, rl2.numberRanges());
		
		rl.add(200);
		rl2.add(200);
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));
		assertEquals(16, rl.numberValues());
		assertEquals(16, rl2.numberValues());
		assertEquals(2, rl.numberRanges());
		assertEquals(2, rl2.numberRanges());
		assertTrue(rl.subsetOf(rl2));
		assertTrue(rl2.subsetOf(rl));
		System.out.println(rl.toString());
		System.out.println(rl2.toString());
		
	}

}
