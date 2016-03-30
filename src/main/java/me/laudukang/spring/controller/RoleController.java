package me.laudukang.spring.controller;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsPermission;
import me.laudukang.persistence.model.OsRole;
import me.laudukang.persistence.service.IRoleService;
import me.laudukang.spring.domain.RoleDomain;
import me.laudukang.spring.events.LogEvent;
import me.laudukang.util.MapUtil;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
        return "admin_roleList";
    }

    @RequestMapping(value = "roles", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> roles(@ModelAttribute RoleDomain roleDomain, BindingResult bindingResult) {
        Pageable pageable = new PageRequest(roleDomain.getPage(),
                roleDomain.getPageSize(), new Sort(Sort.Direction.fromString(roleDomain.getSortDir()), roleDomain.getSortCol()));
        Page<OsRole> tmp = iRoleService.findAll(pageable);
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", !tmp.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", tmp.getContent());
        map.put("iTotalRecords", tmp.getTotalElements());
        map.put("iTotalDisplayRecords", tmp.getTotalElements());
        return map;
    }

    @RequestMapping(value = "saveRole", method = RequestMethod.GET)
    public String saveRolePage(Model model) {
        List<OsPermission> osPermissionList = iRoleService.findUnAssignPermission();
        model.addAttribute("osPermissionList", osPermissionList);
        model.addAttribute("msg", "添加角色");
        model.addAttribute("type", "save");
        return "admin_addRole";
    }


    @RequestMapping(value = "saveRole", method = RequestMethod.POST)
    public String save(@ModelAttribute @Valid RoleDomain roleDomain, BindingResult bindingResult, HttpServletRequest request, HttpSession session) {
        if (Strings.isNullOrEmpty(roleDomain.getName())) {
            bindingResult.rejectValue("name", "", "角色名称不能为空");
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
            return "admin_addRole";
        }
        iRoleService.updateById(roleDomain, roleDomain.getOsPermissions());
        return "redirect:/admin/roleInfo/" + roleDomain.getId();
    }

    @RequestMapping(value = "roleInfo/{id}", method = RequestMethod.GET)
    public String roleInfo(@PathVariable("id") int id, Model model) {
        OsRole osRole = iRoleService.findOne(id);
        StringBuilder stringBuilder = new StringBuilder(";");
        for (OsPermission osPermission : osRole.getOsPermissions()) {
            stringBuilder.append(osPermission.getId()).append(";");
        }
        List<OsPermission> osPermissionList = iRoleService.findUnAssignPermission();
        model.addAttribute("osRole", osRole);
        model.addAttribute("osPermissionList", osPermissionList);
        model.addAttribute("msg", "修改角色");
        model.addAttribute("type", "update");
        model.addAttribute("hasPermission", stringBuilder.toString());
        return "admin_addRole";
    }

    @RequestMapping(value = "adminRoleInfo/{id}", method = RequestMethod.GET)
    public String adminRoleInfo(@PathVariable("id") int id, @RequestParam("account") String account, Model model) {
        List<OsRole> osRoleList = iRoleService.findAll();
        List<OsRole> own = iRoleService.findRoleByAdminId(id);
        StringBuilder stringBuilder = new StringBuilder(";");
        for (OsRole osRole : own) {
            stringBuilder.append(osRole.getId()).append(";");
        }
        model.addAttribute("osRoleList", osRoleList);
        model.addAttribute("hasRole", stringBuilder.toString());
        model.addAttribute("tmpAccount", account);
        model.addAttribute("tmpId", id);
        return "admin_role";
    }

    @RequestMapping(value = "deleteRole/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") int id) {
        iRoleService.deleteById(id);
        return MapUtil.getDeleteMap();
    }
}
