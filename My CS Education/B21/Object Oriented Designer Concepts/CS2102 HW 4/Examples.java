package cs2102hw4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Examples {

	HeapChecker HT = new HeapChecker();

	
	//remMinEltTester tests
	
	
	@Test
	public void removeOnlyOneElt() {

		DataHeap newData = new DataHeap(5);
		MtBT emptyBT = new MtBT();

		assertTrue(HT.remMinEltTester(newData, emptyBT));
	}

	@Test
	public void removeEmptyHeap() {

		MtHeap newData = new MtHeap();
		MtBT emptyBT = new MtBT();

		assertTrue(HT.remMinEltTester(newData, emptyBT));
	}

	@Test
	public void removeOnMultEltHeap() {

		DataHeap newData2 = new DataHeap(5);
		DataHeap newData3 = new DataHeap(6);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		DataBT dataBt1 = new DataBT(5);
		DataBT dataBt2 = new DataBT(6);
		DataBT newdataBt = new DataBT(5, dataBt1, dataBt2);

		assertFalse(HT.remMinEltTester(newData, newdataBt));

	}

	@Test
	public void removeInvalidHeap() {

		DataHeap newData2 = new DataHeap(5);
		DataHeap newData3 = new DataHeap(6);
		DataHeap newData = new DataHeap(7, newData2, newData3);
		DataBT dataBt2 = new DataBT(6);
		MtBT emptyBT = new MtBT();
		DataBT newdataBt = new DataBT(7, emptyBT, dataBt2);

		assertFalse(HT.remMinEltTester(newData, newdataBt));

	}

	@Test
	public void removeWrongNumberElt() {

		DataHeap newData2 = new DataHeap(5);
		DataHeap newData3 = new DataHeap(6);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		MtBT emptyBT = new MtBT();
		DataBT newdataBt = new DataBT(6, emptyBT, emptyBT);

		assertFalse(HT.remMinEltTester(newData, newdataBt));

	}

	@Test
	public void removeWrongElt() {

		DataHeap newData2 = new DataHeap(5);
		DataHeap newData3 = new DataHeap(6);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		DataBT dataBt2 = new DataBT(6);
		MtBT emptyBT = new MtBT();
		DataBT newdataBt = new DataBT(4, emptyBT, dataBt2);

		assertFalse(HT.remMinEltTester(newData, newdataBt));

	}

	@Test
	public void removeEltActuallyAddsElt() {
		DataHeap newData2 = new DataHeap(5);
		DataHeap newData3 = new DataHeap(6);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		DataBT dataBt2 = new DataBT(4);
		MtBT emptyBT = new MtBT();
		DataBT dataBt4 = new DataBT(4);
		DataBT dataBt3 = new DataBT(5, emptyBT, dataBt4);
		DataBT newdataBt = new DataBT(4, dataBt3, dataBt2);

		assertFalse(HT.remMinEltTester(newData, newdataBt));
	}

	@Test
	public void removeEltSameEltMultipleTimes() {
		DataHeap newData2 = new DataHeap(5);
		DataHeap newData3 = new DataHeap(4);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		DataBT dataBt2 = new DataBT(5);
		MtBT emptyBT = new MtBT();
		DataBT newdataBt = new DataBT(4, dataBt2, emptyBT);

		assertTrue(HT.remMinEltTester(newData, newdataBt));

	}

	@Test
	public void removeEltAllSameElt() {
		DataHeap newData2 = new DataHeap(4);
		DataHeap newData3 = new DataHeap(4);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		DataBT dataBt2 = new DataBT(4);
		MtBT emptyBT = new MtBT();
		DataBT newdataBt = new DataBT(4, dataBt2, emptyBT);

		assertTrue(HT.remMinEltTester(newData, newdataBt));

	}

	@Test
	public void removeTooManyElt() {

		DataHeap newData2 = new DataHeap(4);
		DataHeap newData3 = new DataHeap(4);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		MtBT emptyBT = new MtBT();
		DataBT newdataBt = new DataBT(4, emptyBT, emptyBT);

		assertFalse(HT.remMinEltTester(newData, newdataBt));

	}
	
	@Test
	public void removeEltDoesntRemoveAnything() {
		DataHeap newData2 = new DataHeap(4);
		DataHeap newData3 = new DataHeap(4);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		DataBT newDataBT = new DataBT(4);
		DataBT newDataBT2 = new DataBT(4);
		DataBT newdataBt = new DataBT(4, newDataBT, newDataBT2);

		assertFalse(HT.remMinEltTester(newData, newdataBt));
	}

	@Test
	public void removeSmallestEltOnBigHeap() {
		DataHeap newData4 = new DataHeap(7);
		DataHeap newData5 = new DataHeap(8);
		DataHeap newData6 = new DataHeap(9);
		DataHeap newData7 = new DataHeap(10);
		DataHeap newData2 = new DataHeap(5, newData4, newData5);
		DataHeap newData3 = new DataHeap(6, newData6, newData7);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		MtBT emptyBT = new MtBT();
		DataBT newDataBT1 = new DataBT(8);
		DataBT newDataBT2 = new DataBT(9);
		DataBT newDataBT3 = new DataBT(10);
		DataBT newDataBT4 = new DataBT(6, newDataBT1, newDataBT2);
		DataBT newDataBT5 = new DataBT(7, newDataBT3, emptyBT);
		DataBT newdataBt = new DataBT(5, newDataBT4, newDataBT5);

		assertTrue(HT.remMinEltTester(newData, newdataBt));

	}

	
	//addEltTest tests
	
	@Test
	public void addEltToEmpty() {
		MtHeap emptyHeap = new MtHeap();
		MtBT emptyBT = new MtBT();
		DataBT newDataBT = new DataBT(6, emptyBT, emptyBT);

		assertTrue(HT.addEltTester(emptyHeap, 6, newDataBT));

	}

	@Test
	public void addEltToSingleElt() {
		DataHeap newData = new DataHeap(5);
		MtBT emptyBT = new MtBT();
		DataBT newDataBT1 = new DataBT(6);
		DataBT newDataBT = new DataBT(5, newDataBT1, emptyBT);

		assertTrue(HT.addEltTester(newData, 6, newDataBT));

	}

	@Test
	public void addEltOnBigHeap() {
		DataHeap newData4 = new DataHeap(7);
		DataHeap newData5 = new DataHeap(8);
		DataHeap newData6 = new DataHeap(9);
		DataHeap newData7 = new DataHeap(10);
		DataHeap newData2 = new DataHeap(5, newData4, newData5);
		DataHeap newData3 = new DataHeap(6, newData6, newData7);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		MtBT emptyBT = new MtBT();
		DataBT newDataBT4 = new DataBT(7);
		DataBT addedElt = new DataBT(20);
		DataBT newDataBT5 = new DataBT(8, addedElt, emptyBT);
		DataBT newDataBT6 = new DataBT(9);
		DataBT newDataBT7 = new DataBT(10);
		DataBT newDataBT2 = new DataBT(5, newDataBT4, newDataBT5);
		DataBT newDataBT3 = new DataBT(6, newDataBT6, newDataBT7);
		DataBT newDataBT = new DataBT(4, newDataBT2, newDataBT3);

		assertTrue(HT.addEltTester(newData, 20, newDataBT));

	}

	@Test
	public void addEltNonHeap() {
		DataHeap newData4 = new DataHeap(7);
		DataHeap newData5 = new DataHeap(8);
		DataHeap newData6 = new DataHeap(9);
		DataHeap newData7 = new DataHeap(10);
		DataHeap newData2 = new DataHeap(5, newData4, newData5);
		DataHeap newData3 = new DataHeap(6, newData6, newData7);
		DataHeap newData = new DataHeap(30, newData2, newData3);
		MtBT emptyBT = new MtBT();
		DataBT newDataBT4 = new DataBT(7);
		DataBT addedElt = new DataBT(20);
		DataBT newDataBT5 = new DataBT(8, addedElt, emptyBT);
		DataBT newDataBT6 = new DataBT(9);
		DataBT newDataBT7 = new DataBT(10);
		DataBT newDataBT2 = new DataBT(5, newDataBT4, newDataBT5);
		DataBT newDataBT3 = new DataBT(6, newDataBT6, newDataBT7);
		DataBT newDataBT = new DataBT(30, newDataBT2, newDataBT3);

		assertFalse(HT.addEltTester(newData, 20, newDataBT));

	}

	@Test
	public void addTooManyElt() {
		DataHeap newData = new DataHeap(5);
		DataBT newDataBT1 = new DataBT(6);
		DataBT newDataBT2 = new DataBT(10);
		DataBT newDataBT = new DataBT(5, newDataBT1, newDataBT2);

		assertFalse(HT.addEltTester(newData, 6, newDataBT));
	}
	
	@Test
	public void addDidntAddElt() {
		DataHeap newData = new DataHeap(5);
		DataBT newDataBT1 = new DataBT(5);
		DataBT newDataBT = new DataBT(5, newDataBT1, newDataBT1);

		assertFalse(HT.addEltTester(newData, 5, newDataBT));
	}

	@Test
	public void addEltHeapMultSameElt() {
		DataHeap newData = new DataHeap(5);
		DataHeap newData1 = new DataHeap(6);
		DataHeap newData2 = new DataHeap(5, newData, newData1);
		MtBT emptyBT = new MtBT();
		DataBT newAddedElt = new DataBT(7);
		DataBT newDataBT2 = new DataBT(5, emptyBT, newAddedElt);
		DataBT newDataBT3 = new DataBT(6);
		DataBT newDataBT = new DataBT(5, newDataBT2, newDataBT3);

		assertTrue(HT.addEltTester(newData2, 7, newDataBT));

	}

	@Test
	public void addEltThatIsAlreadyInHeap() {
		DataHeap newData = new DataHeap(5);
		DataHeap newData1 = new DataHeap(6);
		DataHeap newData2 = new DataHeap(5, newData, newData1);
		MtBT emptyBT = new MtBT();
		DataBT newAddedElt = new DataBT(5);
		DataBT newDataBT2 = new DataBT(5, emptyBT, newAddedElt);
		DataBT newDataBT3 = new DataBT(6);
		DataBT newDataBT = new DataBT(5, newDataBT2, newDataBT3);

		assertTrue(HT.addEltTester(newData2, 5, newDataBT));

	}

	@Test
	public void addEltHeapAllSameElt() {

		DataHeap newData = new DataHeap(5);
		DataHeap newData1 = new DataHeap(5);
		DataHeap newData2 = new DataHeap(5, newData, newData1);
		MtBT emptyBT = new MtBT();
		DataBT newAddedElt = new DataBT(5);
		DataBT newDataBT2 = new DataBT(5, emptyBT, newAddedElt);
		DataBT newDataBT3 = new DataBT(5);
		DataBT newDataBT = new DataBT(5, newDataBT2, newDataBT3);

		assertTrue(HT.addEltTester(newData2, 5, newDataBT));

	}

	@Test
	public void addEltActuallyRemoved() {
		
		DataHeap newData2 = new DataHeap(4);
		DataHeap newData3 = new DataHeap(4);
		DataHeap newData = new DataHeap(4, newData2, newData3);
		DataBT dataBt2 = new DataBT(4);
		MtBT emptyBT = new MtBT();
		DataBT newdataBt = new DataBT(4, dataBt2, emptyBT);

		assertFalse(HT.addEltTester(newData, 4, newdataBt));
		
	}

	@Test
	public void addWrongElt() {
		
		DataHeap newData = new DataHeap(5);
		DataHeap newData1 = new DataHeap(5);
		DataHeap newData2 = new DataHeap(5, newData, newData1);
		MtBT emptyBT = new MtBT();
		DataBT newAddedElt = new DataBT(6);
		DataBT newDataBT2 = new DataBT(5, emptyBT, newAddedElt);
		DataBT newDataBT3 = new DataBT(5);
		DataBT newDataBT = new DataBT(5, newDataBT2, newDataBT3);

		assertFalse(HT.addEltTester(newData2, 5, newDataBT));

	}

}
