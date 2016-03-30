<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="${pageContext.request.contextPath}" var="home" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>网络投稿系统</title>
    <link rel="stylesheet" href="${home}/css/main.css"/>
    <link rel="shortcut icon" href="${home}/favicon.ico"/>
</head>
<body>
<header class="user_header">
    <h1>网络投稿系统</h1>
</header>
<div class="user_wrap">
    <div class="user_bg"></div>
    <form class="user_container login" action="${home}/admin/login" method="post">
        <ul class="user_content">
            <li class="login_item">
                <h2 class="login_title" style="padding-bottom: 10px;">管理员登录</h2>
                <a class="register_item_rightBtn" href="${home}/login">用户登录</a>
            </li>
            <li class="login_item">
                <input class="doc_text doc_text_Large" type="text" name="account" placeholder="请输入注册邮箱" required
                       pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"/>
            </li>
            <li class="login_item">
                <input class="doc_text doc_text_Large" type="password" name="password" placeholder="密码(6到16位有效字符)"
                       required pattern="[\w]{6,16}"/>
            </li>
            <li class="login_item">
                <input type="submit" class="doc_btn doc_btn_Blue" value="登录"/>
                <span class="user_remind remind">${requestScope['org.springframework.validation.BindingResult.loginDomain'].getFieldError('account').getDefaultMessage()}</span>
            </li>
            <li class="login_item">
            </li>
        </ul>
    </form>
</div>
<script src="${home}/js/jquery.js"></script>
<script src="${home}/js/jQuery.md5.js"></script>
<script>
    $(function () {
        $('.login').on('submit', function () {
            var password = $(this).find('input[name="password"]').val();
            $(this).find('input[name="password"]').val($.md5(password));
        });
    });
</script>
</body>
</html>