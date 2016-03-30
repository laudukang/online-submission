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
            系统中心 &gt; 管理员管理</h2>
        <div class="frame_main_center">
            <div class="doc_filter">
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">账号：</span><input type="text" class="doc_text"
                                                                         id="adminList_account">
                </div>
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">名字：</span><input type="text" class="doc_text"
                                                                         id="adminList_name">
                </div>
                <div class="doc_filter_item">
                    <a href="javascript:;" class="doc_btn" id="adminList_table_searchBtn">查询</a>
                </div>
                <div class="doc_filter_item">
                    <a href="${home}/admin/newAdmin?reviewer=1" class="doc_btn">新增管理员</a>
                </div>
            </div>
            <table id="adminTable" class="display com-dataTableInit" cellspacing="0" style="display: none;">
                <thead>
                <tr>
                    <th style="width: 50px;">编号</th>
                    <th style="width: 140px;">账号</th>
                    <th style="width: 100px;">名字</th>
                    <th style="width: 50px;">性别</th>
                    <th style="width: 90px;">工作电话</th>
                    <th>角色</th>
                    <th style="width: 50px">状态</th>
                    <th style="width: 50px;">操作</th>
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
        doc.system.adminDataTable = $('#adminTable').dataTable({
            "bServerSide": true,  //启动服务端分页、排序，搜索等功能
            "sAjaxSource": '${home}/admin/admins',  //ajax请求路径
            //修改参数
            "fnServerParams": function (aoData) {
                aoData.push(
                        {"name": "search_account", "value": $('#adminList_account').val()},
                        {"name": "search_name", "value": $('#adminList_name').val()});
            },
            "fnServerData": doc.tool.retrieveData,//自定义ajax函数
            //格式化列
            "aoColumns": [
                {"mData": "id", "bSortable": false},
                {"mData": "account", "bSortable": false},
                {"mData": "name", "bSortable": false},
                {"mData": "sex", "bSortable": false},
                {"mData": "officePhone", "bSortable": false},
                {"mData": "role", "bSortable": false},
                {"mData": "status", "bSortable": false},
                {"mData": "id", "bSortable": false}
            ],
            "fnRowCallback": function (nRow, oData, iDisplayIndex, iDisplayIndexFull) {
                $('td:eq(0)', nRow).html(iDisplayIndex + 1);
                $('td:eq(1)', nRow).html('<a href="${home}/admin/adminInfo/' + oData.id + '">' + oData.account + '</a>');
                $('td:eq(7)', nRow).html('<i class="doc_icon icon-lock" title="禁用" data-id="' + oData.id + '"></i><i class="doc_icon icon-trash" title="删除" data-id="' + oData.id + '"></i>');
                if (!oData.role) {
                    $('td:eq(5)', nRow).html('<a href="${home}/admin/adminRoleInfo/' + oData.id + '?account=' + oData.account + '">暂无角色,点击添加</a> ');
                } else {
                    $('td:eq(5)', nRow).html('<a href="${home}/admin/adminRoleInfo/' + oData.id + '?account=' + oData.account + '">' + oData.role + '</a>');
                }
                switch (oData.status) {
                    case '0':
                        $('td:eq(6)', nRow).html('<span class="doc_remind_Red">禁用</span>');
                        $('td:eq(7)', nRow).html('<i class="doc_icon icon-unlock" title="解禁" data-id="' + oData.id + '"></i><i class="doc_icon icon-trash" title="删除" data-id="' + oData.id + '"></i>');
                        break;
                    case '1':
                        $('td:eq(6)', nRow).html('正常');
                        break;
                }
                return nRow;
            }
        }).show();
        doc.app.confirmBoxEvent();
        doc.system.adminDataTableEvent();
        doc.app.remindBoxEvent();
    });
</script>
</body>
</html>