public interface IReportList
{
	public Integer getHigh(Date date);
	public int getMonthlyHigh(int month, int year);
	public Integer getLow(Date date);
	public int getMonthlyLow(int month, int year);
	public void addReport(Date date, int high, int low);
}