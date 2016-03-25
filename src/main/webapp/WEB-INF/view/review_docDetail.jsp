<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25 0025
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>在线投稿系统</title>
    <link rel="stylesheet" href="css/font-awesome.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <style>
        body {
            position: relative;
        }
    </style>
</head>
<body>
<div class="frame_header">
    <div class="frame_header_banner">
        <a class="frame_header_logo" href="index.html">
            <img src="images/online/logo.png" alt="在线投稿系统">
            <h2 class="frame_header_title">在线投稿系统</h2>
        </a>
        <div class="frame_header_account">
            <span>用户名：欢迎你！</span>
            <a href="message.html">系统信息</a>
            <a href="updatePassword.html">修改密码</a>
            <a href="#">安全退出</a>
        </div>
    </div>
</div>
<div class="frame_main">
    <div class="frame_main_nav_wrap" id="docNav">
        <div class="frame_main_nav">
            <div class="frame_main_nav_title">
                <strong>稿件中心</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="admin_docList.html">稿件审阅</a></li>
            </ul>
            <div class="frame_main_nav_title">
                <strong>个人中心</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="admin_account.html">账号信息</a></li>
                <li><a href="admin_updatePassword.html">修改密码</a></li>
            </ul>
            <div class="frame_main_nav_title">
                <strong>系统中心</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="admin_message.html">系统信息</a></li>
            </ul>
        </div>
    </div>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            稿件中心 &gt; 稿件查询 &gt; ${稿件名字}</h2>
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
                <tr>
                    <td>${reviewTime}</td>
                    <td>${name}</td>
                    <td>${reviewResult}</td>
                    <td>${propose}</td>
                </tr>
                <tr>
                    <td>${reviewTime}</td>
                    <td>${name}</td>
                    <td>${reviewResult}</td>
                    <td>${propose}</td>
                </tr>
                <tr>
                    <td>${reviewTime}</td>
                    <td>${name}</td>
                    <td>${reviewResult}</td>
                    <td>${propose}</td>
                </tr>
                <tr>
                    <td>${reviewTime}</td>
                    <td>${name}</td>
                    <td>${reviewResult}</td>
                    <td>${propose}</td>
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
                        (该稿件于${postTime}投递成功，目前处于【${status}】状态)
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
                <tr>
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
            <form action="#" method="post">
                <table class="doc_table doc_table_WidthMarginBottom docInfo_reviewTable">
                    <tbody>
                    <tr>
                        <td colspan="2" class="doc_title">
                            评审稿件：<span class="doc_remind_Blue docInfo_resultInfo">${成功评阅}</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;">
                            建议：
                        </td>
                        <td class="doc_td_Active">
                            <select name="propose" class="doc_select" required>
                                <option value="">请选择</option>
                                <option value="拟采编">拟采编</option>
                                <option value="拟退稿">拟退稿</option>
                            </select>
                            <input type="hidden" name="id" value="${id}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            评语：
                        </td>
                        <td class="doc_td_Active">
                            <textarea class="doc_textarea" name="reviewResult"
                                      style="height: 100px;width: 550px;">${reviewResult}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="border-bottom: 4px solid #C4D8ED;">
                            <input type="submit" class="doc_btn docInfo_reviewTable_submitBtn">提&nbsp;&nbsp;&nbsp;&nbsp;交</input>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
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
