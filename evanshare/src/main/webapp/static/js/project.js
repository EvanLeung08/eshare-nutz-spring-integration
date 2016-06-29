function disDate(oDate, iDate) {
	var ms = oDate.getTime();
	ms += iDate * 24 * 60 * 60 * 1000;
	return new Date(ms);
}

// 自动填写项目的结束时间
function set_endDate() {

	var startdate = _$("#project_startdate").val();
	if (startdate == null || startdate == "") {
		_$("#project_enddate").val("");
		return;
	}
	var timePar = startdate.split(' ');
	var timeDate = timePar[0].split('-');
	// var iNum = Number(0);
	bTime = timeDate[1] + '/' + timeDate[2] + '/' + timeDate[0];
	var later = new Date(bTime);
	// var startdate = new Date(startdate);
	var iNum = Number(0);

	if (!isNaN(_$("#project_limitday").val())) {
		iNum = _$("#project_limitday").val();
	}

	var oMyDate = disDate(later, iNum <= 0 ? 0 : iNum - 1);
	var enddatamonth = oMyDate.getMonth() + 1;
	var enddatadate = oMyDate.getDate();
	if (enddatamonth < 10) {
		enddatamonth = "0" + enddatamonth;
	}
	if (enddatadate < 10) {
		enddatadate = "0" + enddatadate;
	}

	var enddata = oMyDate.getFullYear() + "-" + enddatamonth + "-"
			+ enddatadate;
	_$('#project_enddate').val(enddata);

}

/**
 * 自动填写回购日期
 */
function set_buyBackDate() {
	// alert("end change1!");
	// debugger;
	var startdate = _$("#project_enddate").val();
	var buyBackDate = _$('#buybackdate').val();
	if (startdate == null || startdate == "") {
		return;
	}
	/*
	 * if(buyBackDate != null&&buyBackDate != ""){ return; }
	 */

	var timePar = startdate.split(' ');
	var timeDate = timePar[0].split('-');
	bTime = timeDate[1] + '/' + timeDate[2] + '/' + timeDate[0];
	var later = new Date(bTime);
	var iNum = Number(-2);
	var buyBackDateTemp = disDate(later, iNum);

	// 构造时间控件标准格式，并进行赋值
	var buyBackDateMonth = buyBackDateTemp.getMonth() + 1;
	var buyBackDateDate = buyBackDateTemp.getDate();
	if (buyBackDateMonth < 10) {
		buyBackDateMonth = "0" + buyBackDateMonth;
	}
	if (buyBackDateDate < 10) {
		buyBackDateDate = "0" + buyBackDateDate;
	}
	var buyBackDateStr = buyBackDateTemp.getFullYear() + "-" + buyBackDateMonth
			+ "-" + buyBackDateDate;

	_$('#buybackdate').val(buyBackDateStr);

}

/**
 * 还款方式的显示控制：0：按年，1：按月 add by zhaodegang 2015-3-23
 */
function repaymentkind() {
	var repaymentkind = _$("select[name='project.repaymentkind']").val();
	// 按年
	if (repaymentkind == 0) {

		_$("#repaymentday").val('0');

		// 季度的月数 值
		_$("#project_repaymentmonthorder").val("");

		// 制定时间
		_$("#tr_repaymentdate").show();

		// 制定周期
		_$("#tr_repaymentday").hide();

		// 季度的月数
		_$("#td_monthCount1").hide();
		_$("#td_monthCount2").hide();

	} else if (repaymentkind == 1) {
		_$("#project_repaymentmonthorder").val(0);
		_$("#repaymentdate").val("");
		_$("#tr_repaymentday").show();
		_$("#tr_repaymentdate").hide();
		_$("#td_monthCount1").hide();
		_$("#td_monthCount2").hide();
	} else if (repaymentkind == 2) {
		_$("#repaymentdate").val("");
		_$("#tr_repaymentday").show();
		_$("#tr_repaymentdate").hide();
		_$("#td_monthCount1").show();
		_$("#td_monthCount2").show();
	}
}

/**
 * 发票登记操作
 * @param _url
 * @param v
 */
function registerInvoice(_url,navTabId,v,index){
	var title = '';
	if(index == 1)
		title = "取消发票";
	else if(index ==0){
		title= "开发票";
	}
	alertMsg.confirm("确定要"+title + "吗",{
		okCall:function(){
			$.ajax({
				type:"POST",
				url:_url,
				dataType:"json",
				success:function(msg){
					if(msg.statusCode=='200'){
						alertMsg.correct(msg.message);
						 navTab.reloadFlag(navTabId);
					}
				}
			});
		}
	});
}