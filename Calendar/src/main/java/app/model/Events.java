package app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Events implements Serializable{
	
	private Integer eventId;
	private java.util.Date date;
	private String event;

	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}


}
