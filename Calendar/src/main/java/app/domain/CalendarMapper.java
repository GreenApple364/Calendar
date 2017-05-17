package app.domain;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import app.model.Events;

@Mapper
public interface CalendarMapper {
	
	@Select("SELECT events_id,date,event FROM events WHERE date BETWEEN #{from} AND #{to} ORDER BY date ASC")
	List<Events> selectMonthlyEvents(@Param(value="from")Date from, @Param(value="to")Date to);
}
