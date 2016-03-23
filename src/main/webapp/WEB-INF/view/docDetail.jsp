<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>在线投稿系统</title>
    <link rel="stylesheet" href="css/font-awesome.css"/>
    <link rel="stylesheet" href="css/main.css"/>
</head>
<body>
<%@include file="header.jsp" %>
<div class="frame_main">
    <%@include file="nav.jsp" %>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            稿件中心 &gt; 稿件查询 &gt; ${osDoc.zhTitle}</h2>
        <div class="frame_main_center">
            <table class="doc_table doc_table_WidthMarginBottom">
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
                <c:if test="${osDoc.osDocAdmins.size()==0}">
                    <td colspan="4" style="text-align: center" class="doc_remind_Blue">稿件待审阅</td>
                </c:if>
                <c:if test="${osDoc.osDocAdmins.size()!=0}">
                    <c:forEach items="${osDoc.osDocAdmins}" var="osDocAdmin">
                        <tr>
                            <td>${osDocAdmin.reviewTime}</td>
                            <td>${osDocAdmin.osAdmin.name!=null&&osDocAdmin.osAdmin.name.trim()!=""?osDocAdmin.osAdmin.name:osDocAdmin.osAdmin.account}</td>
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
                            ${osAuthor.name}
                    </td>
                </tr>
                <tr>
                    <td>
                        所在地区：
                    </td>
                    <td class="doc_td_Active">
                            ${osAuthor.country}国${osAuthor.province}省${osAuthor.city}市
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
                        该稿件于${osDoc.postTime}投递成功，目前处于【${osDoc.status}】状态
                        <c:if test="${osDoc.status=='待审阅'}">
                            <a class="doc_btn" href="updateDoc?id=${osDoc.id}"
                               style="float:right;margin-right: 10px">修改</a>
                        </c:if>
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
                <tr style="border-bottom: 4px solid #C4D8ED;">
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
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/pdfobject.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function () {
        var pdfObject = new PDFObject({url: "/upload/" + "${osDoc.path}"}).embed("pdf");
    });
</script>
</body>
</html>