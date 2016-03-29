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
            系统中心 &gt; 系统日志</h2>
        <div class="frame_main_center">
            <div class="doc_filter">
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">时间：</span><input type="date" class="doc_text"
                                                                         id="docLog_fromTime"><span
                        class="doc_filter_item_link">至</span><input type="date" class="doc_text" id="docLog_toTime">
                </div>
                <div class="doc_filter_item">
                    <span class="doc_filter_item_title">内容：</span><input type="text" class="doc_text"
                                                                         id="docLog_content">
                </div>
                <div class="doc_filter_item">
                    <a href="javascript:;" class="doc_btn" id="logList_table_searchBtn">查询</a>
                </div>
            </div>
            <table id="logTable" class="display com-dataTableInit" cellspacing="0" style="display: none;">
                <thead>
                <tr>
                    <th style="width: 50px;">编号</th>
                    <th style="width: 160px;">时间</th>
                    <th style="width: 80px;">操作者</th>
                    <th style="width: 130px;">IP地址</th>
                    <th>内容</th>
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
        doc.system.logDataTable = $('#logTable').dataTable({
            "bServerSide": true,  //启动服务端分页、排序，搜索等功能
            "sAjaxSource": 'logList.json',  //ajax请求路径
            "aaSorting": [[1, 'desc']],  //初始化时默认的排序字段
            //修改参数
            "fnServerParams": function (aoData) {
                aoData.push(
                        {"name": "search_fromTime", "value": $('#docLog_fromTime').val()},
                        {"name": "search_toTime", "value": $('#docLog_toTime').val()},
                        {"name": "search_content", "value": $('#docLog_content').val()});
            },
            "fnServerData": doc.tool.retrieveData,//自定义ajax函数
            //格式化列
            "aoColumns": [
                {"mData": "id", "bSortable": false},
                {"mData": "time"},
                {"mData": "userOrAdminName", "bSortable": false},
                {"mData": "ip", "bSortable": false},
                {"mData": "content", "bSortable": false}
            ],
            "fnRowCallback": function (nRow, oData, iDisplayIndex, iDisplayIndexFull) {
                $('td:eq(0)', nRow).html(iDisplayIndex + 1);
                return nRow;
            }
        }).show();
        doc.system.logDataTableEvent();
        doc.app.remindBoxEvent();
    });
</script>
</body>
</html>
