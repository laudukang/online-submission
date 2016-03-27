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
            系统中心 &gt; 系统信息</h2>
        <div class="frame_main_center">
            <table id="messageTable" class="display com-dataTableInit" cellspacing="0" style="display: none;">
                <thead>
                <tr>
                    <th style="width: 50px;">编号</th>
                    <th style="width: 180px;">时间</th>
                    <th style="width: 150px;">标题</th>
                    <th>内容</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<script src="${home}/js/jquery.js"></script>
<script src="${home}/js/jquery.dataTables.js"></script>
<script src="${home}/js/common.js"></script>
<script src="${home}/js/main.js"></script>
<script>
    $(function () {
        doc.message.dataTable = $('#messageTable').dataTable({
            "bServerSide": false,  //启动服务端分页、排序，搜索等功能
            "paging": false,
            "sAjaxSource": '${home}${loginType=='0'?'/userMessages':'/admin/adminMessages'}',  //ajax请求路径
            //修改参数
            "fnServerParams": function (aoData) {
            },
            "fnServerData": doc.tool.retrieveData,//自定义ajax函数
            //格式化列
            "aoColumns": [
                {"mData": "id", "bSortable": false},
                {"mData": "postTime", "bSortable": false},
                {"mData": "title", "bSortable": false},
                {"mData": "content", "bSortable": false}
            ],
            "fnRowCallback": function (nRow, oData, iDisplayIndex, iDisplayIndexFull) {
                $('td:eq(0)', nRow).html(iDisplayIndex + 1);
                return nRow;
            }
        }).show();
    });
</script>
</body>
</html>