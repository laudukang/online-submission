<%@ page language="java" isErrorPage="true" import="java.io.PrintWriter" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>网络投稿系统-Exception</title>
    <link rel="shortcut icon" href="${home}/favicon.ico"/>
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="description" content="Exception">
</head>


<body style="background: #4CAF50; color: #ffffff; vertical-align: center;">
<div align="center">
    <br> <br>
    <h1>We are sorry for there are some errors occurred.</h1>
    <br> <a href="${pageContext.request.contextPath}">返回首页</a>
    <hr>
    <%
        Exception ex = (Exception) request.getAttribute("ex");
    %>
</div>

<h3>
    Exception:<%=ex.toString()%>
</h3>
<h3>
    Message:<%=ex.getMessage()%>
</h3>
<h3>
    <%
        ex.printStackTrace(new PrintWriter(out));
    %>
</h3>

<br>
<br>
</body>