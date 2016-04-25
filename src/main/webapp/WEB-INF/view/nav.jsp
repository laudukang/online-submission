<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/16 0016
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="frame_main_nav_wrap" id="docNav">
    <div class="frame_main_nav">
        <div class="frame_main_nav_title">
            <strong>稿件中心</strong>
            <div class="frame_main_nav_title_Bg"></div>
        </div>
        <shiro:hasRole name="user">
            <ul class="frame_main_nav_list">
                <li><a href="${home}/docs">我的投稿</a></li>
                <li><a href="${home}/newDoc">我要投稿</a></li>
            </ul>
        </shiro:hasRole>
        <shiro:hasRole name="Administrator">
            <ul class="frame_main_nav_list">
                <li><a href="${home}/admin/docs">稿件查询</a></li>
                <li><a href="${home}/admin/distributedDocs">已派发稿件</a></li>
            </ul>
        </shiro:hasRole>
        <shiro:hasRole name="Reviewer">
            <ul class="frame_main_nav_list">
                <li><a href="${home}/admin/review">我的审稿</a></li>
            </ul>
        </shiro:hasRole>
        <%--<shiro:hasPermission name="USER:QUERY,USER:UPDATE,USER:DELETE,ADMIN:NEW,ADMIN:DELETE,ADMIN:QUERY,ADMIN:UPDATELOG:QUERY,ROLE:QUERY,ROLE:UPDATE">--%>
        <shiro:hasRole name="Administrator">
        <div class="frame_main_nav_title">
            <strong>系统中心</strong>
            <div class="frame_main_nav_title_Bg"></div>
        </div>
            <ul class="frame_main_nav_list">
                <li><a href="${home}/admin/admins">管理员管理</a></li>
                <li><a href="${home}/admin/reviewers">审稿员管理</a></li>
                <li><a href="${home}/admin/users">用户管理</a></li>
                <li><a href="${home}/admin/roles">角色管理</a></li>
                <li><a href="${home}/admin/logs">系统日志</a></li>
            </ul>
        </shiro:hasRole>
        <div class="frame_main_nav_title">
            <strong>个人中心</strong>
            <div class="frame_main_nav_title_Bg"></div>
        </div>
        <shiro:hasRole name="user">
            <ul class="frame_main_nav_list">
                <li><a href="${home}/userInfo">账号信息</a></li>
                <li><a href="${home}/updatePassword">修改密码</a></li>
            </ul>
        </shiro:hasRole>
        <shiro:hasRole name="MYADMININFO">
            <ul class="frame_main_nav_list">
                <li><a href="${home}/admin/info">账号信息</a></li>
                <li><a href="${home}/admin/updatePassword">修改密码</a></li>
            </ul>
        </shiro:hasRole>
        <div class="frame_main_nav_title">
            <strong>系统信息</strong>
            <div class="frame_main_nav_title_Bg"></div>
        </div>
        <shiro:hasRole name="user">
            <ul class="frame_main_nav_list">
                <li><a href="${home}/messages">系统信息</a></li>
            </ul>
        </shiro:hasRole>
        <shiro:hasRole name="MYADMININFO">
            <ul class="frame_main_nav_list">
                <li><a href="${home}/admin/messages">系统信息</a></li>
            </ul>
        </shiro:hasRole>
    </div>
</div>

