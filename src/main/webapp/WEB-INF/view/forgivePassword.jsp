<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>在线投稿系统</title>
    <link rel="stylesheet" href="css/main.css"/>
</head>
<body>
<header class="user_header">
    <h1>在线投稿系统</h1>
</header>
<div class="user_wrap">
    <div class="user_bg"></div>
    <form class="user_container forgivePassword" action="#" method="post">
        <ul class="user_content">
            <li class="forgivePassword_item">
                <h2 class="forgivePassword_title">找回密码</h2>
                <a class="forgivePassword_item_rightBtn" href="login.html">登录</a>
            </li>
            <li class="forgivePassword_item">请输入注册邮箱：</li>
            <li class="forgivePassword_item">
                <input type="text" name="account" class="doc_text doc_text_Large" required
                       pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"/>
            </li>
            <li class="forgivePassword_item">
                <input type="submit" class="doc_btn doc_btn_Blue" value="激话"/>
                <span class="user_remind remind">该邮箱非注册登记的邮箱</span>
            </li>
            <li class="forgivePassword_item remind" style="color:#3587C7;">
                请到注册邮箱接收激活邮件，并按步骤重置密码
            </li>
        </ul>
    </form>
</div>
</body>
</html>