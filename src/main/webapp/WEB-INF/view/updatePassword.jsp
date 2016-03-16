<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>在线投稿系统</title>
    <link rel="stylesheet" href="css/main.css"/>
</head>
<body>
<%@include file="header.jsp" %>
<div class="frame_main">
    <%@include file="nav.jsp" %>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            个人中心 &gt; 修改密码</h2>
        <div class="frame_main_center">
            <form action="#" method="post" id="updatePassword_form">
                <table class="doc_table updatePassword_table">
                    <tbody>
                    <tr>
                        <td class="doc_title" colspan="2">
                            修改密码 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="doc_remind_Red">修改密码成功/失败信息</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 120px;">
                            原密码：
                        </td>
                        <td class="doc_td_Active">
                            <input type="password" class="doc_text doc_text_Big" id="password" name="password" required
                                   placeholder="请输入6-16位有效字符" pattern="^[\w]{6,16}$" maxlength="16"><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            新密码：
                        </td>
                        <td class="doc_td_Active">
                            <input type="password" class="doc_text doc_text_Big" name="newPassword1" id="newPassword1"
                                   required placeholder="请输入6-16位有效字符" pattern="^[\w]{6,16}$" maxlength="16"><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            确认新密码：
                        </td>
                        <td class="doc_td_Active">
                            <input type="password" class="doc_text doc_text_Big" name="newPassword2" id="newPassword2"
                                   required placeholder="请确认密码" pattern="^[\w]{6,16}$" maxlength="16"><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr style="border-bottom: 4px solid #C4D8ED;">
                        <td colspan="2">
                            <input type="submit" value="修改密码" class="doc_btn  updatePassword_submitBtn"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/jQuery.md5.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function () {
        $('#newPassword2').on('change', function () {
            if ($(this).val() != $('#newPassword1').val()) {
                $(this).get(0).setCustomValidity("两次输入的密码不匹配");
            } else {
                $(this).get(0).setCustomValidity("");
            }
        });

        $('#updatePassword_form').on('submit', function () {
            $('#password').val($.md5($('#password').val()));
            $('#newPassword1').val($.md5($('#newPassword1').val()));
            $('#newPassword2').val($.md5($('#newPassword2').val()));
        });
    });
</script>
</body>
</html>