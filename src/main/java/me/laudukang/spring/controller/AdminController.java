package me.laudukang.spring.controller;

import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.domain.AdminLoginDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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
}
