$(function() {
	var counter = 1
	$('#plusNewEventForm').click(
			function() {
				$('.newEventZone').find('tbody').append(
						"<tr>" + "<td><input type='date' name='newEvents["
								+ counter + "].eventDate'></td>"
								+ "<td><input type='text' name='newEvents["
								+ counter + "].event'></td>" + "<td style='font-size:14pt'>×</td>"
								+ "</tr>");
				counter += 1
			});

	//	全ての日付には削除用のフォームが仕込まれているが,予定が記入されている場合のみsubmit出来るようにする処理.
	$('#calendarZone td').hover(function() {
		if ($(this).find("p").length) {
			$(this).find("form").append("<button>削除</button>");
		}
	}, function() {
		$(this).find("button:last").remove();
	});

	$('.newEventZone table').on('click', 'tr td:nth-child(3)', function() {
		$(this).parent().remove();
	});

});
