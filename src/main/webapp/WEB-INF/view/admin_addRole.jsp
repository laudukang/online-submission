<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25 0025
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>网络投稿系统</title>
    <link rel="stylesheet" href="${home}/css/font-awesome.css"/>
    <link rel="stylesheet" href="${home}/css/main.css"/>
</head>
<body>
<%@include file="header.jsp" %>
<div class="frame_main">
    <%@include file="nav.jsp" %>
    <div class="frame_main_content">
        <h2 class="frame_main_content_path">
            系统中心 &gt; 角色管理 &gt; ${msg}</h2>
        <div class="frame_main_center">
            <form action="${home}${type=="save"?"/admin/saveRole":"/admin/updateRole"}" method="post">
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
                            <input type="text" name="name" id="roleName" class="doc_text" value="${osRole.name}"
                                   required/>
                            <input type="text" name="id" value="${osRole.id}" hidden>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            备注：
                        </td>
                        <td colspan="3">
                            <textarea name="remark" id="roleRemark" class="doc_select"
                                      style="width: 450px;height: 80px;">${osRole.remark}</textarea>
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
                    <c:if test="${not empty osRole.osPermissions}">
                        <c:forEach items="${osRole.osPermissions}" var="permission" varStatus="obj">
                            <tr>
                                <td style="text-align: center"><input type="checkbox" name="osPermissions"
                                                                      onclick="stop(event)"
                                                                      checked value="${permission.id}"/></td>
                                <td>${obj.count}</td>
                                <td>${permission.name}</td>
                                <td>${permission.remark}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty osPermissionList}">
                        <c:forEach items="${osPermissionList}" var="permission" varStatus="obj">
                            <c:set var="checked" value="${';'}${permission.id}${';'}"></c:set>
                            <tr>
                                <td style="text-align: center"><input type="checkbox" name="osPermissions"
                                                                      onclick="stop(event)"
                                    ${fn:contains(hasPermission,checked)?"checked":""} value="${permission.id}"/></td>
                                <td>${fn:length(osRole.osPermissions)+obj.count}</td>
                                <td>${permission.name}</td>
                                <td>${permission.remark}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty osPermissionList}">
                        <tr>
                            <td style="text-align: center" colspan="4"><span class="doc_remind_Red">没有更多权限可分配了</span>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td colspan="4" style="border-bottom: 4px solid #C4D8ED;">
                            <input type="submit" id="doc_system_permissionTable_submitBtn" class="doc_btn"
                                   style="margin: 5px 50px;" value="保存角色信息">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<script src="${home}/js/jquery.js"></script>
<script src="${home}/js/common.js"></script>
<script src="${home}/js/main.js"></script>
<script>
    $(function () {
//        $('#doc_system_permissionTable_submitBtn').on('click',function(){
//            var permissionArr = [];
//            $('.doc_system_permissionTable').find('input[type="checkbox"]:checked').each(function(){
//                permissionArr.push($(this).val());
//            });
//            doc.tool.getJSON({
//                url:"data.json",
//                data:{'id':"",'osPermission':permissionArr,'name':$('#roleName').val(),'remark':$('#roleRemark').val()},
//                success:function(){
//                    $.remindBox({
//                        remind:"保存权限成功！"
//                    });
//                },
//                error:{
//                    remind:"保存权限出错"
//                }
//            });
//        });

        $('.doc_system_permissionTable').delegate('tr', 'click', function () {
            var checkbox = $(this).find('input[type="checkbox"]');
            if (!checkbox.prop('checked')) {
                checkbox.prop('checked', true);
            } else {
                checkbox.prop('checked', false);
            }
        });

    });

    function stop(ev) {
        ev.stopPropagation();
    }
</script>
</body>
</html>