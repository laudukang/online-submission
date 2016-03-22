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
    <form class="user_container register" action="#" method="post">
        <ul class="user_content">
            <li class="register_item">
                <h2 class="register_title">用户注册</h2>
                <a class="register_item_rightBtn" href="login.html">登录</a>
            </li>
            <li class="register_item">
                <input type="text" name="mail" class="doc_text doc_text_Large" placeholder="请输入有效邮箱" required
                       pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"/>
            </li>
            <li class="register_item">
                <input type="password" id="password1" name="password1" class="doc_text doc_text_Large"
                       placeholder="密码(6到16位有效字符)" required pattern="[\w]{6,16}"/>
            </li>
            <li class="register_item">
                <input type="password" id="password2" name="password2" class="doc_text doc_text_Large"
                       placeholder="请确认密码" required pattern="[\w]{6,16}"/>
            </li>
            <li class="register_item">
                <input type="submit" class="doc_btn doc_btn_Blue" value="注册"/>
                <span class="user_remind remind">${该邮箱已被注册}</span>
            </li>
        </ul>
    </form>
</div>
<script src="js/jquery.js"></script>
<script src="js/jQuery.md5.js"></script>
<script>
    $('#password2').on('change', function () {
        if ($(this).val() != $('#password1').val()) {
            $(this).get(0).setCustomValidity("两次输入的密码不匹配");
        } else {
            $(this).get(0).setCustomValidity("");
        }
    });

    $('.register').on('submit', function () {
        $('#password1').val($.md5($('#password1').val()));
        $('#password2').val($.md5($('#password2').val()));
    });
</script>
</body>
</html>