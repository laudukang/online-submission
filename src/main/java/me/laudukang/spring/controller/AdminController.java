package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.domain.AdminDomain;
import me.laudukang.spring.domain.LoginDomain;
import me.laudukang.spring.domain.PasswordDomain;
import me.laudukang.spring.events.EmailEvent;
import me.laudukang.spring.events.LogEvent;
import me.laudukang.util.MapUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;


/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/27
 * <p>Time: 0:12
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("admin")
public class AdminController implements ApplicationContextAware {
    @Autowired
    private IAdminService iAdminService;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("loginDomain", new LoginDomain());
        return "";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute @Valid LoginDomain loginDomain, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasFieldErrors()) {
            return "";
        }
        OsAdmin osAdmin = iAdminService.login(loginDomain.getAccount(), loginDomain.getPassword());
        if (null != osAdmin) {
            session.setAttribute("adminid", osAdmin.getId());
            session.setAttribute("account", osAdmin.getAccount());
            session.setAttribute("name", null != osAdmin.getName() ? osAdmin.getName() : osAdmin.getAccount());
            return "welcome";
        }
        bindingResult.rejectValue("account", "", "账号或密码不对");
        return "";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePasswordPage(Model model) {
        model.addAttribute("passwordDomain", new PasswordDomain());
        return "index";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute @Valid PasswordDomain passwordDomain, BindingResult
            bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasFieldErrors()) {
            return "index";
        }
        boolean isSame = passwordDomain.getNewPassword1().equals(passwordDomain.getNewPassword2());
        int tmp = 0;
        int id = Integer.valueOf(String.valueOf(session.getAttribute("adminid")));
        OsAdmin osAdmin = iAdminService.findOne(id);
        if (null != osAdmin && osAdmin.getPassword().equals(passwordDomain.getPassword()) && isSame) {
            tmp = iAdminService.updatePassword(id, passwordDomain.getNewPassword1());
        }
        bindingResult.rejectValue("password", "", 1 == tmp ? "成功修改密码" : "原密码密码输入不对");
        model.addAttribute("success", 1 == tmp);
        model.addAttribute("msg", 1 == tmp ? "成功修改密码" : "原密码密码输入不对");
        return "index";
    }

    @RequestMapping(value = "newAdmin", method = RequestMethod.GET)
    public String newAdminPage(Model model) {
        model.addAttribute("adminDomain", new AdminDomain());
        return "";
    }

    @RequestMapping(value = "newAdmin", method = RequestMethod.POST)
    public String newAdmin(@ModelAttribute @Valid AdminDomain adminDomain, BindingResult bindingResult, HttpServletRequest request, HttpSession session, Model model) {
        if (bindingResult.hasFieldErrors()) {
            return "";
        }
        if (iAdminService.existAccount(adminDomain.getAccount())) {
            bindingResult.rejectValue("account", "", "账号已存在");
            return "";
        }
        OsAdmin osAdmin = new OsAdmin();
        osAdmin.setAccount(adminDomain.getAccount());
        osAdmin.setPassword(adminDomain.getPassword());
        osAdmin.setName(adminDomain.getName());
        osAdmin.setBirth(adminDomain.getBirth());
        osAdmin.setAddress(adminDomain.getAddress());
        osAdmin.setMobilePhone(adminDomain.getMobilePhone());
        osAdmin.setOfficePhone(adminDomain.getOfficePhone());
        osAdmin.setRemark(adminDomain.getRemark());
        osAdmin.setSex(adminDomain.getSex());
        osAdmin.setSubject(adminDomain.getSubject());
        osAdmin.setStatus(1);
        osAdmin.setReviewer(adminDomain.getReviewer());
        iAdminService.save(osAdmin);

        String user = null != session.getAttribute("account") ? String.valueOf(session.getAttribute("account")) : "admin";
        //发送注册邮件
        StringBuffer sb = new StringBuffer(
                "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                .append(adminDomain.getAccount())
                .append(",您好</br>管理员[")
                .append(user)
                .append("]邀请您使用网络投稿系统</br>")
                .append("<a href=\"")
                .append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append("/?time=")
                .append(new Date().getTime())
                .append("\">")
                .append("点击这里登录")
                .append("</a></br></body></html>");
        StringBuilder stringBuilder = new StringBuilder("管理员[").append(user).append("]创建了用户[")
                .append(!isNullOrEmpty(adminDomain.getName()) ? adminDomain.getName() : adminDomain.getAccount()).append("]");
        applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), user, request.getRemoteHost()));
        applicationContext.publishEvent(new EmailEvent(this, adminDomain.getAccount(), "注册成功-网络投稿系统服务邮件", sb.toString()));
        return "";
    }

    @RequestMapping(value = "deleteAdmin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        if (1 == id) {
            return MapUtil.getForbiddenOperationMap();
        }
        iAdminService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "ableAdmin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> disable(@RequestParam("id") int id, @RequestParam("status") int status) {
        if (1 == id) {
            return MapUtil.getForbiddenOperationMap();
        }
        iAdminService.ableAdmin(id, status);
        return MapUtil.getAbleMap();
    }
