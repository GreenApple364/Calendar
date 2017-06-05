$(function() {
	$('#plusNewEventForm').click(
			function() {
				$('.newEventZone').find('tbody').append(
						"<tr><td><input type='date' name='date'></td>"
								+ "<td><input type='text' name='event'></td>"
								+ "</tr>")
			});
});