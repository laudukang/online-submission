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
            系统中心 &gt; 管理员管理 &gt; ${tmpAccount}账号拥有的角色</h2>
        <div class="frame_main_center">
            <form action="${home}/admin/updateAdminRole" method="post">
                <input type="text" value="${tmpId}" name="id" hidden/>
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
                    <c:if test="${not empty osRoleList}">
                        <c:forEach items="${osRoleList}" var="osRole" varStatus="obj">
                            <c:set var="checked" value="${';'}${osRole.id}${';'}"></c:set>
                            <tr>
                                <td style="text-align: center">
                                    <input type="checkbox" ${fn:contains(hasRole,checked)?"checked":""}
                                           value="${osRole.id}" name="role" onclick="stop(event)"/>
                                </td>
                                <td>${obj.count}</td>
                                <td>${osRole.name}</td>
                                <td>
                                    <c:forEach items="${osRole.osPermissions}" var="permission">
                                        <c:out value="${permission.name}${';'}"></c:out>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
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
<script src="${home}/js/jquery.js"></script>
<script src="${home}/js/common.js"></script>
<script src="${home}/js/main.js"></script>
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

    function stop(ev) {
        ev.stopPropagation();
    }
</script>
</body>
</html>