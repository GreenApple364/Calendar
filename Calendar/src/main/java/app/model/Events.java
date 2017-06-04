package app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Events implements Serializable{
	
	public void setDate(Integer date) {
		this.date = date;
	}
	private Integer events_id;
	private Date events_date;
	private String event;
	private LocalDate formattedDate;
	@SuppressWarnings("unused")
	private Integer month;
	@SuppressWarnings("unused")
	private Integer date;
	private List<Events> events;

	public Integer getEvents_id() {
		return events_id;
	}
	public void setEvents_id(Integer events_id) {
		this.events_id = events_id;
	}

	public Date getEvents_date() {
		return events_date;
	}
	public void setEvents_date(Date events_date) {
		this.events_date = events_date;
		setFormattedDate(this.events_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}

	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}

	public LocalDate getFormattedDate() {
		return formattedDate;
	}
	public void setFormattedDate(LocalDate formattedDate) {
		this.formattedDate = formattedDate;
	}

	public Integer getMonth(){
		return formattedDate.getMonthValue();
	}
	
	public Integer getDate(){
		return formattedDate.getDayOfMonth();
	}
	public List<Events> getEvents() {
		return events;
	}
	public void setEvents(List<Events> events) {
		this.events = events;
	}
	
	
}
