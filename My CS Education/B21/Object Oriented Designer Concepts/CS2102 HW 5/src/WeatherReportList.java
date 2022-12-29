import java.util.LinkedList;

public class WeatherReportList implements IReportList
{
	private LinkedList<DailyReport> reports = new LinkedList<DailyReport>();

	
	public Integer getHigh(Date date)
	{
		Integer h = null;
		for(DailyReport report : reports)
		{
			if (report.getDate().equals(date))
				h = report.getHigh();
		}
		return h;
	}

	
	public int getMonthlyHigh(int month, int year)
	{
		int accum = 0;
		int count = 0;
		for(DailyReport report : reports)
		{
			if (report.getMonth() == month && report.getYear() == year)
			{
				accum += report.getHigh();
				count++;
			}
		}
		return accum / count;
	}

	
	public Integer getLow(Date date)
	{
		Integer l = null;
		for(DailyReport report : reports)
		{
			if (report.getDate().equals(date))
				l = report.getLow();
		}
		return l;
	}

	
	public int getMonthlyLow(int month, int year)
	{
		int accum = 0;
		int count = 0;
		for(DailyReport report : reports)
		{
			if (report.getMonth() == month && report.getYear() == year)
			{
				accum += report.getLow();
				count++;
			}
		}
		return accum / count;
	}

	
	public void addReport(Date date, int high, int low)
	{
		DailyReport r = new DailyReport(date, high, low);
		reports.add(r);
	}
}