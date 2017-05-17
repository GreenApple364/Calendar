package app.domain;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.model.Events;

@Repository
public class CalendarRepository {
	
	@Autowired
	CalendarMapper calendarMapper;
	
	public List<Events> selectMonthlyEvents(Date from,Date to){
		return calendarMapper.selectMonthlyEvents(from,to);
	}
}
