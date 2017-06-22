package app;

import java.io.Serializable;
import java.time.YearMonth;

@SuppressWarnings("serial")
public class RemoveEventForm implements Serializable {
	
	private String removeDate;
	private YearMonth viewingYearMonth;


	public String getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(String removeDate) {
		this.removeDate = removeDate;
	}

	public YearMonth getViewingYearMonth() {
		return viewingYearMonth;
	}

	public void setViewingYearMonth(YearMonth viewingYearMonth) {
		this.viewingYearMonth = viewingYearMonth;
	}
	
	

}
