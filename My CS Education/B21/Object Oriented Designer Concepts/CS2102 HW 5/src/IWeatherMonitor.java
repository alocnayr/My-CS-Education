import java.util.LinkedList;

public interface IWeatherMonitor
{
	public Integer averageHighForMonth(int month, int year);
	public Integer averageLowForMonth(int month, int year);
	public void addDailyReport(Date date, LinkedList<Integer> temps);
}