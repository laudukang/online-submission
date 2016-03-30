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
    <link rel="stylesheet" href="${home}/css/font-awesome.css"/>
    <link rel="stylesheet" href="${home}/css/jquery.dataTables.css"/>
    <link rel="stylesheet" href="${home}/css/main.css"/>
</head>
<body>
<%@include file="header.jsp" %>
<div class="frame_main">
    <%@include file="nav.jsp" %>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            系统中心 &gt; 管理员管理 &gt; 新增管理员</h2>
        <div class="frame_main_center">
            <form action="${home}/admin/newAdmin" method="post" class="addAdmin">
                <table class="doc_table">
                    <tbody>
                    <tr>
                        <td class="doc_title" colspan="2">
                            登录信息：&nbsp;&nbsp;&nbsp;&nbsp;<span class="doc_remind_Red">${msg}</span>
                            <input type="hidden" name="reviewer" value="${adminDomain.reviewer}"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;">
                            个人账户：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" style="width: 240px;" name="account"
                                   placeholder="请输入有效邮箱" required
                                   pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"><span
                                class="doc_table_tip">*${requestScope['org.springframework.validation.BindingResult.adminDomain'].getFieldError('account').getDefaultMessage()}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            密码：
                        </td>
                        <td class="doc_td_Active">
                            <input type="password" class="doc_text" style="width: 240px;" id="password1" name="password"
                                   placeholder="6到16位有效字符" required pattern="[\w]{6,16}"><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            确认密码：
                        </td>
                        <td class="doc_td_Active">
                            <input type="password" class="doc_text" style="width: 240px;" id="password2"
                                   name="password1" placeholder="6到16位有效字符" required pattern="[\w]{6,16}"><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="doc_title" colspan="2">
                            个人信息：
                        </td>
                    </tr>
                    <tr>
                        <td>
                            姓名：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="name" required><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            性别：
                        </td>
                        <td class="doc_td_Active">
                            <label style="margin-right: 10px;"><input type="radio" name="sex" checked="checked"
                                                                      value="男" style="margin-right: 3px">男</label>
                            <label><input type="radio" name="sex" value="女" style="margin-right: 3px">女</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            出现日期：
                        </td>
                        <td class="doc_td_Active">
                            <input type="date" class="doc_text " name="birth" value=""><span
                                class="doc_table_tip"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="doc_title" colspan="2">
                            联系方式：
                        </td>
                    </tr>
                    <tr>
                        <td>
                            手机：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="mobilePhone" value=""
                                   pattern="^1[3|4|5|8]\d{9}$"><span class="doc_table_tip"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            工作电话：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="officePhone" value="" placeholder="如：010-12345678"
                                   pattern="((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)"><span
                                class="doc_table_tip"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            邮编：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="postcode" value=""
                                   pattern="^[1-9]\d{5}$"><span class="doc_table_tip"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            详细地址：
                        </td>
                        <td class="doc_td_Active" colspan="3">
                            <input type="text" class="doc_text" name="address" value="" style="width: 420px;">
                            <span class="doc_table_tip"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="doc_title" colspan="2">
                            备注信息：
                        </td>
                    </tr>
                    <tr>
                        <td>
                            备注：
                        </td>
                        <td class="doc_td_Active">
                            <textarea name="remark" class="doc_textarea" style="width: 420px" maxlength="255"
                                      placeholder="最多输入255个字符"></textarea>
                        </td>
                    </tr>
                    <tr style="border-bottom: 4px solid #C4D8ED;">
                        <td colspan="2">
                            <input type="submit" value="确定" class="doc_btn addAdmin_submitBtn"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<script src="${home}/js/jquery.js"></script>
<script src="${home}/js/jQuery.md5.js"></script>
<script src="${home}/js/common.js"></script>
<script src="${home}/js/main.js"></script>
<script>
    $(function () {
        $('#password2').on('change', function () {
            if ($(this).val() != $('#password1').val()) {
                $(this).get(0).setCustomValidity("两次输入的密码不匹配");
            } else {
                $(this).get(0).setCustomValidity("");
            }
        });

        $('.addAdmin').on('submit', function () {
            $('#password1').val($.md5($('#password1').val()));
            $('#password2').val($.md5($('#password2').val()));
        });
    });


</script>
</body>
</html>