//通过详情页面更新信息
//    @RequestMapping(value = "updateAdminInfo", method = RequestMethod.GET)
//    public String updateAdminPage(Model model) {
//        model.addAttribute("adminDomain", new AdminDomain());
//        return "";
//    }

    @RequestMapping(value = "adminInfo", method = RequestMethod.GET)
    public String findOne(@RequestParam("id") int id, Model model) {
        OsAdmin osAdmin = iAdminService.findOne(id);
        model.addAttribute("success", null != osAdmin);
        model.addAttribute("msg", null != osAdmin ? "" : "用户不存在");
        if (null != osAdmin) {
            AdminDomain adminDomain = new AdminDomain();
            adminDomain.setId(osAdmin.getId());
            adminDomain.setAccount(osAdmin.getAccount());
            adminDomain.setAddress(osAdmin.getAddress());
            adminDomain.setSubject(adminDomain.getSubject());
            adminDomain.setSex(osAdmin.getSex());
            adminDomain.setName(osAdmin.getName());
            adminDomain.setBirth(osAdmin.getBirth());
            adminDomain.setRemark(osAdmin.getRemark());
            adminDomain.setMobilePhone(osAdmin.getMobilePhone());
            adminDomain.setOfficePhone(osAdmin.getOfficePhone());
            adminDomain.setReviewer(osAdmin.getReviewer());
            adminDomain.setStatus(osAdmin.getStatus());
            model.addAttribute("adminDomain", adminDomain);
        }
        return "";
    }

    @RequestMapping(value = "updateAdminInfo", method = RequestMethod.POST)
    public String updateAdminInfo(@ModelAttribute @Valid AdminDomain adminDomain, BindingResult
            bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        iAdminService.updateById(adminDomain);
        model.addAttribute("success", true);
        model.addAttribute("msg", "成功修改信息");
        bindingResult.rejectValue("account", "", "成功修改信息");
        return "";
    }


    @RequestMapping(value = "admins", method = RequestMethod.GET)
    public String findAllPage() {
        return "";
    }

    @RequestMapping(value = "admins", method = RequestMethod.POST)
    public Map<String, Object> findAllAdmin(@ModelAttribute AdminDomain adminDomain) {
        Map<String, Object> map = new HashMap<>(5);
        Page<OsAdmin> tmp = iAdminService.findAll(adminDomain);
        map.put("success", true);
        map.put("msg", !tmp.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", tmp.getContent());
        map.put("iTotalRecords", tmp.getTotalElements());
        map.put("iTotalDisplayRecords", tmp.getNumberOfElements());
        return map;
    }

    //审稿员页面
    @RequestMapping(value = "reviewers", method = RequestMethod.GET)
    public String findAllReviewerPage() {
        return "";
    }

    //审稿员列表
    @RequestMapping(value = "reviewers", method = RequestMethod.POST)
    public Map<String, Object> findAllReviewer(@ModelAttribute AdminDomain adminDomain) {
        Page<OsAdmin> tmp = iAdminService.findAllReviewer(adminDomain);
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", !tmp.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", tmp.getContent());
        map.put("iTotalRecords", tmp.getTotalElements());
        map.put("iTotalDisplayRecords", tmp.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
