import java.util.HashMap;
import java.util.Map;

//This class is used for creating an OCR map with a mapping of each digit
// 0-9 that is a concatenation of the top, middle, and bottom strings
public class OCRMap {

	private Map<String, String> ocrMap;


	public OCRMap() {

		ocrMap = OCRDecodingMap();

	}

	/**
	 * 
	 * Map of each number 0-9 that is a concatenation of the top, middle, and bottom strings for OCR digit calc
	 * @return a map containing the concatenation of the top, middle, and bottom strings for 0-9 for OCR digit calc as key/value pairs
	 */
	private static Map<String, String> OCRDecodingMap() {
		Map<String, String> ocrMap = new HashMap<>();
		ocrMap.put(" _ | ||_|", "0");
		ocrMap.put(" ||", "1");
		ocrMap.put(" _  _||_ ", "2");
		ocrMap.put("_ _|_|", "3");
		ocrMap.put("   |_|  |", "4");
		ocrMap.put(" _ |_  _|", "5");
		ocrMap.put(" _ |_ |_|", "6");
		ocrMap.put("_  | |", "7");
		ocrMap.put(" _ |_||_|", "8");
		ocrMap.put(" _ |_|  |", "9");
		return ocrMap;
	}

	/**
	 * 
	 * Helper method for the calculateOCRdigits method.
	 * @param ocrCode the concatenation of top, middle, and bottom strings for OCR calculations
	 * @return the value in the map corresponding to the ocrCode or null if the ocrCode key doesn't exist in the map
	 */
	public String getDigit(String ocrEncoding) {
		return ocrMap.get(ocrEncoding);
	}


}
