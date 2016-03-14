<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/14 0014
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
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
            <img src="images/online/logo.png" alt="在线投稿系统" width="165" height="48">
            <h2 class="frame_header_title">在线投稿系统</h2>
        </a>
        <div class="frame_header_account">
            <span>用户名：欢迎你！</span>
            <a href="#">系统信息</a>
            <a href="#">修改密码</a>
            <a href="#">安全退出</a>
        </div>
    </div>
</div>
<div class="frame_main">
    <div class="frame_main_nav_wrap">
        <div class="frame_main_nav">
            <div class="frame_main_nav_title">
                <strong>稿件中心</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="#">我要投稿</a></li>
                <li><a href="#">稿件查询</a></li>
            </ul>
            <div class="frame_main_nav_title">
                <strong>个人中心</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="#">修改个人信息</a></li>
                <li><a href="#">修改登录密码</a></li>
            </ul>
            <div class="frame_main_nav_title">
                <strong>系统中心</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="#">系统信息</a></li>
            </ul>
        </div>
    </div>
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
                    <th style="width: 100px;">投稿类型</th>
                    <th style="width: 80px;">稿件状态</th>
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
                {"mData": "type", "bSortable": false},
                {"mData": "status", "bSortable": false},
                {"mData": "id", "bSortable": false}
            ],
            "fnRowCallback": function (nRow, oData, iDisplayIndex, iDisplayIndexFull) {
                $('td:eq(0)', nRow).html(iDisplayIndex + 1);
                if (oData.status == "待审查") {
                    $('td:eq(6)', nRow).html('<i class="doc_icon icon-edit" data-id="' + oData.id + '"></i><i class="doc_icon icon-trash" data-id="' + oData.id + '"></i>');
                } else {
                    $('td:eq(6)', nRow).html('<i class="doc_icon icon-trash" data-id="' + oData.id + '"></i>');
                }
                return nRow;
            }
        }).show();
        doc.docInfo.dataTableEvent();
    });
</script>
</body>
</html>
