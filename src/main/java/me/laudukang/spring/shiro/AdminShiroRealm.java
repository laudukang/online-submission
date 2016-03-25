package me.laudukang.spring.shiro;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.model.OsPermission;
import me.laudukang.persistence.model.OsRole;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.persistence.service.IRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/24
 * <p>Time: 10:50
 * <p>Version: 1.0
 */
public class AdminShiroRealm extends AuthorizingRealm {
    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private IRoleService iRoleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("in AdminShiroRealm PrincipalCollection");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        int adminid = Integer.valueOf(String.valueOf(super.getAvailablePrincipal(principals)));
        List<OsRole> osRoleList = iRoleService.findRoleByAdminId(adminid);
        StringBuilder stringBuilder = new StringBuilder();
        if (osRoleList.size() > 0) {
            for (OsRole osRole : osRoleList) {
                simpleAuthorizationInfo.addRole(osRole.getName());
                if (osRole.getOsPermissions().size() > 0)
                    for (OsPermission osPermission : osRole.getOsPermissions()) {
                        simpleAuthorizationInfo.addStringPermission(osPermission.getName());
                        stringBuilder.append(osPermission.getName()).append(" - ");
                    }
            }
            System.out.println("stringBuilder.toString()=" + stringBuilder.toString());
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("in AdminShiroRealm AuthenticationToken");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        OsAdmin osAdmin = iAdminService.login(usernamePasswordToken.getUsername(), new String(usernamePasswordToken.getPassword()));
        if (null != osAdmin) {
            setAttributeInSession("adminid", osAdmin.getId());
            setAttributeInSession("account", osAdmin.getAccount());
            setAttributeInSession("loginType", "1");
            setAttributeInSession("isReviewer", "1".equals(osAdmin.getReviewer()));
            setAttributeInSession("name", !Strings.isNullOrEmpty(osAdmin.getName()) ? osAdmin.getName() : osAdmin.getAccount());
            return new SimpleAuthenticationInfo(osAdmin.getId(), osAdmin.getPassword(), getName());

        }
        return null;
    }

    private void setAttributeInSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

}
