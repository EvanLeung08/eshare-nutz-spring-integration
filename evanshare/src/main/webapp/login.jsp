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
            <h1>E 分 享</h1>
            <form action="<%=request.getContextPath()%>/user/login.do" method="post">
                <input type="text" name="username" class="username" placeholder="用户名">
                <input type="password" name="password" class="password" placeholder="密码">
                <button type="submit">登录</button>
                <button type="button" id="registerBtn">注册</button>
                <div class="error"><span>+</span></div>
            </form>
            <div class="connect">
                <p>Or connect with:</p>
                <p>
                    <a class="facebook" href="www.facebook.com"></a>
                    <a class="twitter" href="www.twitter.com"></a>
                </p>
            </div>
        </div>
		
      

    </body>

</html>
<script type="text/javascript">
$(function(){
	
	var root ='<%=request.getContextPath()%>';
	 $("#registerBtn").click(function(){
		window.location.href=root+"/user/toRegister.do";
	});
	
	
})
</script>

