package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsRole;
import me.laudukang.persistence.service.IRoleService;
import me.laudukang.spring.domain.RoleDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
public class RoleController {
    @Autowired
    private IRoleService iRoleService;

    @RequestMapping(value = "roles", method = RequestMethod.GET)
    public String roles() {
        return "";
    }

    @RequestMapping(value = "saveRole", method = RequestMethod.POST)
    public String save(@ModelAttribute @Valid RoleDomain roleDomain, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        OsRole osRole = new OsRole();
        osRole.setName(roleDomain.getName());
        osRole.setRemark(roleDomain.getRemark());
        iRoleService.save(osRole, roleDomain.getOsPermissions());
        return "redirect:roles";
    }

    @RequestMapping(value = "updateRole", method = RequestMethod.POST)
    public String update(@ModelAttribute RoleDomain roleDomain, BindingResult bindingResult, HttpSession session) {
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
