package rtkornitsky.hw2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class queueTest {

	@Test
	public void enqueueTest() {

		SpecialQueue sq = new SpecialQueue();
		Node node1 = new Node(2);
		Node node2 = new Node(5);

		assertEquals(0, sq.size());
		sq.enqueue(node1.value);
		assertEquals(2, sq.head.value);
		assertEquals(2, sq.tail.value);
		assertEquals(1, sq.size());
		sq.enqueue(node2.value);
		assertEquals(2, sq.head.value);
		assertEquals(5, sq.tail.value);
		assertEquals(2, sq.size());
		sq.dequeue();
		assertEquals(5, sq.head.value);
		assertEquals(5, sq.tail.value);

		sq.swapEndPoints();
		assertEquals(5, sq.head.value);
		assertEquals(5, sq.tail.value);

		sq.enqueue(6);
		assertEquals(6, sq.tail.value);
		assertEquals(5, sq.head.value);

		assertEquals(2, sq.size());
		sq.swapEndPoints();
		assertEquals(5, sq.tail.value);
		assertEquals(6, sq.head.value);

		SpecialQueue q = new SpecialQueue();
		Node node3 = new Node(2);
		Node node4 = new Node(5);

		q.enqueue(node3.value);
		q.enqueue(node4.value);
		q.enqueue(10);
		q.enqueue(10);
		q.enqueue(6);
		q.enqueue(0);
		q.enqueue(11);
		assertEquals(7, q.size());
		assertEquals(11, q.dequeueLargest());

	}

	@Test
	public void test2() {

		SpecialQueue sq = new SpecialQueue();

		assertTrue(sq.isEmpty());

		sq.enqueue(5);
		sq.enqueue(6);
		sq.enqueue(7);
		sq.enqueue(0);

		assertEquals(4, sq.size());

		sq.enqueue(10);

		assertEquals(5, sq.size());

		assertTrue(!sq.isEmpty());

		assertEquals(10, sq.tail.value);
		assertEquals(5, sq.head.value);

		sq.swapEndPoints();
		assertEquals(5, sq.tail.value);
		assertEquals(10, sq.head.value);

		assertEquals(10, sq.dequeueLargest());

		sq.dequeue();
		sq.dequeue();
		sq.dequeue();

		assertEquals(1, sq.size);

	}

	@Test
	public void dequeueLargestTests() {

		SpecialQueue sq = new SpecialQueue();

		assertEquals(0, sq.size());

		sq.enqueue(5);
		assertEquals(1, sq.size());

		assertEquals(5, sq.dequeueLargest());
		assertEquals(0, sq.size());

		sq.enqueue(5);
		sq.enqueue(7);
		sq.enqueue(1);

		assertEquals(3, sq.size());
		assertEquals(7, sq.dequeueLargest());
		assertEquals(2, sq.size());
		assertEquals(5, sq.dequeueLargest());
		assertEquals(1, sq.dequeueLargest());

		sq.enqueue(1);
		sq.enqueue(3);
		sq.enqueue(2);
		sq.enqueue(3);

		assertEquals(4, sq.size());

		sq.dequeue();

		assertEquals(3, sq.size());

		assertEquals(3, sq.head.value);
		assertEquals(3, sq.tail.value);

		assertEquals(3, sq.dequeue());

		assertEquals(2, sq.size());
		assertEquals(2, sq.head.value);
		assertEquals(3, sq.tail.value);

		assertEquals(2, sq.dequeue());

		assertEquals(1, sq.size());
		assertEquals(3, sq.head.value);
		assertEquals(3, sq.tail.value);

		assertEquals(3, sq.dequeue());

		assertEquals(0, sq.size());

	}

	@Test
	public void dqTest() {

		SpecialQueue sq = new SpecialQueue();
		try {
			sq.dequeue();
			fail();
		} catch (RuntimeException ise) {
		}

		sq.enqueue(3);

		assertEquals(1, sq.size());
		assertEquals(3, sq.head.value);
		assertEquals(3, sq.tail.value);

		sq.enqueue(4);
		assertEquals(2, sq.size());
		assertEquals(3, sq.head.value);
		assertEquals(4, sq.tail.value);

		sq.enqueue(5);

		System.out.println(sq.head.next.value);
		System.out.println(sq.tail.value);

		//sq.dequeue();
		assertEquals(3, sq.size());
		assertEquals(3, sq.head.value);
		assertEquals(5, sq.tail.value);

		sq.dequeue();
		assertEquals(2, sq.size());
		assertEquals(4, sq.head.value);
		assertEquals(5, sq.tail.value);

		sq.dequeue();
		assertEquals(1, sq.size());
		assertEquals(5, sq.head.value);
		assertEquals(5, sq.tail.value);

		sq.dequeue();
		assertEquals(0, sq.size());

	}
	
	@Test
	public void asd() {
		
		SpecialQueue sq = new SpecialQueue();
		
		sq.enqueue(3);
		assertEquals(1, sq.size());
		assertEquals(3, sq.head.value);
		assertEquals(3, sq.tail.value);
		
		sq.dequeue();
		assertEquals(0, sq.size());
		assertNull(sq.head);
		
		sq.enqueue(0);
		sq.enqueue(0);
		assertEquals(2, sq.size());
		sq.enqueue(2);
		sq.enqueue(10);
		
		assertEquals(0, sq.head.value);
		assertEquals(10, sq.tail.value);
		
		sq.dequeue();
		assertEquals(0, sq.head.value);
		assertEquals(10, sq.tail.value);
		
		sq.dequeue();
		assertEquals(2, sq.head.value);
		assertEquals(10, sq.tail.value);
		
		sq.dequeue();
		assertEquals(10, sq.head.value);
		assertEquals(10, sq.tail.value);
		
		sq.dequeue();
		assertNull(sq.head);
		
		sq.enqueue(1);
		sq.enqueue(1);
		sq.enqueue(1);
		sq.enqueue(1);
		assertEquals(1, sq.head.value);
		assertEquals(1, sq.tail.value);
		
		sq.swapEndPoints();
		assertEquals(1, sq.head.value);
		assertEquals(1, sq.tail.value);
		
		SpecialQueue a =  new SpecialQueue();
		
		a.enqueue(2);
		a.enqueue(3);
		a.enqueue(4);
		
		//2, 3, 4
		assertEquals(2, a.head.value);
		assertEquals(4, a.tail.value);
		
		a.swapEndPoints();
		//4, 3, 2, 6

		assertEquals(4, a.head.value);
		assertEquals(2, a.tail.value);
		
		a.enqueue(6);
		assertEquals(4, a.head.value);
		assertEquals(6, a.tail.value);
		
		a.enqueue(7);
		assertEquals(4, a.head.value);
		assertEquals(7, a.tail.value);
		
		a.dequeue();
		assertEquals(3, a.head.value);
		assertEquals(7, a.tail.value);
		
		
		
	}

}
