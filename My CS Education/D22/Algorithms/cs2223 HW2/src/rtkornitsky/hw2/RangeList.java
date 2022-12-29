package rtkornitsky.hw2;

/**
 * A RangeList contains a compact representation of integer sequences.
 * 
 * For example, given the integer values of { 1, 2, 3, 5, 7, 9, 10, 11, 12} a
 * compact representation would be
 * 
 * { 1-3,5,7,9-12 }
 * 
 * Your assignment is to implement the following API for this data type.
 */
public class RangeList {

	int size;

	/** Linked list node to use. */
	class Node {
		int low;
		int high;
		Node next;

		Node(int lo, int hi) {
			this.low = lo;
			this.high = hi;
		}

		Node(int lo, int hi, Node next) {
			this.low = lo;
			this.high = hi;
			this.next = next;
		}
	}

	Node head = null;

	public RangeList() {
	}

	/**
	 * Return the number of distinct ranges in the RangeList. Performance must be
	 * O(N)
	 */
	public int numberRanges() {
		int numRanges = 0;
		Node n = head;
		while (n != null) {
			numRanges++;
			n = n.next;
		}
		return numRanges;
	}

	/**
	 * Return the number of distinct values in the RangeList. Performance must be
	 * O(1)
	 */
	public int numberValues() {
		return size;
	}

	/**
	 * Determines whether the current RangeList is a subset of the parameter.
	 * 
	 * This object is a subset of rl if every value contained in this object also is
	 * found in rl.
	 * 
	 * Five points for correctness
	 * 
	 * If you have a solution that provides O(N^2) performance you get +5 points If
	 * you have a solution that provides O(N) performance you get +10 points
	 */
	public boolean subsetOf(RangeList rl) {

		Node node1 = head;
		Node node2 = rl.head;

		while (node1 != null && node2 != null) {
			if (node1.low >= node2.low && node1.high <= node2.high) {
				if (node1.high == node2.high) {
					node2 = node2.next;
				}
				node1 = node1.next;
			}
			else if(node2.low < node1.low && node2.high < node1.low) {
				node2 = node2.next;
			} else if (node2.low >= node1.low && node2.high <= node1.high) {
				if (node1.high == node2.high) {
					node1 = node1.next;
				}
				node2 = node2.next;
			} else {
				return false;
			}

		}
		if (node1 == null) {
			return true;
		}
		return false;
	}

