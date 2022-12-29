package cs2102hw4;

import java.util.LinkedList;

interface IBinTree {
	// determines whether element is in the tree
	 boolean hasElt(int e);
	 // returns number of nodes in the tree; counts duplicate elements as separate items
	 int size();
	 // returns depth of longest branch in the tree
	 int height();
	 
	 boolean isHeap();
	 
	 
	 /**
	  * Consumes an int and produces true if the root of this heap is bigger than the given element e
	  * and produces false otherwise
	  * @param e an int that compares the root of the given heap with
	  * @return true if the root of this heap is bigger than the given element e
	  * and produces false otherwise
	  */
	 boolean isBigger(int e);
	 
	 
	 /**
	  * consumes aListOfInteger and adds all elements of tree to a list of integer
	  * @param listOfElement
	  */
	 void populateElementList(LinkedList<Integer> listOfElement);
}
	
