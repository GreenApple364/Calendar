package app;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.NotNull;

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
	
	public class NewEvent{
		
		private Date eventDate;
		@NotNull
		private String event;
		
		public Date getEventDate() {
			return eventDate;
		}
		public void setEventDate(Date eventDate) {
			this.eventDate = eventDate;
		}
		public String getEvent() {
			return event;
		}
		public void setEvent(String event) {
			this.event = event;
		}
	}
	
	
}
