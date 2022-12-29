package rtkornitsky.hw3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class bstTest {
	
	@Test
	public void test() {
		BST newBST = new BST();
		newBST.put("ABC", 5);
		assertEquals(1, newBST.size());
		newBST.delete("ABC");
		assertEquals(0, newBST.size());
		newBST.delete("32");
		assertEquals(0, newBST.size());
		
		newBST.put("1", 2);
		newBST.put("3", 4);
		newBST.put("2", 5);
		newBST.put("0", 50);
		//System.out.println(newBST.root.left.key);
		BST copy = newBST.copy();
		
		System.out.println(copy.root.key);
		System.out.println(newBST.root.key);
		
		assertTrue(copy.root.key.equals(newBST.root.key));
		assertTrue(copy.root.left.key.equals(newBST.root.left.key));
		assertTrue(copy.root.right.key.equals(newBST.root.right.key));
		assertTrue(copy.root.right.left.key.equals(newBST.root.right.left.key));
		
		System.out.println(newBST.keyWithLargestCount());
		
		//newBST.deleteMin();
		
		//newBST.keyWithLargestCount();
		
	}

}
