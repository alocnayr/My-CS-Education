package rtkornitsky.hw1;

/**
 * For this assignment, you are to complete this DoubleStack class that uses a
 * single array to keep track of two independent stacks.
 * 
 * One grows from the left, upwards into the array. The other grows from the
 * right, downwards into the array.
 * 
 * For example, after creating a DoubleStack of size 7, storage looks like the
 * following:
 * 
 * [ --, --, --, --, --, --, --]
 * 
 * Now issue the following commands:
 * 
 * (a) pushLeft(5); (b) pushLeft(3); (c) pushRight(7); (d) pushRight(2); (e)
 * pushLeft(1)
 * 
 * The storage changes to the following:
 * 
 * [ 5, 3, 1, --, --, 2, 7]
 * 
 * For simplicity, only 'int' values are stored.
 * 
 * Any attempt to pop an empty stack (left or right) or push to a full stack
 * must throw an IllegalStateException.
 * 
 * Complete all methods and make sure that Existing TestCases pass.
 */
public class DoubleStack {

	int array[];
	int size;
	int left = 0;
	int right = size - 1;
	
	int leftSizeCounter; //size counter for each time the pop/pushLeft method is called
	int rightSizeCounter; //size counter for each time the pop/pushRight method is called

	/** Construct a DoubleStack that can store n integers. */
	public DoubleStack(int n) {

		this.size = n;
		array = new int[n];
		left = 0;
		right = n - 1;

	}

	/** Determines if Double Stack is full. */
	public boolean isFull() {
		
		if (left > right || right < left) {
			return true;
		}

		return false;
	}

	/** Returns the number of int values in the left stack. */
	public int sizeLeft() {
		return leftSizeCounter;
	}

	/** Returns the number of int values in the right stack. */
	public int sizeRight() {
		return rightSizeCounter;
	}

	/** If DoubleStack is not full, push value onto the left stack. */
	public void pushLeft(int v) {
		if ((sizeLeft() + sizeRight()) != size) {
				array[left] = v;
				left++;
				leftSizeCounter++;
			} else {
				throw new IllegalStateException("Stack is full");
			}
		}
	

	/** If DoubleStack is not full, push value onto the right stack. */
	public void pushRight(int v) {
		if ((sizeLeft() + sizeRight()) != size) {
			array[right] = v;
			right--;
			rightSizeCounter++;
		} else {
			throw new IllegalStateException("Stack is full");
		}
	}

	/**
	 * If left and right contain at least one value, exchange the top values found
	 * on both stacks. If either the left or right side is empty, throw new
	 * IllegalStateException.
	 */
	public void exchange() {
		if (leftSizeCounter>=1 && rightSizeCounter>=1) {
			int temp = array[right+1];
			array[right+1] = array[left-1];
			array[left-1] = temp;
		}
		else if(leftSizeCounter == 0) {
		throw new IllegalStateException("left stack empty");
		}
		else if(rightSizeCounter == 0) {
			throw new IllegalStateException("right stack empty");
		}
		else if(leftSizeCounter == 0 && rightSizeCounter == 0) {
			throw new IllegalStateException("both stacks are empty");
		}
	}

	/** Pop and return the topmost value from left stack. */
	public int popLeft() {
		if ((left > 0) && sizeLeft() != 0) {
			int v = array[left-1];
			left--;
			if(leftSizeCounter > 0) {
			leftSizeCounter--;
			}
			return v;
		} else {
			throw new IllegalStateException("Left stack is empty");
		}
	}

	
	/** Pop and return the topmost value from right stack. */
	public int popRight() {
		if ((right < size) && sizeRight() != 0) {
			int v = array[right+1];
			right++;
			if(rightSizeCounter > 0) {
			rightSizeCounter--;
			}
			return v;
		} else {
			throw new IllegalStateException("Right stack is empty");
		}
	}
}
