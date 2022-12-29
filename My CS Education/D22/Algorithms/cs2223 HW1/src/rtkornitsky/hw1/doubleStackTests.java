package rtkornitsky.hw1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class doubleStackTests {
	
	@Test
	public void isFullTests() {
		
		DoubleStack ds = new DoubleStack(4);
		
		assertTrue(!ds.isFull());
		
		ds.pushLeft(0);ds.pushLeft(0);ds.pushLeft(0);ds.pushLeft(0);
		
		assertTrue(ds.isFull());
		
		
		try { ds.pushRight(3); fail(); } catch (IllegalStateException ise) { }
		
		ds.popLeft();
		ds.popLeft();
		
		try { ds.exchange(); fail(); } catch (IllegalStateException ise) { }
		
		try { ds.popRight(); fail(); } catch (IllegalStateException ise) { }
		
		ds.popLeft();
		ds.popLeft();
		
		try { ds.popLeft(); fail(); } catch (IllegalStateException ise) { }
		
		ds.pushLeft(1);ds.pushLeft(2);ds.pushLeft(3);ds.pushLeft(4);
		
		assertEquals(4, ds.sizeLeft());
		assertTrue(ds.isFull());
		
		try { ds.pushLeft(3); fail(); } catch (IllegalStateException ise) { }
		
		assertEquals(0, ds.sizeRight());
		
		ds.popLeft();
		ds.pushRight(2);
		ds.exchange();
		
		assertTrue(ds.isFull());
		assertEquals(1, ds.sizeRight());

		
	}

}
