package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsRole;
import me.laudukang.persistence.service.IRoleService;
import me.laudukang.spring.domain.RoleDomain;
import me.laudukang.spring.events.LogEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/17
 * <p>Time: 23:25
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("admin")
public class RoleController implements ApplicationContextAware {
    @Autowired
    private IRoleService iRoleService;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = "roles", method = RequestMethod.GET)
    public String roles() {
        return "";
    }

    @RequestMapping(value = "saveRole", method = RequestMethod.POST)
    public String save(@ModelAttribute @Valid RoleDomain roleDomain, BindingResult bindingResult, HttpServletRequest request, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        OsRole osRole = new OsRole();
        osRole.setName(roleDomain.getName());
        osRole.setRemark(roleDomain.getRemark());
        iRoleService.save(osRole, roleDomain.getOsPermissions());
        String user = null != session.getAttribute("account") ? String.valueOf(session.getAttribute("account")) : "admin";
        StringBuilder stringBuilder = new StringBuilder("管理员[").append(user).append("]").append("创建了角色[").append(roleDomain.getName()).append("]");
        applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), user, request.getRemoteHost()));
        return "redirect:roles";
    }

    @RequestMapping(value = "updateRole", method = RequestMethod.POST)
    public String update(@ModelAttribute RoleDomain roleDomain, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        iRoleService.updateById(roleDomain, roleDomain.getOsPermissions());
        return "redirect:roles";
    }

    @RequestMapping(value = "roleInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> roleInfo(@RequestParam("id") int id) {
        OsRole osRole = iRoleService.findOne(id);
        Map<String, Object> map = new HashMap<>();
        map.put("data", osRole);
        map.put("success", true);
        return map;
    }

}
