package app;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;

import app.model.NewEvent;

@SuppressWarnings("serial")
public class NewEventForm implements Serializable {

	private ArrayList<NewEvent> newEvents;
	private YearMonth viewingYearMonth;
	
	public YearMonth getViewingYearMonth() {
		return viewingYearMonth;
	}
	public void setViewingYearMonth(YearMonth viewingYearMonth) {
		this.viewingYearMonth = viewingYearMonth;
	}
	public ArrayList<NewEvent> getNewEvents() {
		return newEvents;
	}
	public void setNewEvents(ArrayList<NewEvent> newEvents) {
		this.newEvents = newEvents;
	}
	
}
