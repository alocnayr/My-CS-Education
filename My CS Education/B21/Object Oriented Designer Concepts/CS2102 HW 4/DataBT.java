package cs2102hw4;

import java.lang.Math;
import java.util.LinkedList;

class DataBT implements IBinTree {
	int data;
	IBinTree left;
	IBinTree right;

	DataBT(int data, IBinTree left, IBinTree right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	// an alternate constructor for when both subtrees are empty
	DataBT(int data) {
		this.data = data;
		this.left = new MtBT();
		this.right = new MtBT();
	}

	// determines whether this node or node in subtree has given element
	public boolean hasElt(int e) {
		return this.data == e || this.left.hasElt(e) || this.right.hasElt(e);
	}

	// adds 1 to the number of nodes in the left and right subtrees
	public int size() {
		return 1 + this.left.size() + this.right.size();
	}

	// adds 1 to the height of the taller subtree
	public int height() {
		return 1 + Math.max(this.left.height(), this.right.height());
	}

	@Override
	/**
	 * Returns true if the BT is a heap and false otherwise
	 */
	public boolean isHeap() {

		if (this.size() > 0) {
			if (left.isBigger(this.data) == true && (right.isBigger(this.data) == true)) {
				if (left.isHeap() == true && (right.isHeap() == true)) {
					return true;

				}
				return false;

			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	public boolean isBigger(int e) {
		return (this.data >= e);
	}
	
	
	/**
	 * Consumes a list of integer and adds all elements of a tree (DataBT) to the list
	 * @param listOfElement A list of integer represents node values of a tree
	 */
	public void populateElementList(LinkedList<Integer> listOfElement) {
		
		if (this.size() > 0) {
		listOfElement.add(this.data);
		left.populateElementList(listOfElement);
		right.populateElementList(listOfElement);	
	}
}
}