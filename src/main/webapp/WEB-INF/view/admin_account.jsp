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
            个人中心 &gt; 账户信息</h2>
        <div class="frame_main_center">
            <form action="${home}/admin/updateInfo" method="post">
                <table class="doc_table account_table">
                    <tbody>
                    <tr>
                        <td class="doc_title" colspan="2">
                            登录信息：&nbsp;&nbsp;&nbsp;&nbsp;<span class="doc_remind_Red"></span>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;">
                            个人账户：
                        </td>
                        <td class="doc_td_Active">
                            ${adminDomain.account}&nbsp;&nbsp;&nbsp;(账户名不允许修改)
                            <input name="id" value="${adminDomain.id}" hidden>
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
                            <input type="text" class="doc_text" name="name" value="${adminDomain.name}" required><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            性别：
                        </td>
                        <td class="doc_td_Active">
                            <label style="margin-right: 10px;"><input type="radio"
                                                                      name="sex" ${adminDomain.sex=="男"?"checked":""}
                                                                      value="男" style="margin-right: 3px">男</label>
                            <label><input type="radio" name="sex" value="女" ${adminDomain.sex=="女"?"checked":""}
                                          style="margin-right: 3px">女</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            出现日期：
                        </td>
                        <td class="doc_td_Active">
                            <input type="date" class="doc_text " name="birth" value="${adminDomain.birth}"
                                   required><span
                                class="doc_table_tip">*</span>
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
                            <input type="text" class="doc_text" name="mobilePhone" value="${adminDomain.mobilePhone}"
                                   required
                                   pattern="^1[3|4|5|8]\d{9}$"><span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            工作电话：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="officePhone" value="${adminDomain.officePhone}"
                                   placeholder="如：010-12345678"
                                   required
                                   pattern="((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)"><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            详细地址：
                        </td>
                        <td class="doc_td_Active" colspan="3">
                            <input type="text" class="doc_text" name="address" value="${adminDomain.address}"
                                   style="width: 420px;"
                                   required><span class="doc_table_tip">*</span>
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
                                      placeholder="最多输入255个字符">${adminDomain.remark}</textarea>
                        </td>
                    </tr>
                    <tr style="border-bottom: 4px solid #C4D8ED;">
                        <td colspan="2">
                            <input type="submit" value="保存个人信息" class="doc_btn  account_submitBtn"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<script src="${home}/js/jquery.js"></script>
<script src="${home}/js/common.js"></script>
<script src="${home}/js/main.js"></script>
<script>
    $(function () {
        $(function () {
            doc.tool.success(window.location.search, function (msg) {
                $('.doc_title .doc_remind_Red').html(msg);
            }, function (msg) {
                $('.doc_title .doc_remind_Red').html(msg);
            });
        });
    });
</script>
</body>
</html>
