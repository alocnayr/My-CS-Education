package rtkornitsky.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import algs.hw1.fixed.OrderedArray;

public class TestOrderedArray {

	@Test
	public void testOrderedInts () {
		Integer[] vals = new Integer[] { 1, 2, 3};
		OrderedArray<Integer> oa = new OrderedArray<Integer>(vals, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		
		assertEquals (3, oa.length());
		assertEquals(0, oa.getNumInspections());
		assertEquals(new Integer(1), oa.get(0));
		assertEquals(new Integer(2), oa.get(1));
		assertEquals(2, oa.getNumInspections());   // two inspections
	}
}
