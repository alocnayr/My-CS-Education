package cs2102hw4;

import java.util.LinkedList;

/**
 * A class that uses various methods to determine/check whether adding or removing elements
 * from a heap produces a new valid heap
 * @author rtkornitsky
 *
 */
public class HeapChecker {

	/**
	 * Consumes an IHeap, an int, and an IBinTree and produces true if the IBinTree is a heap and has the
	 * same elemenets as the IHeap but with the int added to the tree
	 * @param hOrig an IHeap representing the original heap that we want to add an element to
	 * @param elt an int representing what int we want to add to the IHeap
	 * @param hAdded an IBinTree representing the new tree with the added int
	 * @return true if the IBinTree is a heap and has the
	 * same elemenets as the IHeap but with the int added to the tree
	 */
	boolean addEltTester(IHeap hOrig, int elt, IBinTree hAdded) {

		if (hAdded.size() >= 0) {

			// ...code to compare hOrig and hAdded around the addition of elt as
			// appropriate...

			if (hAdded.isHeap() && allOriginalEltPlusAdded(hOrig, hAdded, elt)) {

				return true;

			}

			return false;
		}
		
		return false;

	}

	/**
	 * Consumes an IHeap and an IBinTree and produces true if IBinTree is a heap and has the same elements
	 * as the IHeap but with the minimum element removed
	 * @param hOrig an IHeap representing the original heap that we want to remove the min element from
	 * @param hRemoved an IBinTree representing the new tree with the removed element
	 * @return true if IBinTree is a heap and has the same elements
	 * as the IHeap but with the minimum element removed
	 */
	boolean remMinEltTester(IHeap hOrig, IBinTree hRemoved) {

		if (hRemoved.size() >= 0) {

			if (hRemoved.isHeap() && allOriginalEltMinusMin(hOrig, hRemoved, hOrig.data())) {

				// ...code to compare hOrig and hRemoved as appropriate...
				return true;

			}

			return false;

		}
		return false;
	}

	/**
	 * consume two IBinTree and produce true if the newTree is equal to the original
	 * tree but with another node
	 * @param oldTree Represents the original tree that is being passed in
	 * @param newTree Represents the new tree that is getting the new element added
	 * @param newData Represents the element that was added
	 * @return true if the newTree is equal to the original tree but with the added node and false otherwise
	 */
	public boolean allOriginalEltPlusAdded(IBinTree oldTree, IBinTree newTree, int newData) {

		LinkedList<Integer> listOfOldTreeNode = new LinkedList<>();
		LinkedList<Integer> newList = new LinkedList<>();

		if (oldTree.size() > 0) {

			oldTree.populateElementList(listOfOldTreeNode);

		}

		if (newTree.size() > 0) {

			newTree.populateElementList(newList);
		}

		for (int i = 0; i < listOfOldTreeNode.size(); i++) {
			int nodeCompare = listOfOldTreeNode.get(i);
			int sameIndex = getIndex(newList, nodeCompare);

			if (sameIndex == -1) {
				return false;
			}

			newList.remove(sameIndex);

		}

		if (newList.size() == 1 && (newList.get(0) == newData)) {
			return true;
			
		} else {
			return false;
		}
	}

	/**
	 * consume two IBinTree and produce true if the oldTree is equal to the newTree
	 * tree but without the smallest element
	 * @param oldTree Represents the original tree that is being passed in
	 * @param newTree Represents the new tree that is getting the new element added
	 * @param minElt Represents the minimum element in newTree that was removed
	 * @return true if the oldTree is equal to the newTree but with the removed node and false otherwise
	 */
	boolean allOriginalEltMinusMin(IBinTree oldTree, IBinTree newTree, int minElt) {

		LinkedList<Integer> listOfOldTreeNode = new LinkedList<>();
		LinkedList<Integer> newList = new LinkedList<>();
		
		
			
		if (oldTree.size() > 0) {

			oldTree.populateElementList(listOfOldTreeNode);

		}

		if (newTree.size() > 0) {

			newTree.populateElementList(newList);
		}
		
		if (listOfOldTreeNode.size() == 0 && newList.size() == 0) {
			return true;
		}

		for (int i = 0; i < newList.size(); i++) {
			int nodeCompare = newList.get(i);
			int sameIndex = getIndex(listOfOldTreeNode, nodeCompare);

			if (sameIndex == -1) {
				return false;
			}

			listOfOldTreeNode.remove(sameIndex);

		}

		if (listOfOldTreeNode.size() == 1 && (listOfOldTreeNode.get(0) == minElt)) {
			return true;
			
		} else {
			return false;
		}
	}

	/**
	 * consumes a listOfInt and an element return an integer representing the index
	 * of the element
	 * @param listOfInt List of Integers
	 * @param element an integer that we want to find it's index in a list
	 * @return an integer representing at what index is element in the listOfInt
	 */
	int getIndex(LinkedList<Integer> listOfInt, int element) {

		for (int i = 0; i < listOfInt.size(); i++) {
			int compare = listOfInt.get(i);

			if (compare == element) {
				return i;
			}

		}
		return -1;
	}

}
