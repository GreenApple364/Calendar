package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import app.domain.CalendarService;

@Controller
public class CalendarController {
	
	@Autowired
	CalendarService calendarService;

	@RequestMapping(value="/")
	public String home(){
		calendarService.select();
		return "calendar";
	}
}
