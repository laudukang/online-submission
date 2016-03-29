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
    <link rel="stylesheet" href="css/jquery.dataTables.css"/>
    <link rel="stylesheet" href="css/main.css"/>
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
                <li><a href="admin_docList.html">稿件查询</a></li>
                <li><a href="">已派发稿件</a></li>
            </ul>
            <div class="frame_main_nav_title">
                <strong>系统中心</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="admin_adminList.html">管理员管理</a></li>
                <li><a href="admin_reviewerList.html">审稿员管理</a></li>
                <li><a href="admin_userList.html">用户管理</a></li>
                <li><a href="admin_roleList.html">角色管理</a></li>
                <li><a href="admin_logList.html">系统日志</a></li>
            </ul>
            <div class="frame_main_nav_title">
                <strong>个人中心</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="admin_account.html">账号信息</a></li>
                <li><a href="admin_updatePassword.html">修改密码</a></li>
            </ul>
        </div>
    </div>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            系统中心 &gt; 角色管理</h2>
        <div class="frame_main_center">
            <div class="doc_filter_item" style="float: right;">
                <a href="admin_addRole.html" class="doc_btn" id="roleList_table_searchBtn">新增角色</a>
            </div>
            <table id="roleTable" class="display com-dataTableInit" cellspacing="0" style="display: none;">
                <thead>
                <tr>
                    <th style="width: 50px;">编号</th>
                    <th style="width: 130px;">角色名</th>
                    <th>所拥有权限</th>
                    <th style="width: 200px;">备注</th>
                    <th style="width: 80px;">操作</th>
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
        doc.system.roleDataTable = $('#roleTable').dataTable({
            "bServerSide": true,  //启动服务端分页、排序，搜索等功能
            "sAjaxSource": 'roleList.json',  //ajax请求路径
            "bSort": false,
            //修改参数
            "fnServerParams": function (aoData) {
                aoData.push();
            },
            "fnServerData": doc.tool.retrieveData,//自定义ajax函数
            //格式化列
            "aoColumns": [
                {"mData": "id", "bSortable": false},
                {"mData": "name", "bSortable": false},
                {"mData": "permission", "bSortable": false},
                {"mData": "remark", "bSortable": false},
                {"mData": "id", "bSortable": false}
            ],
            "fnRowCallback": function (nRow, oData, iDisplayIndex, iDisplayIndexFull) {
                $('td:eq(0)', nRow).html(iDisplayIndex + 1);
                $('td:eq(4)', nRow).html('<a href="admin_addRole.html?id=' + oData.id + '"><i class="doc_icon icon-edit" style="margin-right:0" data-id="' + oData.id + '"></i></a><i class="doc_icon icon-trash"  data-id="' + oData.id + '"></i>');
                return nRow;
            }
        }).show();
        doc.system.roleDataTableEvent();
        doc.app.confirmBoxEvent();
    });
</script>
</body>
</html>
