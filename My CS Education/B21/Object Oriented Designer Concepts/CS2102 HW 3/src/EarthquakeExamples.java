import static org.junit.Assert.*;
import org.junit.Test;
import java.util.LinkedList;

public class EarthquakeExamples {
  Earthquake1 E1 = new Earthquake1();
  LinkedList<Double> noData = new LinkedList<Double>();
  LinkedList<Double> threeDates = new LinkedList<Double>();  
  LinkedList<MaxHzReport> NovReports = new LinkedList<MaxHzReport>();
  LinkedList<MaxHzReport> DecReports = new LinkedList<MaxHzReport>();
  
  public EarthquakeExamples() {
    threeDates.add(20151013.0);
    threeDates.add(10.0);
    threeDates.add(5.0);
    threeDates.add(20151020.0);
    threeDates.add(40.0);
    threeDates.add(50.0);
    threeDates.add(45.0);
    threeDates.add(20151101.0);
    threeDates.add(6.0);
    
  }

  
  @Test
  public void oneTest() { 
	  Earthquake1 E2 = new Earthquake1();
	  LinkedList<Double> data = new LinkedList<Double>();  
	  LinkedList<MaxHzReport> OctReports = new LinkedList<MaxHzReport>();
	    data.add(20151013.0);
	    data.add(10.0);
	OctReports.add(new MaxHzReport(20151013.0,10.0));
    assertEquals(OctReports, 
                 E2.dailyMaxForMonth(data, 10));
  }
  
  @Test
  public void numerousQuakes() { 
	  Earthquake1 E2 = new Earthquake1();
	  LinkedList<Double> data = new LinkedList<Double>();  
	  LinkedList<MaxHzReport> OctReports = new LinkedList<MaxHzReport>();
	    data.add(20151013.0);
	    data.add(10.0);
	    data.add(100.0);
	    data.add(499.0);
	OctReports.add(new MaxHzReport(20151013.0,499.0));
    assertEquals(OctReports, 
                 E2.dailyMaxForMonth(data, 10));
  }
  
  @Test
  public void quake500() { 
	  Earthquake1 E2 = new Earthquake1();
	  LinkedList<Double> data = new LinkedList<Double>();  
	  LinkedList<MaxHzReport> OctReports = new LinkedList<MaxHzReport>();
	    data.add(20151013.0);
	    data.add(10.0);
	    data.add(100.0);
	    data.add(500.0);
	OctReports.add(new MaxHzReport(20151013.0,500.0));
    assertEquals(OctReports, 
                 E2.dailyMaxForMonth(data, 10));
  }
  
  
  
  @Test
  public void empty() {
	  Earthquake1 E2 = new Earthquake1();
	  LinkedList<Double> noData = new LinkedList<Double>();
	  LinkedList<MaxHzReport> DecReports = new LinkedList<MaxHzReport>();
	  assertEquals(DecReports, E2.dailyMaxForMonth(noData, 0));
  }
  
  
  @Test
  public void differentDates() {
	  Earthquake1 E2 = new Earthquake1();
	  LinkedList<Double> data = new LinkedList<Double>();  
	  LinkedList<MaxHzReport> DecReports = new LinkedList<MaxHzReport>();
	    data.add(20151013.0);
	    data.add(10.0);
	    data.add(5.0);
	    data.add(40.0);
	    data.add(50.0);
	    data.add(20151101.0);
	    data.add(16.0);
	    DecReports.add(new MaxHzReport(20151101.0, 16.0));
	    assertEquals(DecReports, 
                E2.dailyMaxForMonth(data, 11));
  }
  
  @Test
  public void threeDates() {
	  Earthquake1 E2 = new Earthquake1();
	  LinkedList<Double> data = new LinkedList<Double>();  
	  LinkedList<MaxHzReport> DecReports = new LinkedList<MaxHzReport>();
	    data.add(20151013.0);
	    data.add(10.0);
	    data.add(5.0);
	    data.add(40.0);
	    data.add(50.0);
	    data.add(20151101.0);
	    data.add(16.0);
	    data.add(20151201.0);
	    data.add(160.0);
	    DecReports.add(new MaxHzReport(20151201.0, 160.0));
	    assertEquals(DecReports, 
                E2.dailyMaxForMonth(data, 12));
  }

	 
}


//Subtasks:

//consumes list of double and an int
//Consumes a list of data and a month
//identify the highest frequency 
//produce a a list of MaxHzReport 
//that have the highest frequency for each day of each month
//ignore negative frequencies
//later date never appears before an earlier one
//8 digit numbers represent dates in year month day format
//numbers between 0 and 500 represent vibration frequencies in Hz
//every date is followed by at least one frequency

//difference between Earthquake 1 and Earthquake 2:

//Earthquake1 uses an accumulator and index based loops
//to determine which idex of the data list to add to the MaxHzReport
//it makes use of a new MaxHzReport list to continuous add to while going through the data list

//Earthquake2 does not use an accumulator, instead it iterates through the data list 
//using two index based for loops
//in order to get the proper new ist of MaxHzReport, it compares the index number of the first for loop
//to the next and determines if it should keep iterating through the loop, stop (break the loop), or
//add the particular index of data list to the holder MaxHzReport.

