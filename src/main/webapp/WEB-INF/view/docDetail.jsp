<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            稿件中心 &gt; 稿件查询 &gt; ${稿件名字}</h2>
        <div class="frame_main_center">
            <table class="doc_table doc_table_WidthMarginBottom">
                <thead>
                <tr>
                    <th colspan="3" style="text-align: left;">审稿信息</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="doc_sec_title" style="width: 180px;">审稿时间</td>
                    <td class="doc_sec_title" style="width: 120px;">审稿员</td>
                    <td class="doc_sec_title">评语</td>
                </tr>
                <tr>
                    <td>${reviewTime}</td>
                    <td>${name}</td>
                    <td>${reviewResult}</td>
                </tr>
                <tr>
                    <td>${reviewTime}</td>
                    <td>${name}</td>
                    <td>${reviewResult}</td>
                </tr>
                <tr>
                    <td>${reviewTime}</td>
                    <td>${name}</td>
                    <td>${reviewResult}</td>
                </tr>
                <tr>
                    <td>${reviewTime}</td>
                    <td>${name}</td>
                    <td>${reviewResult}</td>
                </tr>
                </tbody>
            </table>
            <table class="doc_table doc_table_WidthMarginBottom">
                <tbody>
                <tr>
                    <td class="doc_title submitDoc_authorTable_title" colspan="4">
                        <h3>第1作者</h3>
                    </td>
                </tr>
                <tr>
                    <td style="width: 80px;">
                        姓名：
                    </td>
                    <td class="doc_td_Active" style="width: 340px;">
                        ${name}
                    </td>
                    <td style="width: 80px;">
                        性别：
                    </td>
                    <td class="doc_td_Active">
                        ${sex}
                    </td>
                </tr>
                <tr>
                    <td>
                        出生年月：
                    </td>
                    <td class="doc_td_Active">
                        ${birth}
                    </td>
                    <td>
                        电子邮箱：
                    </td>
                    <td class="doc_td_Active">
                        ${account}
                    </td>
                </tr>
                <tr>
                    <td>
                        工作电话：
                    </td>
                    <td class="doc_td_Active">
                        ${officePhone}
                    </td>
                    <td>
                        手机：
                    </td>
                    <td class="doc_td_Active">
                        ${name}
                    </td>
                </tr>
                <tr>
                    <td>
                        所在地区：
                    </td>
                    <td class="doc_td_Active">
                        ${country}国${province}省${city}市
                    </td>
                    <td>
                        邮编：
                    </td>
                    <td class="doc_td_Active">
                        ${postcode}
                    </td>
                </tr>
                <tr>
                    <td>
                        详细地址：
                    </td>
                    <td class="doc_td_Active" colspan="3">
                        ${address}
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="doc_table doc_table_WidthMarginBottom">
                <tbody>
                <tr>
                    <td style="width: 80px;" class="doc_title">
                        稿件信息：
                    </td>
                    <td class="doc_title doc_td_Active doc_remind_Blue">
                        (该稿件于${postTime}投递成功，目前处于【${status}】状态)<a class="doc_btn" href="updateDoc.html"
                                                                  style="float:right;margin-right: 10px">修改</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        中文标题：
                    </td>
                    <td class="doc_td_Active" style="width: 340px;">
                        ${zhTitle}
                    </td>
                </tr>
                <tr>
                    <td>
                        英文标题：
                    </td>
                    <td class="doc_td_Active">
                        ${enTitle}
                    </td>
                </tr>
                <tr>
                    <td>
                        投稿类型：
                    </td>
                    <td class="doc_td_Active">
                        ${type}
                    </td>
                </tr>
                <tr>
                    <td>
                        稿件类别：
                    </td>
                    <td class="doc_td_Active">
                        ${classification}
                    </td>
                </tr>
                <tr>
                    <td>
                        中文关键字：
                    </td>
                    <td class="doc_td_Active">
                        ${zhKeyword}
                    </td>
                </tr>
                <tr>
                    <td>
                        英文关键字：
                    </td>
                    <td class="doc_td_Active">
                        ${enKeyword}
                    </td>
                </tr>
                <tr>
                    <td>
                        中文摘要：
                    </td>
                    <td class="doc_td_Active">
                        ${zhSummary}
                    </td>
                </tr>
                <tr style="border-bottom: 4px solid #C4D8ED;">
                    <td>
                        英文摘要：
                    </td>
                    <td class="doc_td_Active">
                        ${enSummary}
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
        var pdfObject = new PDFObject({url: "/OnlineSubmission/update/test.pdf"}).embed("pdf");
    });
</script>
</body>
</html>