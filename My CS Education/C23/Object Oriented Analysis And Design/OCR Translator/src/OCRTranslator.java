

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016-2017 Gary F. Pollice
 *******************************************************************************/


/**
 * This class has a single public method that will translate OCR digits to a string of
 * text digits that correspond to the OCR digits.
 * 
 * @version Mar 13, 2019
 */
public class OCRTranslator
{

	private OCRMap ocrMap;

	public OCRTranslator()
	{
		ocrMap = new OCRMap();
	}

	/**
	 * Translate a string of OCR digits to a corresponding string of text
	 * digits. OCR digits are represented as three rows of characters (|, _, and space).
	 * @param top the top row of the OCR input
	 * @param middle the middle row of the OCR input
	 * @param bottom the third row of the OCR input
	 * @return a String containing the digits corresponding to the OCR input
	 * @throws an OCRException on error as noted in the specification
	 */
	public String translate(String top, String middle, String bottom) {

		if (top == null || middle == null || bottom == null) {
			throw new OCRException("Null input strings are not allowed");
		}
		if (top.length() != middle.length() || top.length() != bottom.length()) {
			throw new OCRException("Input strings need to have the same length");
		}

		return calculateOCRdigits(top, middle, bottom);

	}


	/**
	 * Translate a string of OCR digits to a corresponding string of text
	 * digits. OCR digits are represented as three rows of characters (|, _, and space).
	 * Helper method for the translate method.
	 * @param top the top row of the OCR input
	 * @param middle the middle row of the OCR input
	 * @param bottom the third row of the OCR input
	 * @return a String containing the digits corresponding to the OCR input
	 * @throws an OCRException on error as noted in the specification
	 */
	private String calculateOCRdigits(String top, String middle, String bottom) {

		StringBuilder translation = new StringBuilder();

		//stepSize indicates the variable increments of i based on the size of the current digit
		//Default it to 4 since most digits are size 3
		int stepSize = 4;

		//used when encountering multiple proceeding blanks
		int amountOfExtraSpace = 0;

		for (int i = 0; i < top.length(); i += stepSize) {

			if(top.charAt(i) == ' ' && middle.charAt(i) == ' ' && bottom.charAt(i) == ' ') {
				amountOfExtraSpace++;
				//set stepSize to 1 to cycle through each char, looking for a non blank
				stepSize = 1;
			}

			if(top.charAt(i) != ' ' || middle.charAt(i) != ' ' || bottom.charAt(i) != ' '){
				//once a non blank is found, proceed to the OCR digit calculation
				amountOfExtraSpace = 0;
			}

			//cycle through all the extra space before computing OCR digit
			if(amountOfExtraSpace < 1) {

				int topLength = top.length();

				String code = null;
				String digit = null;

				//each digit is either going to be size 3, 2, or 1. Start from size 3 bc its the most common size
				//want to look at the next digitLength characters to calculate the OCR digit
				for (int digitLength = 3; digitLength > 0; digitLength--) {
					//ensure no OutOfBoundsException
					if (i + digitLength <= topLength) {
						
						code = getDigitCode(i, digitLength, top, middle, bottom);
						digit = ocrMap.getDigit(code);
						
						//proper translation from ocrMap found
						if (digit != null) {
							
							translation.append(digit);
							stepSize = digitLength + 1;
							break;
							
						}
					}
				}
				//no translation found
				if(digit == null) {
					throw new OCRException("Invalid OCR Encoding");
				}
			}
		}
		return translation.toString();
	}

	/**
	 * 
	 * Helper method for the calculateOCRdigits method.
	 * Computes the concatenation of top, middle, bottom substrings to see if contained in the OCRMap
	 * @param i the index of the current character
	 * @param stepSize how far to index each string to find OCR string
	 * @param top the top row of the OCR input
	 * @param middle the middle row of the OCR input
	 * @param bottom the third row of the OCR input
	 * @return a String containing the concat of top, middle, and bottom OCR digits
	 */
	private String getDigitCode(int i, int stepSize, String top, String middle, String bottom) {
		return top.substring(i, i + stepSize) + middle.substring(i, i + stepSize) + bottom.substring(i, i + stepSize);
	}
}


