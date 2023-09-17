import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;


// The purpose of this class is to test the functionality of the translate method in OCRTranslator class
public class OCRTest {

	private OCRTranslator translator = new OCRTranslator();

	// Task 1, check invalid String inputs -> whether top, middle, or bottom are null
	// In this task, keep it simple and simply check for throws instead of actual output

	@Test
	public void testNullInputTop() {

		String top =     null;
		String middle = "   ";
		String bottom = "   ";
		assertThrows(OCRException.class, () -> translator.translate(top, middle, bottom));

	}

	@Test
	public void testNullInputMiddle() {

		String top =    "   ";
		String middle =   null;
		String bottom = "   ";
		assertThrows(OCRException.class, () -> translator.translate(top, middle, bottom));

	}

	@Test
	public void testNullInputBottom() {

		String top =    "   ";
		String middle = "   ";
		String bottom =  null;
		assertThrows(OCRException.class, () -> translator.translate(top, middle, bottom));

	}

	// Task 2 Test input strings that have different length
	// In this task, keep it simple and simply check for throws instead of actual output

	@Test
	public void testDifferentStringLengthsWithOnlyBlanks() {

		String top =    "    ";
		String middle = "   ";
		String bottom = "   ";
		assertThrows(OCRException.class, () -> translator.translate(top, middle, bottom));

	}

	@Test
	public void testDifferentStringLengthsWithNumber() {
		//not testing the actual number, but whether this throws an invalid length exception
		String top =    " _ ";
		String middle = "|_ ";
		String bottom = " _| ";
		assertThrows(OCRException.class, () -> translator.translate(top, middle, bottom));

	}
	
	//Task 3 Test strings with same length don't throw exception for having different length
	// In this task, keep it simple and simply check for throws instead of actual output

	@Test
	public void testSameStringLengthWithNumber() {

		String top =    " ";
		String middle = "|";
		String bottom = "|";
		assertDoesNotThrow(() -> translator.translate(top, middle, bottom));

	}
	

	// Task 4 Handle cases where input String are empty

	@Test
	public void testInputStringsEmpty() {

		String top =    "";
		String middle = "";
		String bottom = "";
		String expected = "";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}

	// Task 5 Start with reading in single characters 0-9 alone with nothing else and no other spaces

	@Test
	public void testInputString0() {

		String top =    " _ ";
		String middle = "| |";
		String bottom = "|_|";
		String expected = "0";
		assertEquals(expected, translator.translate(top, middle, bottom));
	}

	@Test
	public void testInputString1() {

		String top =    " ";
		String middle = "|";
		String bottom = "|";
		String expected = "1";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}

	@Test
	public void testInputString2() {

		String top =    " _ ";
		String middle = " _|";
		String bottom = "|_ ";
		String expected = "2";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}
	@Test
	public void testInputString3() {

		String top =    "_ ";
		String middle = "_|";
		String bottom = "_|";
		String expected = "3";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}
	@Test
	public void testInputString4() {

		String top =    "   ";
		String middle = "|_|";
		String bottom = "  |";
		String expected = "4";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}
	@Test
	public void testInputString5() {

		String top =    " _ ";
		String middle = "|_ ";
		String bottom = " _|";
		String expected = "5";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}
	@Test
	public void testInputString6() {

		String top =    " _ ";
		String middle = "|_ ";
		String bottom = "|_|";
		String expected = "6";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}
	@Test
	public void testInputString7() {

		String top =    "_ ";
		String middle = " |";
		String bottom = " |";
		String expected = "7";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}
	@Test
	public void testInputString8() {

		String top =    " _ ";
		String middle = "|_|";
		String bottom = "|_|";
		String expected = "8";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}
	@Test
	public void testInputString9() {

		String top =    " _ ";
		String middle = "|_|";
		String bottom = "  |";
		String expected = "9";
		assertEquals(expected, translator.translate(top, middle, bottom));

	}
	
	// Task 6 Invalid OCR encoding -> OCR digit but with space between each character

	@Test
	public void testInvalidOCRWithSpace() {
		//try "9" but with space inbetween characters in the same digit -> throw exception
		String top =    "  _  ";
		String middle = "| _ |";
		String bottom = "    |";
		assertThrows(OCRException.class, () -> translator.translate(top, middle, bottom));

	}
	
	// Task 7 Be able to read any input with the concatenation of characters 0-9 with only 
	// one single space between all characters and no extra space at the beginning or end
	
	@Test
    public void testInputOf42() {
		
        String top =    "     _ ";
        String middle = "|_|  _|";
        String bottom = "  | |_ ";
        String expected = "42";
        assertEquals(expected, translator.translate(top, middle, bottom));
    } 
	
	//Task 8 Read input with just space
	
	@Test public void testOnlyWhiteSpace() {
		
		String top =    " ";
 		String middle = " ";
 		String bottom = " ";
 		String expected = "";
 		assertEquals(expected, translator.translate(top, middle, bottom));
		
	}
	
	// Task 9 Read any input with variable spaces between characters but still no extra space at beginning or end
	@Test
 	public void testVariableSpacesInBetweenDigits() {
 		
		String top =    " _      _ ";
 		String middle = "|_       |";
 		String bottom = "|_|      |";
 		String expected = "67";
 		assertEquals(expected, translator.translate(top, middle, bottom));
 	
	}
	
	// Task 10 Read input with variable spaces at the beginning and end without variable spaces between each digit
	
	@Test
 	public void testVariableSpacesAtBeginningEndNoInbetween() {
		
		String top =    "  _  _  ";
 		String middle = " |_   | ";
 		String bottom = " |_|  | ";
 		String expected = "67";
 		assertEquals(expected, translator.translate(top, middle, bottom));
 	
	}
	
	//Task 11 Read input with variable spaces at the beginning and end with variable spaces between each digit

	@Test
 	public void testVariableSpacesAtBeginningEndAndInbetween() {
		
		String top =    "  _    _  ";
 		String middle = " |_     | ";
 		String bottom = " |_|    | ";
 		String expected = "67";
 		assertEquals(expected, translator.translate(top, middle, bottom));
 	
	}


}
