<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>网络投稿系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
</head>
<body>
<header class="user_header">
    <h1>网络投稿系统</h1>
</header>
<div class="user_wrap">
    <div class="user_bg"></div>
    <form class="user_container resetPassword" action="${pageContext.request.contextPath}/resetPassword" method="post">
        <ul class="user_content">
            <li class="resetPassword_item">
                <h2 class="resetPassword_title">重置密码</h2>
            </li>
            <li class="resetPassword_item">请输入新密码：</li>
            <li class="resetPassword_item">
                <input id="password1" type="password" name="password" class="doc_text doc_text_Large"
                       placeholder="密码(6到16位有效字符)" required pattern="[\w]{6,16}"/>
            </li>
            <li class="resetPassword_item">
                <input id="password2" type="password" name="password1" class="doc_text doc_text_Large"
                       placeholder="请确认新密码" required pattern="[\w]{6,16}"/>
            </li>
            <li class="resetPassword_item">
                <input type="submit" class="doc_btn doc_btn_Blue" value="重置"/>
                <span class="user_remind remind">${msg}</span>
            </li>
        </ul>
    </form>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js"></script>
<script>
    $('#password2').on('change', function () {
        if ($(this).val() != $('#password1').val()) {
            $(this).get(0).setCustomValidity("两次输入的密码不匹配");
        } else {
            $(this).get(0).setCustomValidity("");
        }
    });
    $('.resetPassword').on('submit', function () {
        $('#password1').val($.md5($('#password1').val()));
        $('#password2').val($.md5($('#password2').val()));
    });

    //    doc.tool.success(window.location.search,function(){
    //    },function(msg){
    //        $('.user_remind').html(msg);
    //    });

</script>
</body>
</html>