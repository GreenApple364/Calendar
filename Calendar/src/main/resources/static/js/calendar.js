$(function() {
	var counter = 1
	$('#plusNewEventForm').click(
			function() {
				$('.newEventZone').find('tbody').append(
						"<tr><td><input type='date' name='newEvents[" + counter + "].eventDate'></td>"
								+ "<td><input type='text' name='newEvents[" + counter + "].event'></td>"
								+ "</tr>");
				counter += 1
			});
});

//TODO:作成されたフォームごとにイベントリスナを付与して削除ボタンを押下した際に任意の行を削除できるようにする.