package rtkornitsky.hw3;

/**
 * Store (key, value) pairs in BinarySearchTree over keys.
 * 
 * For simplicity, it is not possible to remove (key, value) pairs.
 */
public class BST_SymbolTable {

	class Node {
		Integer   key;
		Integer   value;
		Node      left, right;  // left and right subtrees

		public Node(Integer key, Integer value) {
			this.key = key;
			this.value = value;
		}

		// here for debugging
		public String toString() { return "[" + key + "=" + value + "]"; }
	}

	Node root;               // root of the tree

	/** Invoke put on parent, should it exist. */
	public void put(Integer key, Integer value) { 
		root = put(root, key, value);
	}

	/** Return value associated with key, or null if it doesn't exist. */
	public Integer get(Integer key) { 
		Node n = get(root, key);
		if (n == null) { return null; } else { return n.value; }
	}

	private Node get (Node parent, Integer key) {
		if (parent == null) return null;
		
		int cmp = key.compareTo(parent.key);  // compare on keys
		if      (cmp < 0) { return get(parent.left, key); }
		else if (cmp > 0)  { return get(parent.right, key); }
		else { return parent; }
	}
	
	/** Determine if key is contained in Symbol Table. */
	public boolean contains(Integer key) { 
		return get(root, key) != null;
	}
	
	private Node put(Node parent, Integer key, Integer value) {
		if (parent == null) return new Node(key, value);

		int cmp = key.compareTo(parent.key);  // compare on keys
		if      (cmp < 0) { parent.left  = put(parent.left,  key, value); }
		else if (cmp > 0)  { parent.right = put(parent.right, key, value); }
		else { parent.value = value; }  // replace value
		
		return parent;
	}
	

}
