package rtkornitsky.hw1;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import algs.days.day03.FixedCapacityStack;

public class TestZeckendorf {

	@Test
	public void testFibStack() {
		FixedCapacityStack<Long> stack = Zeckendorf.makeFibonacciStack(7);
		assertTrue(5 == stack.pop());
		assertTrue(3 == stack.pop());
		assertTrue(2 == stack.pop());
		assertTrue(1 == stack.pop());
		assertTrue(stack.isEmpty());

		stack = Zeckendorf.makeFibonacciStack(8);
		assertTrue(8 == stack.pop());
		assertTrue(5 == stack.pop());
		assertTrue(3 == stack.pop());
		assertTrue(2 == stack.pop());
		assertTrue(1 == stack.pop());
		assertTrue(stack.isEmpty());
	}
}
