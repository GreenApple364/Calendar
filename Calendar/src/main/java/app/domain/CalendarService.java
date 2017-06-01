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

	public List<List<Events>> selectMonthlyEvents(LocalDate wannaSelectEventsDate) {

		// イベントを取得したい月の月初及び月末を取得.
		List<LocalDate> firstAndLastDay = mkFirstAndLastDayOfMonth(wannaSelectEventsDate);
		LocalDate firstDayOfMonth = firstAndLastDay.get(0);
		LocalDate lastDayOfMonth = firstAndLastDay.get(1);

		// 指定した月のイベントを取得する.
		List<Events> eventsList = calendarRepository.selectMonthlyEvents(java.sql.Date.valueOf(firstDayOfMonth),
				java.sql.Date.valueOf(lastDayOfMonth));

		// 指定した月のカレンダー(日付オブジェクトのリスト)を取得する
		List<List<LocalDate>> calendar = makeCalendar(firstAndLastDay);

		// 日ごとにイベントが格納されていイベントオブジェクトをリターンするために、
		// カレンダーとイベントリストを走査して、一つのEventオブジェクトのリストにする.
		List<List<Events>> eventCalendar = mergeCalendarAndEvents(calendar, eventsList);
		
		return eventCalendar;

	}

	private List<List<Events>> mergeCalendarAndEvents(List<List<LocalDate>> calendar, List<Events> eventsList) {

		List<List<Events>> eventCalendar = new ArrayList<List<Events>>();
		List<Events> eventWeek = null;

		for (List<LocalDate> week : calendar) {
			eventWeek = new ArrayList<Events>();
			for (LocalDate date : week) {
				Events dailyEvent = new Events();
				dailyEvent.setFormattedDate(date);
				for(Events event : eventsList){
					if(event.getFormattedDate().equals(date)){
						dailyEvent.setEvent(event.getEvent());
					}
				}
				eventWeek.add(dailyEvent);
			}
			eventCalendar.add(eventWeek);
		}
		return eventCalendar;
	}

	/**
	 * 月初及び月末をLocalDateオブジェクトのリストで返す.
	 * 
	 * @param 該当する月初及び月末を取得したい任意の日付オブジェクト.
	 * @return 月初[0]及び月末[1]オブジェクトを格納したリスト(ArrayList).
	 */
	private List<LocalDate> mkFirstAndLastDayOfMonth(LocalDate createdLocalDate) {

		// 月初オブジェクトを作成.
		LocalDate firstDayOfMonth = LocalDate.of(createdLocalDate.getYear(), createdLocalDate.getMonthValue(), 1);
		// その月の長さを元に月末オブジェクトを作成.
		LocalDate lastDayOfMonth = LocalDate.of(firstDayOfMonth.getYear(), firstDayOfMonth.getMonthValue(),
				firstDayOfMonth.lengthOfMonth());

		List<LocalDate> firstAndLastDay = new ArrayList<LocalDate>();
		firstAndLastDay.add(firstDayOfMonth);
		firstAndLastDay.add(lastDayOfMonth);

		return firstAndLastDay;
	}

	private List<List<LocalDate>> makeCalendar(List<LocalDate> firstAndLastDay) {

		List<List<LocalDate>> calendar = new ArrayList<List<LocalDate>>();
		List<LocalDate> week = null;

		LocalDate firstDayOfMonth = firstAndLastDay.get(0);
		LocalDate lastDayOfMonth = firstAndLastDay.get(1);

		int firstDayOfCalendar = firstDayOfMonth.getDayOfWeek().getValue();

		// 週を表すリストを月曜日から始めたいので、月初が月曜日でない場合,
		// その週の月曜日を初日とする.
		if (firstDayOfCalendar != CalendarDays.MONDAY.dayNum) {
			firstDayOfMonth = firstDayOfMonth.minusDays(firstDayOfCalendar - 1);
		}

		for (int i = 0; i <= 100; ++i) {
			LocalDate d = firstDayOfMonth.plusDays(i);
			int wd = d.getDayOfWeek().getValue();
			if (wd == CalendarDays.MONDAY.dayNum) {
				week = new ArrayList<LocalDate>();
			}
			week.add(d);
			if (CalendarDays.SUNDAY.isEnd(wd)) {
				calendar.add(week);
				week = null;
			}
			if (d.equals(lastDayOfMonth)) {
				System.out.println(d);
				break;
			}
		}

		int wd = lastDayOfMonth.getDayOfWeek().getValue();
		//その月が日曜日で終了していない場合、
		//来月の日付をリストに格納して数合わせする.
		if (!(CalendarDays.SUNDAY.isEnd(wd))) {
			for (int i = 1; i <= 7 - wd; ++i) {
				LocalDate d = lastDayOfMonth.plusDays(i);
				week.add(d);
			}
			calendar.add(week);
		}

		return calendar;
	}
	
	public enum CalendarDays{
		MONDAY(1),
		SUNDAY(7);
		
		public boolean isEnd(int n){
			if(n == SUNDAY.dayNum){
				return true;
			}else{
				return false;
			}
		}
		
		private final int dayNum;
		
		CalendarDays(int n){
			this.dayNum = n;
		}
		
		public int dayNum(){
			return dayNum;
		}
		
	}
}
