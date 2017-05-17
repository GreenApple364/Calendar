package app.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Events;

@Service
public class CalendarService {

	@Autowired
	CalendarRepository calendarRepository;

	public void select() {
		List<Events> eventsList = calendarRepository.selectMonthlyEvents(java.sql.Date.valueOf("2017-5-1"), java.sql.Date.valueOf("2017-5-31"));
		System.out.println(eventsList);
	}

	private List<List<LocalDate>> makeCalendar() {

		List<List<LocalDate>> calendar = new ArrayList<List<LocalDate>>();
		List<LocalDate> week = null;

		LocalDate firstDayOfMonth = LocalDate.of(2017, 6, 1);
		LocalDate lastDayOfMonth = LocalDate.of(firstDayOfMonth.getYear(), firstDayOfMonth.getMonthValue(),
				firstDayOfMonth.lengthOfMonth());

		int firstDayOfCalendar = firstDayOfMonth.getDayOfWeek().getValue();

		// 週を表すリストを月曜日から始めたいので、月初が月曜日でない場合,
		// その週の月曜日を初日とする.
		if (firstDayOfCalendar != 1) {
			firstDayOfMonth = firstDayOfMonth.minusDays(firstDayOfCalendar - 1);
		}

		for (int i = 0; i <= 100; ++i) {
			LocalDate d = firstDayOfMonth.plusDays(i);
			int wd = d.getDayOfWeek().getValue();
			if (wd == 1) {
				week = new ArrayList<LocalDate>();
			}
			week.add(d);
			if (wd == 7) {
				calendar.add(week);
				week = null;
			}
			if (d.equals(lastDayOfMonth)) {
				break;
			}
		}

		int wd = lastDayOfMonth.getDayOfWeek().getValue();
		if (wd != 7) {
			for (int i = 1; i <= 7 - wd; ++i) {
				LocalDate d = lastDayOfMonth.plusDays(i);
				week.add(d);
			}
			calendar.add(week);
		}

		return calendar;
	}
}
