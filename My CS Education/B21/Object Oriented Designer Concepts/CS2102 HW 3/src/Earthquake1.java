import java.util.LinkedList;

class Earthquake1 {
  Earthquake1(){}

  // checks whether a datum is a date
  boolean isDate(double anum) { return (int)anum > 10000000; }
  // extracts the month from an 8-digit date
  int extractMonth(double dateNum) { return ((int)dateNum % 10000) / 100; }
 
  
  
  /**
   * Consumes a list of data and a month and produces a a list of MaxHzReport that have the highest frequency for each day of each month
   * @param data List of sensor data
   * @param month The month which we want to collect MaxHz from sensor data
   * @return a list of MaxHzReport that have the highest frequency for each day of each month
   */
  public LinkedList<MaxHzReport> dailyMaxForMonth(LinkedList<Double> data, int month){
	  

	    double accumulator = 0;
	    
	    LinkedList<MaxHzReport> holder = new LinkedList<>();
	    
	    for(int i=0 ; i<data.size() ; i++){

	    	Double someData = data.get(i);
	    	
	      if(someData > 500){
	    	  
	        accumulator = someData;

	        if(((int) (accumulator) / 100) % 100 == month) {
	        	        	
	          MaxHzReport aReport = new MaxHzReport(accumulator, 0);
	          
	          holder.add(aReport);
	          
	        }
	        else {
	        	accumulator = 0;
	        }
	      }

	      else {

	          if(accumulator > 0) {
	        	  
	            double maxData = holder.get(holder.size() - 1).maxReading;

	            if(maxData < someData){
	            	
	              MaxHzReport anotherReport = new MaxHzReport(accumulator, someData);

	              holder.set(holder.size() - 1, anotherReport);
	            }
	          }
	      }
	    }
	    
	    return holder;
	  }
  
  
  
	}