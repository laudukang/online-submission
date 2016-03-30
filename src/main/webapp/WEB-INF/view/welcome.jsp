<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25 0025
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>网络投稿系统</title>
    <link rel="stylesheet" href="${home}/css/main.css"/>
</head>
<body>
<%@include file="header.jsp" %>
<div class="frame_main">
    <%@include file="nav.jsp" %>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            欢迎界面</h2>
        <div class="frame_main_center" style="text-align: center;padding-top: 50px;">
            <img src="${home}/images/online/logo.png" alt="logo"/>
            <h1 style="">欢迎使用网络投稿系统</h1>
        </div>
    </div>
</div>
</body>
</html>