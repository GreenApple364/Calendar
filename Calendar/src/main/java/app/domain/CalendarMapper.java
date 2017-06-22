package app.domain;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import app.model.Events;
import app.model.NewEvent;

@Mapper
public interface CalendarMapper {
	
	@Select("SELECT events_id,events_date,event FROM events WHERE events_date BETWEEN #{from} AND #{to} ORDER BY events_date ASC")
	List<Events> selectMonthlyEvents(@Param(value="from")Date from, @Param(value="to")Date to);
	
	@Insert("INSERT INTO events VALUES(NEXTVAL('events_seq'),#{eventDate},#{event})")
	void insertNewEvents(NewEvent event);
	
	@Delete("DELETE FROM events WHERE events_date = #{removeDate}")
	void removeDailyEvents(Date removeDate);
}
