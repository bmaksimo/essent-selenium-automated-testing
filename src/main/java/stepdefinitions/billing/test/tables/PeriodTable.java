package stepdefinitions.billing.test.tables;

import org.joda.time.DateTime;

import java.util.Date;

/*
 * |startdate|enddate|
 */
public class PeriodTable {

	protected String startDate;
	protected String endDate;

	public String getStartDate() {
		return startDate;
	}

	public Date getStartDateAsDate() {
		return new DateTime(startDate).toDate();
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public Date getEndDateAsDate() {
		return new DateTime(endDate).toDate();
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setStartDateAsDate(Date periodeStart) {

		startDate = new DateTime(periodeStart).toString("yyyy-MM-dd");

	}

	public void setEndDateAsDate(Date periodEnd) {
		endDate = new DateTime(periodEnd).toString("yyyy-MM-dd");

	}

}