	/**
	 * Determine if the current RangeList contains the same values as the parameter
	 * list. Performance: O(N)
	 */

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof RangeList) {
			RangeList aRangeList = (RangeList) o;
			Node aRangeNode = aRangeList.head;
			Node aNode = head;
			int nodeCounter = 0;
			int equalsCounter = 0;
			while (aRangeNode != null && aNode != null) {
				nodeCounter++;
				if (aRangeNode.low == aNode.low && aRangeNode.high == aNode.high) {
					equalsCounter++;
				}
				aNode = aNode.next;
				aRangeNode = aRangeNode.next;
			}
			if(equalsCounter == nodeCounter && aRangeNode == null && aNode == null) {
				return true;
			}
			if (nodeCounter == aRangeList.size && aRangeNode == null && aNode == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the value is contained in the given RangeList. Performance
	 * must be O(N)
	 */
	public boolean contains(int value) {

		Node node1 = head;
		while (node1 != null) {
			if (value >= node1.low && value <= node1.high) {
				return true;
			}
			node1 = node1.next;
		}
		return false;
	}

	/**
	 * If value does not already exist in RangeList, add it and return true,
	 * otherwise return false.
	 * 
	 * This method must guarantee the GAP property and the ASCENDING property of
	 * RangeList Performance must be O(N)
	 */
	public boolean add(int value) {

		if (head == null) {
			head = new Node(value, value);
			size++;
			return true;
		}
		if (value < head.low - 1) {
			Node aNode = new Node(value, value, head);
			head = aNode; // this may be wrong
			size++;
			return true;
		}
		if (value == head.low - 1) {
			head.low = value;
			size++;
			return true;
		}

		Node aNode = head;
		Node prevNode = null;
		while (aNode != null) {
			if (value <= aNode.high) {
				return false;
			}
			if (value == aNode.high + 1) {
				aNode.high++;
				if (aNode.next != null && aNode.high == aNode.next.low - 1) {
					aNode.high = aNode.next.high;
					aNode.next = aNode.next.next;
				}
				size++;
				return true;
			}
			if (aNode.next != null) {
				if (value == aNode.next.low - 1) {
					aNode.next.low = value;
					size++;
				}
				if (value < aNode.next.low) {
					Node newNode = new Node(value, value, aNode.next);
					aNode.next = newNode;
					size++;
					return true;
				}
			}
			prevNode = aNode;
			aNode = aNode.next;
		}
		prevNode.next = new Node(value, value);
		size++;
		return true;

	}

	/**
	 * Remove value from RangeList if it exists (and return true) otherwise return
	 * false.
	 * 
	 * This method must guarantee the GAP property and the ASCENDING property of
	 * RangeList Performance must be O(N)
	 */

	// value is greater than low and less than the high and
	// only consider that one case

	// [1-5]
	// 1,2,3,4,5

	// [1,2] -> (newNode) [4,5]
	public boolean remove(int value) {
		if (!contains(value)) { // covers if empty
			return false;
		}
		Node aNode = head;
		Node prevNode = null;
		while (aNode != null) {
			if (value < aNode.high && value > aNode.low) {
				Node newNodeHighEnd = new Node(value + 1, aNode.high, aNode.next);
				aNode.high = value-1;
				aNode.next = newNodeHighEnd;
		        size--;
				return true;
			}

			//[1,2]
			//[1,1]
			if (value == aNode.high && aNode.high != aNode.low) {
				aNode.high = aNode.high - 1;
				size--;
				return true;
			}
			
			//[1,2]
			//[1,1]
			if (value == aNode.low && aNode.low != aNode.high) {
				aNode.low = aNode.low + 1;
				size--;
				return true;
			}
			//[1-2] -> [5,5] -> [8,10]
			//[1,2] -> [8,10]
			//[1,2] -> [8,10]
			if (value == aNode.low && value == aNode.high) {
				//only one node
				if(aNode.next == null && prevNode == null) {
					head = null;
					size--;
					return true;
				}
				//Single node is top node
				if(aNode.next == null && prevNode != null) {
					aNode = prevNode;
					prevNode.next = null;
					size--;
					return true;
				}
				//Single node exists and is between other nodes
				if(aNode.next != null && prevNode != null) {
					prevNode.next = aNode.next;
					aNode = aNode.next;
					size--;
					return true;
					}
				if(aNode.next != null && prevNode == null) {
					prevNode = aNode.next;
					head = aNode.next;
					size--;
					return true;
				}
					else {
						aNode.next = prevNode;
						size--;
						return true;
					}
			}
			prevNode = aNode;
			aNode = aNode.next;
		}
		return false;

	}

	/**
	 * Produce string representation of ranges, in order from least to greatest.
	 * Performance must be O(N)
	 */
	public String toString() {

		// [1-3], [6-8], [10] -> "1-3 , 6-8, 10"
		// look at the head first (look at low and high)
		// put a comma an space between each range
		// if there is only 1 number in a range (if lo = high) write the single value
		// do this until the end of this list
		// create an empty string carrier/holder
		// then add it by doing str = str + ....

		Node aNode = head;
		String holder = "";
		while (aNode != null) {
			if (aNode.high == aNode.low) {
				Integer singleRange = aNode.high;
				String newSingleString = singleRange.toString();
				if (aNode.next == null) {
					holder = holder.concat("" + newSingleString + "");
				} else {
					holder = holder.concat("" + newSingleString + ",");
				}
			}
			if (aNode.high > aNode.low) {
				Integer high = aNode.high;
				Integer low = aNode.low;
				if (aNode.next == null) {
					String aString = ("" + low + "-" + high + "");
					holder = holder.concat(aString);
				} else {
					String aString = ("" + low + "-" + high + ",");
					holder = holder.concat(aString);
				}
			}
			aNode = aNode.next;
		}
		return holder;

	}

	// BONUS QUESTIONS
	// --------------------------------------------------
	/** Return a random integer from this RangeList uniformly over all values. */
	public int random() {
		throw new RuntimeException("ONLY COMPLETE IF DOING BONUS");
	}

	public java.util.Iterator<Integer> iterator() {
		throw new RuntimeException("ONLY COMPLETE IF DOING BONUS");
	}

}
