package rtkornitsky.hw3;

// Copy this file into your USERID.hw3 package

public class BenfordValidate {
	public static void main(String[] args) {
		// This BST should is already provided for you
		BST aBST = new BST();
		BST anotherBST = new BST();
		System.out.println("Leading Digit\tCount(m)\t%\t\tCount(ft)\t%\t\tPer Benfords Law");
		for (int i = 0; i < Table.heights.length; i++) {
			String meter = Table.heights[i][0];
			String feet = Table.heights[i][1];
			String firstDigit = meter.substring(0, 1);
			String feetDigit = feet.substring(0, 1);

			if (aBST.get(firstDigit) == null) {
				aBST.put(firstDigit, 1);
			} else {
				aBST.put(firstDigit, aBST.get(firstDigit) + 1);
			}
			if (anotherBST.get(feetDigit) == null) {
				anotherBST.put(feetDigit, 1);
			} else {
				anotherBST.put(feetDigit, anotherBST.get(feetDigit) + 1);
			}
		}
		for (int i = 1; i < 10; i++) {
			int countMeter = 0;
			int countFeet = 0;
			double meterPercent = 0;
			double feetPercent = 0;
			double benford = 0;
			if(aBST.get(String.valueOf(i)) != null && anotherBST.get(String.valueOf(i)) != null) {
	
			countMeter = aBST.get(String.valueOf(i)).intValue();
			countFeet = anotherBST.get(String.valueOf(i)).intValue();
			 meterPercent = ((double) countMeter / 62) * 100;
			 feetPercent =  ((double)countFeet / 62) * 100;
			 benford = Math.log10(1 + (1 / (double) i)) * 100;
			}
			
			else if(aBST.get(String.valueOf(i)) == null && anotherBST.get(String.valueOf(i)) != null){
				countMeter = 0;
				countFeet = anotherBST.get(String.valueOf(i)).intValue();
				 meterPercent = ((double) countMeter / 62) * 100;
				 feetPercent =  ((double)countFeet / 62) * 100;
				 benford = Math.log10(1 + (1 / (double) i)) * 100;
			}
			else if(aBST.get(String.valueOf(i)) != null && anotherBST.get(String.valueOf(i)) == null){
				countMeter = aBST.get(String.valueOf(i)).intValue();
				countFeet = 0;
				 meterPercent = ((double) countMeter / 62) * 100;
				 feetPercent =  ((double)countFeet / 62) * 100;
				 benford = Math.log10(1 + (1 / (double) i)) * 100;
			}

			System.out.println(i + "\t\t" + countMeter + "\t" + meterPercent 
					+ "%" + "\t" + +countFeet + "\t" + feetPercent + "%" + "\t" + benford + "%");
		}
		

	}
}