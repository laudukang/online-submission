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
            稿件中心 &gt; 我要投稿</h2>
        <div class="frame_main_center">
            <form action="newDoc" method="post" ENCTYPE="multipart/form-data">
                <table class="doc_table submitDoc_authorTable">
                    <tbody class="submitDoc_authorBody">
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
                            <input type="text" class="doc_text" name="authorDomainList[0].name" value="" required><span
                                class="doc_table_tip">*</span>
                        </td>
                        <td style="width: 80px;">
                            性别：
                        </td>
                        <td class="doc_td_Active">
                            <label style="margin-right: 10px;"><input type="radio" name="authorDomainList[0].sex"
                                                                      checked="checked"
                                                                      value="男" style="margin-right: 3px">男</label>
                            <label><input type="radio" name="authorDomainList[0].sex" value="女"
                                          style="margin-right: 3px">女</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            出生年月：
                        </td>
                        <td class="doc_td_Active">
                            <input type="date" class="doc_text" name="authorDomainList[0].birth">
                        </td>
                        <td>
                            电子邮箱：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="authorDomainList[0].mail" value="" required
                                   pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"><span
                                class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            工作电话：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="authorDomainList[0].officePhone" value=""
                                   placeholder="如：010-12345678"
                                   pattern="^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$">
                        </td>
                        <td>
                            手机：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="authorDomainList[0].mobilePhone" value="" required
                                   pattern="^1[3|4|5|8]\d{9}$"><span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            所在地区：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text doc_text_Small" name="authorDomainList[0].country"
                                   required/><span
                                style="padding: 0 5px">国</span>
                            <input type="text" class="doc_text doc_text_Small" name="authorDomainList[0].province"
                                   required/><span
                                style="padding: 0 5px">省</span>
                            <input type="text" class="doc_text doc_text_Small" name="authorDomainList[0].city"
                                   required/><span
                                style="padding-left:5px;">市</span>
                            <span class="doc_table_tip">*</span>
                        </td>
                        <td>
                            邮编：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text" name="authorDomainList[0].postcode" value="" required
                                   pattern="^[1-9]\d{5}$"><span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            详细地址：
                        </td>
                        <td class="doc_td_Active" colspan="3">
                            <input type="text" class="doc_text" name="authorDomainList[0].address" value=""
                                   style="width: 568px;"
                                   required><span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <a href="javascript:;" id="submitDoc_addAuthorBtn"><i class="icon-plus-sign doc_icon"></i>添加更多作者</a>
                <table class="doc_table submitDoc_docTable">
                    <tbody>
                    <tr>
                        <td colspan="2" class="doc_title">
                            稿件信息：
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;">
                            中文标题：
                        </td>
                        <td class="doc_td_Active" style="width: 340px;">
                            <input type="text" class="doc_text doc_text_Big" name="zhTitle" value="" required
                                   maxlength="255"><span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            英文标题：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text doc_text_Big" name="enTitle" maxlength="255">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            投稿类型：
                        </td>
                        <td class="doc_td_Active">
                            <select name="type" class="doc_select" required>
                                <option value="">请选择</option>
                                <option value="自由稿件">自由稿件</option>
                                <option value="特约稿">特约稿</option>
                                <option value="其他">其他</option>
                            </select>
                            <span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            稿件类别：
                        </td>
                        <td class="doc_td_Active">
                            <select name="classification" class="doc_select" required>
                                <option value="">请选择</option>
                                <option value="小说">小说</option>
                                <option value="文学">文学</option>
                                <option value="文学">文学</option>
                                <option value="经济管理">经济管理</option>
                                <option value="青春">青春</option>
                                <option value="投资">投资</option>
                                <option value="亲子">亲子</option>
                                <option value="两性">两性</option>
                                <option value="旅游">旅游</option>
                                <option value="传记">传记</option>
                                <option value="时尚">时尚</option>
                                <option value="心理">心理</option>
                                <option value="社科">社科</option>
                                <option value="历史">历史</option>
                                <option value="军事">军事</option>
                                <option value="科普">科普</option>
                                <option value="文化">文化</option>
                                <option value="政治">政治</option>
                                <option value="法律">法律</option>
                                <option value="养生">养生</option>
                                <option value="风水">风水</option>
                                <option value="体育">体育</option>
                                <option value="自然">自然</option>
                                <option value="计算机">计算机</option>
                                <option value="工业">工业</option>
                                <option value="古典">古典</option>
                                <option value="工具">工具</option>
                                <option value="建筑">建筑</option>
                                <option value="美食">美食</option>
                                <option value="农林">农林</option>
                                <option value="漫画">漫画</option>
                                <option value="医学">医学</option>
                                <option value="艺术">艺术</option>
                                <option value="外语">外语</option>
                                <option value="哲学">哲学</option>
                                <option value="宗教">宗教</option>
                                <option value="其他">其他</option>
                            </select>
                            <span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            中文关键字：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text doc_text_Big" name="zhKeyword" value="" required
                                   placeholder="请以‘；’分隔关键字" maxlength="255"><span class="doc_table_tip">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            英文关键字：
                        </td>
                        <td class="doc_td_Active">
                            <input type="text" class="doc_text doc_text_Big" name="enKeyword" value=""
                                   placeholder="请以‘；’分隔关键字" maxlength="255">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            中文摘要：
                        </td>
                        <td class="doc_td_Active">
                            <textarea name="zhSummary" class="doc_textarea" style="width: 551px" maxlength="255"
                                      placeholder="最多输入255个字符"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            英文摘要：
                        </td>
                        <td class="doc_td_Active">
                            <textarea name="enSummary" class="doc_textarea" style="width: 551px" maxlength="255"
                                      placeholder="最多输入255个字符"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            上传附件：
                        </td>
                        <td>
                            <input type="file" id="submitDoc_updateBtn" value="上传稿件" name="uploadFile" required/>
                            <span class="doc_table_tip">*</span>
                            <span class="doc_remind_Blue">(仅支持pdf格式，且大小不超过10M)</span>
                        </td>
                    </tr>
                    <tr style="border-bottom: 4px solid #C4D8ED;">
                        <td colspan="2">
                            <input type="submit" value="发&nbsp;&nbsp;&nbsp;&nbsp;送"
                                   class="doc_btn  submitDoc_submitBtn"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function () {
        doc.docInfo.submitDoc();
    });
</script>
</body>
</html>