<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25 0025
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>网络投稿系统</title>
    <link rel="stylesheet" href="${home}/css/font-awesome.css"/>
    <link rel="stylesheet" href="${home}/css/main.css"/>
    <style>
        body {
            position: relative;
        }
    </style>
</head>
<body>
<%@include file="header.jsp" %>
<div class="frame_main">
    <%@include file="nav.jsp" %>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            稿件中心 &gt; 稿件查询 &gt; ${osDoc.zhTitle}</h2>
        <div class="frame_main_center">
            <table class="doc_table doc_table_WidthMarginBottom doc_table_review">
                <thead>
                <tr>
                    <th colspan="4" style="text-align: left;">审稿信息</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="doc_sec_title" style="width: 180px;">审稿时间</td>
                    <td class="doc_sec_title" style="width: 120px;">审稿员</td>
                    <td class="doc_sec_title">评语</td>
                    <td class="doc_sec_title" style="width: 80px;">建议</td>
                </tr>
                <c:if test="${empty osDoc.osDocAdmins}">
                    <td colspan="4" style="text-align: center" class="doc_remind_Blue">稿件待审阅</td>
                </c:if>
                <c:if test="${not empty osDoc.osDocAdmins}">
                    <c:forEach items="${osDoc.osDocAdmins}" var="osDocAdmin">
                        <c:if test="${osDocAdmin.osAdmin.id==adminid}">
                            <c:set value="${osDocAdmin.propose}" var="tmpPropose"></c:set>
                            <c:set value="${osDocAdmin.reviewResult}" var="tmpReviewResult"></c:set>
                        </c:if>
                        <tr>
                            <td><fmt:formatDate value="${osDocAdmin.reviewTime}" pattern="yyyy-MM-dd hh:MM:ss"/></td>
                            <td>${not empty osDocAdmin.osAdmin.name?osDocAdmin.osAdmin.name:osDocAdmin.osAdmin.account}</td>
                            <td>${osDocAdmin.reviewResult}</td>
                            <td>${osDocAdmin.propose}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <table class="doc_table doc_table_WidthMarginBottom">
                <c:forEach items="${osDoc.osAuthors}" var="osAuthor" varStatus="obj">
                    <tbody>
                    <tr>
                        <td class="doc_title submitDoc_authorTable_title" colspan="4">
                            <h3>第${obj.count}作者</h3>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;">
                            姓名：
                        </td>
                        <td class="doc_td_Active" style="width: 340px;">
                                ${osAuthor.name}
                        </td>
                        <td style="width: 80px;">
                            性别：
                        </td>
                        <td class="doc_td_Active">
                                ${osAuthor.sex}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            出生年月：
                        </td>
                        <td class="doc_td_Active">
                                ${osAuthor.birth}
                        </td>
                        <td>
                            电子邮箱：
                        </td>
                        <td class="doc_td_Active">
                                ${osAuthor.mail}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            工作电话：
                        </td>
                        <td class="doc_td_Active">
                                ${osAuthor.officePhone}
                        </td>
                        <td>
                            手机：
                        </td>
                        <td class="doc_td_Active">
                                ${osAuthor.mobilePhone}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            所在地区：
                        </td>
                        <td class="doc_td_Active">
                                ${osAuthor.country}&nbsp;国&nbsp;${osAuthor.province}&nbsp;省&nbsp;${osAuthor.city}&nbsp;市
                        </td>
                        <td>
                            邮编：
                        </td>
                        <td class="doc_td_Active">
                                ${osAuthor.postcode}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            详细地址：
                        </td>
                        <td class="doc_td_Active" colspan="3">
                                ${osAuthor.address}
                        </td>
                    </tr>
                    </tbody>
                </c:forEach>
            </table>
            <table class="doc_table doc_table_WidthMarginBottom">
                <tbody>
                <tr>
                    <td style="width: 80px;" class="doc_title">
                        稿件信息：
                    </td>
                    <td class="doc_title doc_td_Active doc_remind_Blue">
                        该稿件于<fmt:formatDate value="${osDoc.postTime}"
                                            pattern="yyyy-MM-dd hh:MM:ss"/>投递成功，目前处于【${osDoc.status}】状态
                    </td>
                </tr>
                <tr>
                    <td>
                        中文标题：
                    </td>
                    <td class="doc_td_Active" style="width: 340px;">
                        ${osDoc.zhTitle}
                    </td>
                </tr>
                <tr>
                    <td>
                        英文标题：
                    </td>
                    <td class="doc_td_Active">
                        ${osDoc.enTitle}
                    </td>
                </tr>
                <tr>
                    <td>
                        投稿类型：
                    </td>
                    <td class="doc_td_Active">
                        ${osDoc.type}
                    </td>
                </tr>
                <tr>
                    <td>
                        稿件类别：
                    </td>
                    <td class="doc_td_Active">
                        ${osDoc.classification}
                    </td>
                </tr>
                <tr>
                    <td>
                        中文关键字：
                    </td>
                    <td class="doc_td_Active">
                        ${osDoc.zhKeyword}
                    </td>
                </tr>
                <tr>
                    <td>
                        英文关键字：
                    </td>
                    <td class="doc_td_Active">
                        ${osDoc.enKeyword}
                    </td>
                </tr>
                <tr>
                    <td>
                        中文摘要：
                    </td>
                    <td class="doc_td_Active">
                        ${osDoc.zhSummary}
                    </td>
                </tr>
                <tr>
                    <td>
                        英文摘要：
                    </td>
                    <td class="doc_td_Active">
                        ${osDoc.enSummary}
                    </td>
                </tr>
                </tbody>
            </table>
            <div id="pdf" class="docInfo_pdf"></div>
            <form action="${home}/admin/saveReview" method="post">
                <table class="doc_table doc_table_WidthMarginBottom docInfo_reviewTable">
                    <tbody>
                    <tr>
                        <td colspan="2" class="doc_title">
                            评审稿件：<span class="doc_remind_Blue docInfo_resultInfo"> ${msg}</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;">
                            建议：
                        </td>
                        <td class="doc_td_Active">
                            <select name="propose" class="doc_select" required>
                                <option value="${not empty tmpPropose?tmpPropose:''}">${not empty tmpPropose?tmpPropose:'请选择'}</option>
                                <option value="拟采编">拟采编</option>
                                <option value="拟退稿">拟退稿</option>
                            </select>
                            <input type="hidden" name="id" value=" ${osDoc.id}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            评语：
                        </td>
                        <td class="doc_td_Active">
                            <textarea class="doc_textarea" name="reviewResult"
                                      style="height: 100px;width: 550px;">${tmpReviewResult}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="border-bottom: 4px solid #C4D8ED;">
                            <input type="submit" class="doc_btn docInfo_reviewTable_submitBtn"></input>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<script src="${home}/js/jquery.js"></script>
<script src="${home}/js/pdfobject.js"></script>
<script src="${home}/js/common.js"></script>
<script src="${home}/js/main.js"></script>
<script>
    $(function () {
        var pdfObject = new PDFObject({url: "${home}/upload/${osDoc.path}"}).embed("pdf");
    });
</script>
</body>
</html>
