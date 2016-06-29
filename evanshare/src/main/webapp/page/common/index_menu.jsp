<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>简单实用国产jQuery UI框架 - DWZ富客户端框架(J-UI.com)</title>

<link href="<%=request.getContextPath()%>/static/js/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/static/js/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/static/js/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="<%=request.getContextPath()%>/static/js/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<style type="text/css">
	#header{height:85px}
	#leftside, #container, #splitBar, #splitBarProxy{top:90px}
</style>

<!--[if lt IE 9]><script src="js/speedup.js" type="text/javascript"></script><script src="js/jquery-1.11.3.min.js" type="text/javascript"></script><![endif]-->
<!--[if gte IE 9]><!--><script src="<%=request.getContextPath()%>/static/js/dwz/js/jquery-2.1.4.min.js" type="text/javascript"></script><!--<![endif]-->

<script src="<%=request.getContextPath()%>/static/js/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/dwz/xheditor/xheditor-1.2.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/dwz/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/js/dwz/bin/dwz.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>


<script type="text/javascript">
$(function(){
	DWZ.init("<%=request.getContextPath()%>/dwz/dwz.frag.xml", {
		loginUrl:"login_dialog.jsp", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		keys: {statusCode:"statusCode", message:"message"}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"themes"});
			setTimeout(function() {$("#sidebar .toggleCollapse div").trigger("click");}, 10);
		}
	});
});
</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="http://j-ui.com">标志</a>
				<ul class="nav">
					<li id="switchEnvBox"><a href="javascript:">（<span>北京</span>）切换城市</a>
						<ul>
							<li><a href="sidebar_1.html">北京</a></li>
							<li><a href="sidebar_2.html">上海</a></li>
							<li><a href="sidebar_2.html">南京</a></li>
							<li><a href="sidebar_2.html">深圳</a></li>
							<li><a href="sidebar_2.html">广州</a></li>
							<li><a href="sidebar_2.html">天津</a></li>
							<li><a href="sidebar_2.html">杭州</a></li>
						</ul>
					</li>
					<li><a href="<%=request.getContextPath()%>/login.jsp">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<!--<li theme="red"><div>红色</div></li>-->
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>
		
			<div id="navMenu">
				<ul>
					<li class="selected"><a href="<%=request.getContextPath()%>/dwz/sidebar_1.jsp"><span>资讯管理</span></a></li>
					<li><a href="<%=request.getContextPath()%>/dwz/sidebar_2.jsp"><span>订单管理</span></a></li>
					<li><a href="<%=request.getContextPath()%>/dwz/sidebar_1.jsp"><span>产品管理</span></a></li>
					<li><a href="<%=request.getContextPath()%>/dwz/sidebar_2.jsp"><span>会员管理</span></a></li>
					<li><a href="<%=request.getContextPath()%>/dwz/sidebar_1.jsp"><span>服务管理</span></a></li>
					<li><a href="<%=request.getContextPath()%>/dwz/sidebar_2.jsp"><span>系统设置</span></a></li>
				</ul>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2><span>Folder</span>界面组件</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="<%=request.getContextPath()%>/dwz/tabsPage.jsp" target="navTab">主框架面板</a>
								<ul>
									<li><a href="<%=request.getContextPath()%>/dwz/main.jsp" target="navTab" rel="main">我的主页</a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>

			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2010 <a href="demo_page2.html" target="dialog">E分享后台管理</a></div>

</body>
</html>