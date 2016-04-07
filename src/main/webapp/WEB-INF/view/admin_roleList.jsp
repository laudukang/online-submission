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
            系统中心 &gt; 角色管理</h2>
        <div class="frame_main_center">
            <div class="doc_filter_item" style="float: right;">
                <a href="${home}/admin/saveRole" class="doc_btn" id="roleList_table_searchBtn">新增角色</a>
            </div>
            <table id="roleTable" class="display com-dataTableInit" cellspacing="0" style="display: none;">
                <thead>
                <tr>
                    <th style="width: 50px;">编号</th>
                    <th style="width: 130px;">角色名</th>
                    <th style="width: 200px;">备注</th>
                    <th style="width: 80px;">操作</th>
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
        doc.system.roleDataTable = $('#roleTable').dataTable({
            "bServerSide": true,  //启动服务端分页、排序，搜索等功能
            "sAjaxSource": '${home}/admin/roles',  //ajax请求路径
            "aaSorting": [[1, 'desc']],  //初始化时默认的排序字段
            //修改参数
            "fnServerParams": function (aoData) {
                aoData.push();
            },
            "fnServerData": doc.tool.retrieveData,//自定义ajax函数
            //格式化列
            "aoColumns": [
                {"mData": "id", "bSortable": false},
                {"mData": "name"},
                {"mData": "remark", "bSortable": false},
                {"mData": "id", "bSortable": false}
            ],
            "fnRowCallback": function (nRow, oData, iDisplayIndex, iDisplayIndexFull) {
                $('td:eq(0)', nRow).html(iDisplayIndex + 1);
                $('td:eq(1)', nRow).html('<a href="${home}/admin/roleInfo/' + oData.id + '">' + oData.name + '</a>');
                if (oData.name != 'Administrator' && oData.name != 'Reviewer')
                    $('td:eq(3)', nRow).html('<a href="${home}/admin/roleInfo/' + oData.id + '"><i class="doc_icon icon-edit" style="margin-right:0" data-id="' + oData.id + '"></i></a><i class="doc_icon icon-trash"  data-id="' + oData.id + '"></i>');
                else
                    $('td:eq(3)', nRow).html('<a href="${home}/admin/roleInfo/' + oData.id + '"><i class="doc_icon icon-edit" style="margin-right:0" data-id="' + oData.id + '"></i></a>');
                return nRow;
            }
        }).show();
        doc.system.roleDataTableEvent();
        doc.app.confirmBoxEvent();
    });
</script>
</body>
</html>
