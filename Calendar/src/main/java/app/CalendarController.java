package app;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import app.domain.CalendarService;
import app.model.Consts;

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
		model.addAttribute("calendar",calendarService.selectMonthlyEvents(now));
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
