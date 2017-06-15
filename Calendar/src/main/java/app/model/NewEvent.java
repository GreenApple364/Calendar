package app.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class NewEvent {
	
	@NotNull
	private Date eventDate;
	@NotBlank
	private String event;
	
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = java.sql.Date.valueOf(eventDate);
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}

}
