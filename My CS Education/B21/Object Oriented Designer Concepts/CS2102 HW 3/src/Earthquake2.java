import java.util.LinkedList;

class Earthquake2 {
  Earthquake2(){}
      
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
  public LinkedList<MaxHzReport> dailyMaxForMonth(LinkedList<Double> data, int month) {
     
	  LinkedList<MaxHzReport> holder = new LinkedList<MaxHzReport>();

      for (int i = 0; i < data.size(); i++) {
    	  
    	  Double someData = data.get(i);

          if (someData > 10000000) {

              if ((int)(someData % 10000) / 100 == month) {
            	  
                  double aDate = data.get(i);               
                  double maxDate = data.get(i + 1);

                  for (int a = i+1; a < data.size(); a++) {
                	  
                	  Double dataA = data.get(a);

                      if (dataA <= 500) {
                          if (dataA > maxDate)
                              maxDate = dataA;
                      }
                      if (dataA > 10000000)
                          break;

                  }
                  
                  holder.add(new MaxHzReport(aDate, maxDate));
              }
          }
      }

      return holder;
  }
}