public class DailyReport
{
	private Date date;
	private int high, low;

	DailyReport(Date date, int high, int low)
	{
		this.date = date;
		this.high = high;
		this.low = low;
	}

	public Date getDate()
	{
		return date;
	}

	public int getMonth()
	{
		return date.getMonth();
	}

	public int getYear()
	{
		return date.getYear();
	}

	public int getDay()
	{
		return date.getDay();
	}

	public int getHigh()
	{
		return high;
	}

	public int getLow()
	{
		return low;
	}
}