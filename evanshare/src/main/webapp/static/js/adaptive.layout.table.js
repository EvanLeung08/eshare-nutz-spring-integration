/**
 * DWZ列表页面表格LayoutH自适应修正
 */
setTimeout(function(){
	var layoutH = 0;
	if (_$('>#pagerForm').size() > 0) {	//存在分页栏，表示列表页
		layoutH = _$('.pageHeader').outerHeight(true) + _$('.panelBar').outerHeight(true) * 2 + _$('.gridHeader').outerHeight(true) + _$('.statisticsBar').outerHeight(true);
		_$('.gridScroller').attr('layoutH', layoutH);
		
	} else {
		_$('>.pageContent').siblings().each(function() {
			layoutH += $(this).outerHeight(true);
		});
		_$('.pageFormContent').attr('layoutH', layoutH + _$('.formBar').outerHeight(true) + ( _$('.pageFormContent').outerHeight(true) -  _$('.pageFormContent').height()) );
		
	}
	
	_$('[layoutH]').layoutH();
	
}, 1);


/**
 * 数据列表查询框清空
 */
navTab.getCurrentPanel().delegate('.empty,:reset', 'click', function() {
	var form = $(this).parents('form');
	form.find(':input')
		.not(':button, :submit, :reset, :hidden')
		.each(function(){
			$(this).val('').removeAttr('checked').removeAttr('selected');
		}
	);
	return false;
});

