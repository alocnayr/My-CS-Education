package rtkornitsky.hw3;

/**
 * Copy this class into the package where you have BST and it should run
 * without any errors
 */
public class TestBST {
	
	static void test(boolean b) {
		if (!b) { throw new RuntimeException("Fails Test case."); }
	}
	
	public static void main(String[] args) {
		BST bst = new BST();
		
		
		
		test(bst.keyWithLargestCount() == null);        // empty? nothing can be returned.
		
		test(bst.copy().root == null);                // copy of an empty BST is null.
		
		bst.put("a", 1);
		test(bst.keyWithLargestCount().equals("a"));    // single node is most frequent...
		bst.put("rose", 4);
		bst.put("by", 2);
		bst.put("any", 3);
		bst.put("other", 5);
		
		//System.out.println(bst.keyWithLargestCount());
		test(bst.keyWithLargestCount().equals("other"));    // add a few more, and one is higher than others...
		
		bst.put("name",  4);
		bst.put("would",  7);
		bst.put("smell",  8);
		bst.put("as",  2);
		bst.put("sweet",  9);
		
		BST bst_copy = bst.copy();    // make a copy and test it out!
		test(9 == bst_copy.get("sweet"));
		
		test(5 == bst.get("other"));
		test(bst.get("other") != null);   // 'other' exists at depth 3
		test(10 == bst.size());
		test(10 == bst_copy.size());
	} 
}