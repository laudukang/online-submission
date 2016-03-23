<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            个人中心 &gt; 账户信息</h2>
        <div class="frame_main_center">
            <%--<form:form action="userInfo" modelAttribute="userDomain" method="post">--%>
            <form action="userInfo" method="post">
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
                            ${userDomain.account}&nbsp;&nbsp;&nbsp;(账户名不允许修改)
                            <input name="account" hidden value="${userDomain.account}">
                            <%--<form:input path="account" disabled="true" cssClass="doc_text"></form:input>&nbsp;&nbsp;&nbsp;(账户名不允许修改)--%>
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
                            <input type="text" class="doc_text" name="name" value="${userDomain.name}" required><span
                                class="doc_table_tip">*${requestScope['org.springframework.validation.BindingResult.userDomain'].getFieldError('name').getDefaultMessage()}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            性别：
                        </td>
                        <td class="doc_td_Active">
                            <label style="margin-right: 10px;"><input type="radio"
                                                                      name="sex" ${userDomain.sex=="男"?"checked":""}
                                                                      value="男" style="margin-right: 3px">男</label>
                            <label><input type="radio" name="sex" value="女" ${userDomain.sex=="女"?"checked":""}
                                          style="margin-right: 3px">女</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            出现日期：
                        </td>
                        <td class="doc_td_Active">
                            <input type="date" class="doc_text " name="birth" value="${userDomain.birth}" required><span
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
                            <input type="text" class="doc_text" name="mobilePhone" value="${userDomain.mobilePhone}"
                                   required
                                   pattern="^1[3|4|5|8]\d{9}$"><span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            工作电话：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="officePhone" value="${userDomain.officePhone}"
                                   placeholder="如：010-12345678"
                                   required pattern="^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$"><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            邮编：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="postcode" value="${userDomain.postcode}" required
                                   pattern="^[1-9]\d{5}$"><span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            所在地区：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text doc_text_Small" name="country"
                                   value="${userDomain.country}" required/><span
                                style="padding: 0 5px">国</span>
                            <input type="text" class="doc_text doc_text_Small" name="province"
                                   value="${userDomain.province}" required/><span
                                style="padding: 0 5px">省</span>
                            <input type="text" class="doc_text doc_text_Small" name="city" value="${userDomain.city}"
                                   required/><span
                                style="padding-left:5px;">市</span>
                            <span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            详细地址：
                        </td>
                        <td class="doc_td_Active" colspan="3">
                            <input type="text" class="doc_text" name="address" value="${userDomain.address}"
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
                                      placeholder="最多输入255个字符">${userDomain.remark}</textarea>
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
            <%--</form:form>--%>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function () {
        doc.tool.success(window.location.search,function(msg){
            $('.doc_title .doc_remind_Red').html(msg);
        },function(msg){
            $('.doc_title .doc_remind_Red').html(msg);
        });
    });
</script>
</body>
</html>