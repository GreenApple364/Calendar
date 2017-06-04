package app.model;

public class Consts {
	
	/** コンテキストパスを表す. */
	public static final String CONTEXT_PATH = "/";
	/** イベント追加処理のリクエストマッピング. */
	public static final String ADD_EVENTS_PATH = "/addEvent";
	public static final String PLUS_MONTH_PATH = "/plus";
	public static final String MINUS_MONTH_PATH = "/minus";
	
	/** 月次の日付オブジェクトのリスト（カレンダー）をmodelに格納する際のキー */
	public static final String CALENDAR_KEY = "calendar";
	/** カレンダーを表示させる年月を表す年月オブジェクトをmodelに格納する際のキー */
	public static final String MONTH_KEY = "month";
	
	/** WebページへForwardする. */
	public static final String TO_TOP_PAGE_FORWARD = "calendar";
}
