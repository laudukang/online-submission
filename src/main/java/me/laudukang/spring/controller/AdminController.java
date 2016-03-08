package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.domain.AdminLoginDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/27
 * <p>Time: 0:12
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET() {
        return "admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(@Valid @ModelAttribute AdminLoginDomain adminLoginDomain, BindingResult bindingResult, Model model) {
        System.out.println(adminLoginDomain);
        return "admin/login";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(String password, String newPassword1, String newPassword2, HttpSession session, Model model) {
        boolean check;
        boolean isSame = !isNullOrEmpty(newPassword1) && !isNullOrEmpty(newPassword2) && newPassword1.equals(newPassword2);
        int tmp = 0;
        if (check = !isNullOrEmpty(password)) {
            int id = Integer.valueOf(String.valueOf(session.getAttribute("adminid")));
            OsAdmin osAdmin = adminService.findOne(id);
            if (null != osAdmin && osAdmin.getPassword().equals(password) && isSame) {
                tmp = adminService.updatePassword(id, newPassword1);
            }
        }
        model.addAttribute("success", check && 1 == tmp ? true : false);
        model.addAttribute("msg", check && 1 == tmp ? "成功修改密码" : "修改密码失败");
        return "";
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        request.getSession().setAttribute("adminid", 1);
    }
}
