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
            系统中心 &gt; 角色管理 &gt; ${新增角色/修改角色}</h2>
        <div class="frame_main_center">
            <table class="doc_table doc_table_WidthMarginBottom doc_system_permissionTable">
                <tbody>
                <tr>
                    <td class="doc_title" colspan="4" style="text-align: left;">基本</td>
                </tr>
                <tr>
                    <td style="width:100px">
                        角色名称：
                    </td>
                    <td colspan="3">
                        <input type="text" name="name" id="roleName" class="doc_text" value="${name}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        备注：
                    </td>
                    <td colspan="3">
                        <textarea name="remark" id="roleRemark" class="doc_select"
                                  style="width: 450px;height: 80px;">${remark}</textarea>
                    </td>
                </tr>
                </tbody>
                <tbody>
                <tr>
                    <td class="doc_title" colspan="4" style="text-align: left;">权限信息</td>
                </tr>
                <tr>
                    <td class="doc_sec_title" style="width: 30px;text-align: center;">操作</td>
                    <td class="doc_sec_title" style="width: 80px;">编号</td>
                    <td class="doc_sec_title" style="width: 120px;">权限名</td>
                    <td class="doc_sec_title">权限备注</td>
                </tr>
                <tr>
                    <td style="text-align: center" colspan="4"><span class="doc_remind_Red">暂无权限可以分配</td>
                </tr>
                <tr>
                    <td style="text-align: center"><input type="checkbox" value="${id}"/></td>
                    <td>1</td>
                    <td>${name}</td>
                    <td>${remark}</td>
                </tr>
                <tr>
                    <td style="text-align: center"><input type="checkbox" value="${id}"/></td>
                    <td>2</td>
                    <td>${name}</td>
                    <td>${remark}</td>
                </tr>
                <tr>
                    <td style="text-align: center"><input type="checkbox" value="${id}"/></td>
                    <td>3</td>
                    <td>${name}</td>
                    <td>${remark}</td>
                </tr>
                <tr>
                    <td style="text-align: center"><input type="checkbox" value="${id}"/></td>
                    <td>4</td>
                    <td>${name}</td>
                    <td>${remark}</td>
                </tr>
                <tr>
                    <td style="text-align: center"><input type="checkbox" value="${id}"/></td>
                    <td>5</td>
                    <td>${name}</td>
                    <td>${remark}</td>
                </tr>
                <tr>
                    <td colspan="4" style="border-bottom: 4px solid #C4D8ED;">
                        <a id="doc_system_permissionTable_submitBtn" class="doc_btn"
                           style="margin: 5px 50px;">保存角色信息</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function () {
        $('#doc_system_permissionTable_submitBtn').on('click', function () {
            var permissionArr = [];
            $('.doc_system_permissionTable').find('input[type="checkbox"]:checked').each(function () {
                permissionArr.push($(this).val());
            });
            doc.tool.getJSON({
                url: "data.json",
                data: {
                    'id': "${id}",
                    'osPermission': permissionArr,
                    'name': $('#roleName').val(),
                    'remark': $('#roleRemark').val()
                },
                success: function () {
                    $.remindBox({
                        remind: "保存权限成功！"
                    });
                },
                error: {
                    remind: "保存权限出错"
                }
            });
        });

        $('.doc_system_permissionTable').delegate('tr', 'click', function () {
            var checkbox = $(this).find('input[type="checkbox"]');
            if (!checkbox.prop('checked')) {
                checkbox.prop('checked', true);
            } else {
                checkbox.prop('checked', false);
            }
        });
        doc.app.remindBoxEvent();
    });
</script>
</body>
</html>