<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<form id="pagerForm" method="post" action="<%=request.getContextPath()%>/user/list">
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pager.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="<%=request.getContextPath()%>/sql/exportData" method="post">
	</form>
</div>
<div class="pageContent">
	<form action="<%=request.getContextPath()%>/spring/sql/exportData" method="post">
	<fieldset>
		<legend>多行文本框(文本域)</legend>
		<dl class="nowrap">
			<dt>普通文本框：</dt>
			<dd><textarea name="querySql" cols="80" rows="10"></textarea></dd>
		</dl>
		
	</fieldset>
	
	<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">导出报表</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
</form>
</div>
