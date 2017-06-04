package app;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import app.domain.CalendarService;
import app.model.Consts;
import app.model.Events;

@Controller
public class CalendarController {
	
	@Autowired
	CalendarService calendarService;

	/**
	 * 予定を取得したい年月を元に月別のカレンダーを作成する.<br>
	 * その際、ビジネスロジックにて日付に対応したイベントをDBに問い合わせ、<br>
	 * domain objectに格納されているようにする.<br>
	 * 初期状態では今月の予定が表示されているようにする.<br>
	 * @return 指定した年月の予定表を月曜から始まる週次の予定(Events)リストに分割したリスト
	 */
	@RequestMapping(value=Consts.CONTEXT_PATH)
	public String home(Model model){
		LocalDate now = LocalDate.now();
		model.addAttribute(Consts.CALENDAR_KEY,calendarService.selectMonthlyEvents(now));
		model.addAttribute("month",YearMonth.of(now.getYear(), now.getMonthValue()));
		return Consts.TO_TOP_PAGE_FORWARD;
	}
	
	@RequestMapping(value=Consts.PLUS_MONTH_PATH)
	public String plus(NewEventForm newEventForm, Model model){
		YearMonth plusOne = newEventForm.getViewingYearMonth().plusMonths(1);
		LocalDate selectValueDate = LocalDate.of(plusOne.getYear(), plusOne.getMonthValue(),1);
		model.addAttribute(Consts.MONTH_KEY, plusOne);
		model.addAttribute(Consts.CALENDAR_KEY,calendarService.selectMonthlyEvents(selectValueDate));
		return Consts.TO_TOP_PAGE_FORWARD;
	}
	
	@RequestMapping(value=Consts.MINUS_MONTH_PATH)
	public String minus(NewEventForm newEventForm, Model model){
		YearMonth minusOne = newEventForm.getViewingYearMonth().minusMonths(1);
		LocalDate selectValueDate = LocalDate.of(minusOne.getYear(), minusOne.getMonthValue(),1);
		model.addAttribute(Consts.MONTH_KEY, minusOne);
		model.addAttribute(Consts.CALENDAR_KEY,calendarService.selectMonthlyEvents(selectValueDate));
		return "calendar";
	}
	
	@RequestMapping(value=Consts.ADD_EVENTS_PATH)
	public String add(@Validated NewEventForm newEventForm,BindingResult result,Model model){
		
		if(result.hasErrors()){
			//入力チェックエラー時の処理
		}else{
			
		}
		
		return "calendar";
	}
}
