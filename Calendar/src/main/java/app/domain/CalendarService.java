package app.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.domain.CalendarService.CalendarDays;
import app.model.Events;
import app.model.NewEvent;

@Service
@Transactional
public class CalendarService {

	@Autowired
	CalendarRepository calendarRepository;

	// 1レコードに対して一回INSERT分を発行するようになってしまっているが、
	// 一回の操作で想定される発生レコード数が少ないのでひとまずこのような実装にしておく.
	public void insertNewEvents(ArrayList<NewEvent> events) {
		for (NewEvent e : events) {
			calendarRepository.insertNewEvents(e);
		}
	}

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

	public void removeDailyEvents(String removeDate) {

		calendarRepository.removeDailyEvents(java.sql.Date.valueOf(removeDate));

	}

	private List<List<Events>> mergeCalendarAndEvents(List<List<LocalDate>> calendar, List<Events> eventsList) {

		List<List<Events>> eventCalendar = new ArrayList<List<Events>>();
		List<Events> eventWeek = null;

		for (List<LocalDate> week : calendar) {
			eventWeek = new ArrayList<Events>();
			for (LocalDate date : week) {
				List<Events> eventsOfDate = new ArrayList<Events>();
				Events dailyEvent = new Events();
				dailyEvent.setFormattedDate(date);
				for (Events event : eventsList) {
					if (event.getFormattedDate().equals(date)) {
						eventsOfDate.add(event);
					}
				}
				dailyEvent.setEvents(eventsOfDate);
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

	/**
	 * 指定した月に関して日付オブジェクトの二次元リストを作成する。<br>
	 * 一つのリストには7日ぶんのローカル日付オブジェクトが格納される。<br>
	 * ある週について7日に満たない場合には前後の月の日付オブジェクトを取得して数合わせをする<br>
	 * 
	 * @param 作成したい月の初日を末日のローカル日付オブジェクト(java.time.LocalDate)
	 * @return その月を表すローカル日付オブジェクトの二次元リスト.
	 */
	private List<List<LocalDate>> makeCalendar(List<LocalDate> firstAndLastDay) {

		List<List<LocalDate>> calendar = new ArrayList<List<LocalDate>>();
		List<LocalDate> week = null;

		LocalDate firstDayOfMonth = firstAndLastDay.get(0);
		LocalDate lastDayOfMonth = firstAndLastDay.get(1);

		int firstDayOfCalendar = firstDayOfMonth.getDayOfWeek().getValue();

		// 週を表すリストを日曜日から始めたいので、月初が日曜日でない場合,
		// その週の日曜日を初日とする.
		if (firstDayOfCalendar != CalendarDays.SUNDAY.dayNum) {
			firstDayOfMonth = firstDayOfMonth.minusDays(firstDayOfCalendar);
		}

		for (int i = 0; i <= 100; ++i) {
			LocalDate d = firstDayOfMonth.plusDays(i);
			int wd = d.getDayOfWeek().getValue();
			if (wd == CalendarDays.SUNDAY.dayNum) {
				week = new ArrayList<LocalDate>();
			}
			week.add(d);
			if (CalendarDays.SATURDAY.isEnd(wd)) {
				calendar.add(week);
				week = null;
			}
			if (d.equals(lastDayOfMonth)) {
				break;
			}
		}

		int wd = lastDayOfMonth.getDayOfWeek().getValue();
		// その月が日曜日で終了していない場合、
		// 来月の日付をリストに格納して数合わせする.
		if (!(CalendarDays.SATURDAY.isEnd(wd))) {
			int eol = CalendarDays.valueOf(String.valueOf(java.time.DayOfWeek.of(wd))).calendarEndNum;
			for (int i = 1; i <= eol; ++i) {
				LocalDate d = lastDayOfMonth.plusDays(i);
				week.add(d);
			}
			calendar.add(week);
		}

		return calendar;
	}

	public enum CalendarDays {
		MONDAY(1, 5), TUESDAY(2, 4), WEDNESDAY(3, 3), THURSDAY(4, 2), FRIDAY(5, 1), SATURDAY(6, 0), SUNDAY(7, 6);

		public boolean isEnd(int n) {
			if (n == SATURDAY.dayNum) {
				return true;
			} else {
				return false;
			}
		}

		final int dayNum;
		/** その曜日が最終日であった時、何日足せば7日になるかの調整に使用する（土曜日はカレンダー上7日目になるので0） */
		private final int calendarEndNum;

		CalendarDays(int n, int m) {
			this.dayNum = n;
			this.calendarEndNum = m;
		}

		public int dayNum() {
			return dayNum;
		}

	}
}
