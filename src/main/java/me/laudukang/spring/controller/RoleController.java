package me.laudukang.spring.controller;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsRole;
import me.laudukang.persistence.service.IRoleService;
import me.laudukang.spring.domain.RoleDomain;
import me.laudukang.spring.events.LogEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String rolesPage() {
        return "";
    }

    @RequestMapping(value = "roles", method = RequestMethod.POST)
    public Map<String, Object> roles(@ModelAttribute RoleDomain roleDomain, BindingResult bindingResult) {
        Pageable pageable = new PageRequest(roleDomain.getPage(),
                roleDomain.getPageSize(), new Sort(roleDomain.getSortDir(), roleDomain.getSortCol()));
        Page<OsRole> tmp = iRoleService.findAll(pageable);
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", !tmp.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", tmp.getContent());
        map.put("iTotalRecords", tmp.getTotalElements());
        map.put("iTotalDisplayRecords", tmp.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "saveRole", method = RequestMethod.POST)
    public String save(@ModelAttribute @Valid RoleDomain roleDomain, BindingResult bindingResult, HttpServletRequest request, HttpSession session) {
        if (Strings.isNullOrEmpty(roleDomain.getName())) {
            bindingResult.rejectValue("name", "角色名称不能为空");
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

    //更新role下的权限
    @RequestMapping(value = "updateRole", method = RequestMethod.POST)
    public String update(@ModelAttribute RoleDomain roleDomain, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        iRoleService.updateById(roleDomain, roleDomain.getOsPermissions());
        return "redirect:roles";
    }

    @RequestMapping(value = "roleInfo/{id}", method = RequestMethod.POST)
    public String roleInfo(@PathVariable("id") int id, Model model) {
        OsRole osRole = iRoleService.findOne(id);
        model.addAttribute("osRole", osRole);
        return "";
    }
}
