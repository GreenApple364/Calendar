package app;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
public class NewEventForm implements Serializable {

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
