package rtkornitsky.hw1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestDoubleStack {

	// ensure can construct and issue sequence of commands.
	@Test
	public void testBasic() {
		DoubleStack ds = new DoubleStack(5);
		assertFalse(ds.isFull());              // initial state
		assertEquals(0, ds.sizeLeft());
		assertEquals(0, ds.sizeRight());
		
		ds.pushLeft(5);
		ds.pushLeft(3);
		ds.pushRight(7);
		ds.pushRight(2);
		ds.pushLeft(1);

		assertTrue(ds.isFull());               // final state
		assertEquals(3, ds.sizeLeft());
		assertEquals(2, ds.sizeRight());
		
		assertEquals(1, ds.popLeft());
		assertEquals(3, ds.popLeft());
		assertEquals(5, ds.popLeft());
		
		assertEquals(2, ds.popRight());
		assertEquals(7, ds.popRight());
	}
	
	// can't popLeft or popRight on empty DoubleStack
	public void testErrors() {
		DoubleStack ds = new DoubleStack(7);
		try {
			ds.popLeft();
			fail();
		} catch (IllegalStateException ise) {
			
		}
		
		try {
			ds.popRight();
			fail();
		} catch (IllegalStateException ise) {
			
		}
		
		try {
			ds.exchange();
			fail();
		} catch (IllegalStateException ise) {
			
		}
	}
	
	// test exchange fully
	@Test
	public void testExchange() {
		DoubleStack ds = new DoubleStack(7);
		ds.pushLeft(2);
		try { ds.exchange(); fail(); } catch (IllegalStateException ise) { }
		ds.popLeft();
		ds.pushRight(3);
		try { ds.exchange(); fail(); } catch (IllegalStateException ise) { }
		ds.pushLeft(7);
		ds.exchange();
		
		// These have been swapped
		assertEquals(7, ds.popRight());
		assertEquals(3, ds.popLeft());
		
		assertEquals(0, ds.sizeLeft());
		assertEquals(0, ds.sizeRight());		
	}
}
