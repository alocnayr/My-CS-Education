package rtkornitsky.hw2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class testCasesRangeList {

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

		// System.out.println(empty.toString());

		assertEquals(9, empty.size);

		empty.remove(10);
		assertEquals(3, empty.numberRanges());
		assertEquals(8, empty.numberValues());
		// System.out.println(empty.toString());
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
		// System.out.println(empty.toString());

		empty.remove(3);

		assertEquals(5, empty.size);

	}

	@Test
	public void a() {

		RangeList rl = new RangeList();

		rl.add(1);

		rl.add(3);
		rl.add(4);

		rl.add(6);

		// assertEquals(4, rl.size);

		// rl.remove(6);
		// assertTrue(!rl.contains(6));

	}

	@Test
	public void abc() {

		RangeList rl = new RangeList();
		RangeList rl2 = new RangeList();

		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));

		rl.add(0);
		rl2.add(0);

		// [0,0] -> [2,2]

		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));

		rl.add(2);
		assertTrue(!rl.equals(rl2));
		assertTrue(!rl2.equals(rl));
		rl2.add(2);
		assertTrue(rl.equals(rl2));
		assertTrue(rl2.equals(rl));

		rl.remove(2);
		assertTrue(!rl.equals(rl2));
		assertTrue(!rl2.equals(rl));

		// rl2.remove(2);
		// assertTrue(rl.equals(rl2));

	}

	@Test
	public void subsetTests2() {

		RangeList rl = new RangeList();
		RangeList rl2 = new RangeList();

		assertTrue(rl.subsetOf(rl2));
		assertTrue(rl2.subsetOf(rl));

		rl.add(2);

		rl2.add(1);
		rl2.add(2);
		rl2.add(3);
		assertTrue(rl.subsetOf(rl2));
		rl.add(4);
		assertTrue(!rl.subsetOf(rl2));

		RangeList a = new RangeList();
		RangeList b = new RangeList();

		a.add(1);
		a.add(3);
		a.add(5);
		b.add(1);
		b.add(3);
		b.add(5);

		assertTrue(a.equals(b));
		assertTrue(b.equals(a));

		assertTrue(a.subsetOf(b));
		assertTrue(b.subsetOf(a));

		b.remove(3);
		assertTrue(!b.contains(3));
		assertTrue(!a.equals(b));
		assertTrue(!a.subsetOf(b));

		assertTrue(b.head.high == 1);
		assertTrue(b.head.next.high == 5);
		assertTrue(b.head.next.low == 5);

		b.remove(1);
		assertTrue(!b.contains(1));
		assertTrue(b.head.low == 5);
		assertTrue(b.head.high == 5);

		b.remove(5);
		assertTrue(!b.contains(5));
		assertNull(b.head);

		b.add(1);
		b.add(3);
		b.add(5);
		assertTrue(b.head.high == 1);
		assertTrue(b.head.next.high == 3);
		assertTrue(b.head.next.next.low == 5);

		b.remove(5);
		assertTrue(b.head.high == 1);
		assertTrue(b.head.next.high == 3);
		assertTrue(!b.contains(5));
		assertNull(b.head.next.next);

	}

	@Test
	public void toStringTests() {

		RangeList rl = new RangeList();

		rl.add(0);

		rl.add(4);
		rl.add(5);
		rl.add(6);

		rl.add(10);

		rl.add(12);
		rl.add(13);

		rl.add(15);
		rl.add(14);

		assertEquals(0, rl.head.low);
		rl.remove(14);
		rl.remove(15);
		assertTrue(!rl.contains(15));
		rl.remove(0);

		//System.out.println(rl.toString());

	}
	
	@Test
	public void moreTestCasesLetsGo() {
		
		RangeList list = new RangeList();
		RangeList anotherList = new RangeList();
		
		System.out.println(list.toString());
		
		list.add(5);
		list.add(6);
		anotherList.add(5);
		System.out.println(list.toString());
		assertTrue(anotherList.subsetOf(list));
		list.remove(6);
		assertTrue(anotherList.subsetOf(list));
		assertTrue(list.subsetOf(anotherList));
		System.out.println(list.toString());
		list.remove(5);
		assertTrue(!anotherList.subsetOf(list));
		assertTrue(list.subsetOf(anotherList));
		System.out.println(list.toString());
		
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		
		list.add(100);
		list.add(54);
		list.add(4);
		System.out.println(list.toString());
		list.remove(8);
		System.out.println(list.toString());
		list.remove(6);
		System.out.println(list.toString());
		list.remove(100);
		System.out.println(list.toString());
		list.remove(54);
		System.out.println(list.toString());
		list.remove(7);
		System.out.println(list.toString());
		list.remove(4);
		System.out.println(list.toString());
		list.add(8);
		list.add(10);
		list.remove(9);
		list.remove(10);
		System.out.println(list.toString());
		
		
		
	}

}
