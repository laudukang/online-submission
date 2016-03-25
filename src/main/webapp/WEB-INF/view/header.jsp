<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/16 0016
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="frame_header">
    <div class="frame_header_banner">
        <a class="frame_header_logo" href="javascript:;">
            <img src="${home}/images/online/logo.png" alt="网络投稿系统">
            <h2 class="frame_header_title">网络投稿系统</h2>
        </a>
        <div class="frame_header_account">
            <span>${name},欢迎你</span>
            <c:if test="${loginType=='0'}">
                <a href="${home}/messages">系统信息</a>
                <a href="${home}/updatePassword">修改密码</a>
                <a href="${home}/logout">安全退出</a>
            </c:if>
            <c:if test="${loginType=='1'}">
                <shiro:hasAnyRoles name="Reviewer">
                    <a href="${home}/admin/messages">系统信息</a>
                </shiro:hasAnyRoles>
                <a href="${home}/admin/updatePassword">修改密码</a>
                <a href="${home}/admin/logout">安全退出</a>
            </c:if>
        </div>
    </div>
</div>

