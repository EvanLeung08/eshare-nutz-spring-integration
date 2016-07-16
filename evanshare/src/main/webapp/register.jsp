<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
    
    
        <meta charset="utf-8">
        <title>E分享后台管理系统</title>
        <!-- CSS -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/reset.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/supersized.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">

        <!-- Javascript -->
        <script src="<%=request.getContextPath()%>/assets/js/jquery-1.8.2.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/supersized.3.2.7.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/supersized-init.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>

    </head>

    <body>

        <div class="page-container">
            <h1>注册</h1>
            <form action="<%=request.getContextPath()%>/user/login.do" method="post">
                <input type="text" name="user.username" class="username" placeholder="用户名">
                <input type="password" name="user.password" class="password" placeholder="密码">
                <input type="text" name="user.email" class="username" placeholder="邮箱">
                <button type="submit">注册</button>
                <button id="returnBtn" type="button">返回</button>
            </form>
        </div>

    </body>

</html>
<script type="text/javascript">
$(function(){
	
	var root ='<%=request.getContextPath()%>';
	 $("#returnBtn").click(function(){
		window.location.href=root+"/user/";
	});
	
	
})
</script>

