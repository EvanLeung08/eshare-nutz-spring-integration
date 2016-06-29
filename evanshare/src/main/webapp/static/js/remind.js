// 渲染到期提醒标识
function renderRemindTag(){
	$('.tag').each(function() {
		var tag = $(this);
		var diffDate = $('input', tag).val();
		if (diffDate <= 0) {
			tag.text('已经到期');
			tag.addClass('overdue');
		} else if (diffDate <= 3) {
			// 已经到期
			tag.text('3天到期');
			tag.addClass('threeDays');
		} else if (diffDate <= 7) {
			tag.text('7天到期');
			tag.addClass('sevenDays');
		} else if (diffDate <= 15) {
			tag.text('15天到期');
			tag.addClass('fifteenDays');
		} else if (diffDate <= 30) {
			tag.text('30天到期');
			tag.addClass('thirtyDays');
		}
	});
}