package me.laudukang.spring.shiro;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/24
 * <p>Time: 10:46
 * <p>Version: 1.0
 */
public class UserShiroRealm extends AuthorizingRealm {
    @Autowired
    private IUserService iUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("in UserShiroRealm PrincipalCollection");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("user");
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("in UserShiroRealm AuthenticationToken");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        OsUser osUser = iUserService.login(usernamePasswordToken.getUsername(), new String(usernamePasswordToken.getPassword()));
        if (null != osUser) {
            setAttributeInSession("userid", osUser.getId());
            setAttributeInSession("account", osUser.getAccount());
            setAttributeInSession("loginType", "0");
            setAttributeInSession("name", !Strings.isNullOrEmpty(osUser.getName()) ? osUser.getName() : osUser.getAccount());
            return new SimpleAuthenticationInfo(osUser.getId(), osUser.getPassword(), getName());
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
