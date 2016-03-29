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
                <strong>系统信息</strong>
                <div class="frame_main_nav_title_Bg"></div>
            </div>
            <ul class="frame_main_nav_list">
                <li><a href="">管理员管理</a></li>
                <li><a href="">审稿员管理</a></li>
                <li><a href="">用户管理</a></li>
                <li><a href="">角色管理</a></li>
                <li><a href="">系统日志</a></li>
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
            系统中心 &gt; 管理员管理 &gt; ${account}账号下的角色管理</h2>
        <div class="frame_main_center">
            <form action="#" method="post">
                <table class="doc_table doc_table_WidthMarginBottom doc_system_roleTable">
                    <thead>
                    <th class="doc_title" colspan="4" style="text-align: left;">角色管理</th>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="doc_sec_title" style="width: 30px;text-align: center;">操作</td>
                        <td class="doc_sec_title" style="width: 80px;">编号</td>
                        <td class="doc_sec_title" style="width: 120px;">角色名</td>
                        <td class="doc_sec_title">拥有权限</td>
                    </tr>
                    <tr>
                        <td style="text-align: center"><input type="checkbox" value="${id}" name="role"/></td>
                        <td>1</td>
                        <td>${name}</td>
                        <td>${permissionName}</td>
                    </tr>
                    <tr>
                        <td style="text-align: center"><input type="checkbox" value="${id}" name="role"/></td>
                        <td>2</td>
                        <td>${name}</td>
                        <td>${permissionName}</td>
                    </tr>
                    <tr>
                        <td style="text-align: center"><input type="checkbox" value="${id}" name="role"/></td>
                        <td>3</td>
                        <td>${name}</td>
                        <td>${permissionName}</td>
                    </tr>
                    <tr>
                        <td style="text-align: center"><input type="checkbox" value="${id}" name="role"/></td>
                        <td>4</td>
                        <td>${name}</td>
                        <td>${permissionName}</td>
                    </tr>
                    <tr>
                        <td colspan="4" style="border-bottom: 4px solid #C4D8ED;">
                            <input type="submit" id="doc_system_roleTable_submitBtn" class="doc_btn"
                                   style="margin: 5px 50px;" value="保存角色"/></td>
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
//        $('#doc_system_roleTable_submitBtn').on('click',function(){
//            var roleArr = [];
//            $('.doc_system_roleTable').find('input[type="checkbox"]:checked').each(function(){
//                roleArr.push($(this).val());
//            });
//            doc.tool.getJSON({
//                url:"data.json",
//                data:{'id':"${id}",'role':roleArr},
//                success:function(){
//                    $.remindBox({
//                        remind:"保存角色成功！"
//                    });
//                },
//                error:{
//                    remind:"保存角色出错"
//                }
//            });
//        });

        $('.doc_system_roleTable').delegate('tr', 'click', function () {
            var checkbox = $(this).find('input[type="checkbox"]');
            if (!checkbox.prop('checked')) {
                checkbox.prop('checked', true);
            } else {
                checkbox.prop('checked', false);
            }
        });
    });
</script>
</body>
</html>