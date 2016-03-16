<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>在线投稿系统</title>
    <link rel="stylesheet" href="css/font-awesome.css"/>
    <link rel="stylesheet" href="css/jquery.dataTables.css"/>
    <link rel="stylesheet" href="css/main.css"/>
</head>
<body>
<%@include file="header.jsp" %>
<div class="frame_main">
    <%@include file="nav.jsp" %>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            稿件中心 &gt; 稿件查询</h2>
        <div class="frame_main_center">
            <div class="doc_filter  docList_filter">
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">投稿时间：</span><input type="date" class="doc_text"
                                                                           id="docList_fromTime"><span
                        class="doc_filter_item_link">至</span><input type="date" class="doc_text" id="docList_toTime">
                </div>
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">标题：</span><input type="text" class="doc_text"
                                                                         id="docList_zhTitle">
                </div>
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">稿件类别：</span>
                    <select name="classification" id="docList_classification" class="doc_select">
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
                </div>
            </div>
            <div class="doc_filter docList_filter">
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">关键字：</span><input type="text" class="doc_text"
                                                                          id="docList_zhKeyword">
                </div>
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">投稿类型：</span>
                    <select id="docList_type" class="doc_select">
                        <option value="">请选择</option>
                        <option value="自由来稿">自由来稿</option>
                        <option value="特约稿">特约稿</option>
                        <option value="其他">其他</option>
                    </select>
                </div>
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">稿件状态：</span>
                    <select id="docList_status" class="doc_select">
                        <option value="">请选择</option>
                        <option value="待查看">待查看</option>
                        <option value="审阅中">审阅中</option>
                        <option value="退修稿">退修稿</option>
                        <option value="已采编">已采编</option>
                    </select>
                </div>
                <div class="doc_filter_item">
                    <a href="javascript:;" class="doc_btn" id="docList_table_searchBtn">查询</a>
                </div>
            </div>
            <table id="docTable" class="display com-dataTableInit" cellspacing="0" style="display: none;">
                <thead>
                <tr>
                    <th style="width: 50px;">编号</th>
                    <th style="width: 100px;">投稿时间</th>
                    <th style="width: 150px;">标题</th>
                    <th>关键字</th>
                    <th style="width: 80px;">稿件类别</th>
                    <th style="width: 80px;">投稿类型</th>
                    <th style="width: 80px;">稿件状态</th>
                    <th style="width: 50px;">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/jquery.dataTables.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function () {
        doc.docInfo.dataTable = $('#docTable').dataTable({
            "bServerSide": true,  //启动服务端分页、排序，搜索等功能
            "sAjaxSource": 'doc.json',  //ajax请求路径
            "aaSorting": [[1, 'desc']],  //初始化时默认的排序字段
            //修改参数
            "fnServerParams": function (aoData) {
                aoData.push(
                        {"name": "search_fromTime", "value": $('#docList_fromTime').val()},
                        {"name": "search_toTime", "value": $('#docList_toTime').val()},
                        {"name": "search_zhTitle", "value": $('#docList_zhTitle').val()},
                        {"name": "search_zhKeyword", "value": $('#docList_zhKeyword').val()},
                        {"name": "search_classification", "value": $('#docList_classification').val()},
                        {"name": "search_type", "value": $('#docList_type').val()},
                        {"name": "search_status", "value": $('#docList_status').val()});
            },
            "fnServerData": doc.tool.retrieveData,//自定义ajax函数
            //格式化列
            "aoColumns": [
                {"mData": "id", "bSortable": false},
                {"mData": "postTime"},
                {"mData": "zhTitle", "bSortable": false},
                {"mData": "zhKeyword", "bSortable": false},
                {"mData": "classification", "bSortable": false},
                {"mData": "type", "bSortable": false},
                {"mData": "status", "bSortable": false},
                {"mData": "id", "bSortable": false}
            ],
            "fnRowCallback": function (nRow, oData, iDisplayIndex, iDisplayIndexFull) {
                $('td:eq(0)', nRow).html(iDisplayIndex + 1);
                $('td:eq(2)', nRow).html('<a href="javascript:;" data-id="' + oData.id + '">' + oData.zhTitle + '</a>');
                $('td:eq(7)', nRow).html('<i class="doc_icon icon-trash" data-id="' + oData.id + '"></i>');
                switch (oData.status) {
                    case "待审查":
                        $('td:eq(6)', nRow).html('<span class="docList_status1">待审查</span>');
                        $('td:eq(7)', nRow).html('<a href="submitDoc.html"><i class="doc_icon icon-edit" style="margin-right:0" data-id="' + oData.id + '"></i></a><i class="doc_icon icon-trash"  data-id="' + oData.id + '"></i>');
                        break;
                    case "审阅中":
                        $('td:eq(6)', nRow).html('<span class="docList_status2">审阅中</span>');
                        break;
                    case "退修稿":
                        $('td:eq(6)', nRow).html('<span class="docList_status3">退修稿</span>');
                        break;
                    case "已采编":
                        $('td:eq(6)', nRow).html('<span class="docList_status4">已采编</span>');
                        break;
                }
                return nRow;
            }
        }).show();
        doc.docInfo.dataTableEvent();
        doc.app.remindBoxEvent();
        doc.app.confirmBoxEvent();
    });
</script>
</body>
</html>