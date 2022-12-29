package rtkornitsky.hw2;

/**
 * A Queue that offers some special methods
 */
public class SpecialQueue {

	int size;
	Node head;
	Node tail;
	Node secondToLast;

	/**
	 * Linked list node to use. You must ensure that the value attribute is final.
	 */
	class Node {

		final int value;
		Node next;

		Node(int val) {
			this.value = val;
		}

	}

	/**
	 * Enqueue val at end of the queue. Performance must be O(1)
	 */

	public void enqueue(int val) {
		size++;
		if (head == null) { // if the queue is empty
			head = new Node(val);
			tail = head;
		} else {
			tail.next = new Node(val); // next addition to the queue gets put at the end
			secondToLast = tail; // secondToLast is the old tail
			tail = tail.next;
		}
	}

	/**
	 * Dequeue and return the first value in the queue. Performance must be O(1)
	 */

	public int dequeue() {
		
		Node n = head;
		Node prev = null;
		if (head != null && tail != null) {
			prev = n;
			n = n.next;
			head=head.next;
			if (head == tail) {
				secondToLast = null;
				size--;
				return prev.value;
			}
			if(head == null && tail == null) {
				size--;
				throw new RuntimeException("Queue is empty");
			}
		
		} else {
			throw new RuntimeException("Queue is empty");
		}
		size--;
		return prev.value;
	}

	/**
	 * Remove from the queue and return the largest value contained.
	 * 
	 * If there are multiple occurrences of the maximum value in the queue, then the
	 * closest one to the front is the one that is removed.
	 * 
	 * If queue is empty a RuntimeException is thrown.
	 * 
	 * Performance is O(N) where N is the number of values in the queue.
	 */
	public int dequeueLargest() {
		Node n = head;
		int largest = head.value;
		while (n != null) {
			if (largest <= n.value) {
				largest = n.value;
			}
			n = n.next;
		}
		n = head;
		Node prev = null;

		while (n != null) {
			if (n.value == largest) {
				if (prev == null) {
					head = head.next;
				} else {
					prev.next = n.next;
				}
				size--;
				return largest;
			}
			prev = n;
			n = n.next;
		}
		throw new RuntimeException("error");
	}

	/**
	 * Determine if queue is empty. Performance must be O(1)
	 */

	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Determine the number of values in the queue. Performance must be O(1)
	 */
	public int size() {
		if (size < 0) {
			throw new RuntimeException("Queue is empty");
		} else {
			return size;
		}
	}

	/**
	 * Adjusts the queue so the first element becomes the last element, and the last
	 * element becomes the first.
	 * 
	 * If queue is empty or contains only one integer, nothing happens.
	 * 
	 * If you can implement this method in O(1) time, +4 points
	 */
	public void swapEndPoints() {
		
		if(head == null || head.next == null) {
			return;
		}
		else {
			if(head == tail) {
				return;
			}
			if(secondToLast == head) {
				Node n = head;
				head = tail;
				tail = n;
				
				head.next = n;
				tail.next = null;
				secondToLast = head;
				return;
			}
			Node n = head; 
			head = tail;
			tail = n;
			head.next = n.next;
			tail.next = null;
			secondToLast.next = tail;
			return;
		}
	}
	
	//for testing methods
	public String printPoints() {
		String output = "{ ";
		Node iterator = head;
		
		while(iterator != null) {
			if(iterator.next == null) {
				output += iterator.value;
			}else {
				output += iterator.value + ", ";
			}
			iterator = iterator.next;
		}
		output += "} ";
		return output;
	}
	
}


