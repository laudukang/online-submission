package me.laudukang.spring.controller;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.model.OsPermission;
import me.laudukang.persistence.model.OsRole;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.domain.AdminDomain;
import me.laudukang.spring.domain.LoginDomain;
import me.laudukang.spring.domain.PasswordDomain;
import me.laudukang.spring.events.EmailEvent;
import me.laudukang.spring.events.LogEvent;
import me.laudukang.util.MapUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
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
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/27
 * <p>Time: 0:12
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController implements ApplicationContextAware {
    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private SimpleDateFormat simpleDateFormat;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = {"", "login"}, method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("loginDomain", new LoginDomain());
        return "admin_login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute @Valid LoginDomain loginDomain, BindingResult bindingResult, HttpServletRequest request, HttpSession session, Model model) {
        if (bindingResult.hasFieldErrors()) {
            return "admin_login";
        }
//        OsAdmin osAdmin = iAdminService.login(loginDomain.getAccount(), loginDomain.getPassword());
//        if (null != osAdmin) {
//            session.setAttribute("adminid", osAdmin.getId());
//            session.setAttribute("account", osAdmin.getAccount());
//            session.setAttribute("name", null != osAdmin.getName() ? osAdmin.getName() : osAdmin.getAccount());
//            if (osAdmin.getReviewer().equals("1")) {
//                return "redirect:docsReview";//审稿员页面
//
//            } else {
//                return "redirect:docsSuper";//管理员页面
//            }
//        }
        StringBuilder stringBuilder = new StringBuilder("管理员[").append(loginDomain.getAccount());
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginDomain.getAccount(), loginDomain.getPassword());
            usernamePasswordToken.setRememberMe(false);
            SecurityUtils.getSubject().login(usernamePasswordToken);

            stringBuilder.append("]登录成功");
            applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), loginDomain.getAccount(), request.getRemoteHost()));

            if (Boolean.valueOf(String.valueOf(session.getAttribute("isReviewer"))))
                return "redirect:/admin/review";
            else return "redirect:/admin/docs";
        } catch (AuthenticationException ex) {
            stringBuilder.append("]登录失败，账号/密码不正确");
            applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), loginDomain.getAccount(), request.getRemoteHost()));
        }
        bindingResult.rejectValue("account", "", "账号或密码不正确");
        model.addAttribute("success", false);
        model.addAttribute("msg", "账号或密码不正确");
        return "admin_login";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePasswordPage(Model model) {
        model.addAttribute("passwordDomain", new PasswordDomain());
        return "updatePassword";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute @Valid PasswordDomain passwordDomain, BindingResult
            bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasFieldErrors()) {
            return "updatePassword";
        }
        boolean isSame = passwordDomain.getNewPassword1().equals(passwordDomain.getNewPassword2());
        if (!isSame) {
            bindingResult.rejectValue("newPassword1", "", "新密码前后输入不一致");
            return "updatePassword";
        }
        int tmp = 0;
        int id = Integer.valueOf(String.valueOf(session.getAttribute("adminid")));
        OsAdmin osAdmin = iAdminService.findOne(id);
        if (null != osAdmin && osAdmin.getPassword().equals(passwordDomain.getPassword()) && isSame) {
            tmp = iAdminService.updatePassword(id, passwordDomain.getNewPassword1());
        }
        bindingResult.rejectValue("password", "", 1 == tmp ? "成功修改密码" : "原密码密码输入不对");
        model.addAttribute("success", 1 == tmp);
        model.addAttribute("msg", 1 == tmp ? "成功修改密码" : "原密码密码输入不对");
        return "updatePassword";
    }


    @RequestMapping(value = "newAdmin", method = RequestMethod.GET)
    public String newAdminPage(Model model) {
        model.addAttribute("adminDomain", new AdminDomain());
        return "";
    }

    @RequestMapping(value = "newAdmin", method = RequestMethod.POST)
    public String newAdmin(@ModelAttribute @Valid AdminDomain adminDomain, BindingResult bindingResult, HttpServletRequest request, HttpSession session, Model model) {
        if (Strings.isNullOrEmpty(adminDomain.getAccount()) || Strings.isNullOrEmpty(adminDomain.getPassword())) {
            bindingResult.rejectValue("account", "", "账号名/密码不能为空");
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
        osAdmin.setSubject("");//暂时忽略adminDomain.getSubject()
        osAdmin.setStatus(1);

        osAdmin.setReviewer(adminDomain.getReviewer());
        if ("1".equals(adminDomain.getReviewer())) {//审稿员默认审稿权限
            OsRole osRole = iAdminService.findReviewerRole();
            List<OsRole> osRoleList = new ArrayList<>(1);
            osRoleList.add(osRole);
            osAdmin.setOsRoles(osRoleList);
        }

        iAdminService.save(osAdmin);

        String user = null != session.getAttribute("account") ? String.valueOf(session.getAttribute("account")) : "admin";
        //发送注册邮件
        StringBuilder sb = new StringBuilder(
                "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                .append(adminDomain.getAccount())
                .append(",您好<br>管理员[")
                .append(user)
                .append("]邀请您使用网络投稿系统<br>")
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
                .append("点击这里登录系统</a><br><br>")
                .append(simpleDateFormat.format(new Date()))
                .append("<br><br>系统自动发送邮件，请勿回复</body></html>");

        StringBuilder stringBuilder = new StringBuilder("管理员[").append(user).append("]创建了用户[")
                .append(!Strings.isNullOrEmpty(adminDomain.getName()) ? adminDomain.getName() : adminDomain.getAccount()).append("]");
        applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), user, request.getRemoteHost()));
        applicationContext.publishEvent(new EmailEvent(this, adminDomain.getAccount(), "注册成功-网络投稿系统服务邮件", sb.toString()));
        return "redirect:";//管理员列表页面
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") int id, HttpServletRequest request, HttpSession session) {
        if (1 == id) {
            return MapUtil.getForbiddenOperationMap();
        }

        String user = null != session.getAttribute("account") ? String.valueOf(session.getAttribute("account")) : "admin";
        OsAdmin osAdmin = iAdminService.findOne(id);
        StringBuilder stringBuilder = new StringBuilder("管理员[").append(user).append("]删除了用户[").append(osAdmin.getId()).append("_")
                .append(!Strings.isNullOrEmpty(osAdmin.getName()) ? osAdmin.getName() : osAdmin.getAccount()).append("]");
        applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), user, request.getRemoteHost()));

        iAdminService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "able", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> disable(@RequestParam("id") int id, @RequestParam("status") int status, HttpServletRequest request, HttpSession session) {
        if (1 == id) {
            return MapUtil.getForbiddenOperationMap();
        }

        String user = null != session.getAttribute("account") ? String.valueOf(session.getAttribute("account")) : "admin";
        OsAdmin osAdmin = iAdminService.findOne(id);
        StringBuilder stringBuilder = new StringBuilder("管理员[").append(user).append("]").append(status == 1 ? "启用了用户[" : "禁用了用户[").append(osAdmin.getId()).append("_")
                .append(!Strings.isNullOrEmpty(osAdmin.getName()) ? osAdmin.getName() : osAdmin.getAccount()).append("]");
        applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), user, request.getRemoteHost()));

        iAdminService.ableAdmin(id, status);
        return MapUtil.getAbleMap();
    }

//通过详情页面更新信息
//    @RequestMapping(value = "updateAdminInfo", method = RequestMethod.GET)
//    public String updateAdminPage(Model model) {
//        model.addAttribute("adminDomain", new AdminDomain());
//        return "";
//    }

    @RequestMapping(value = "updateInfo", method = RequestMethod.POST)
    public String updateAdminInfo(@ModelAttribute @Valid AdminDomain adminDomain, BindingResult
            bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "admin_account";
        }
        int adminid = Integer.valueOf(String.valueOf(session.getAttribute("adminid")));
        adminDomain.setId(adminid);
        iAdminService.updateById(adminDomain);
        model.addAttribute("success", true);
        model.addAttribute("msg", "成功修改信息");
        bindingResult.rejectValue("account", "", "成功修改信息");
        session.setAttribute("name", !Strings.isNullOrEmpty(adminDomain.getName()) ? adminDomain.getName() : String.valueOf(session.getAttribute("account")));
        return "redirect:/admin/info";
    }

    //超级管理员更新其他管理员信息
    @RequestMapping(value = "updateOtherInfo", method = RequestMethod.POST)
    public String updateOtherAdminInfo(@ModelAttribute @Valid AdminDomain adminDomain, BindingResult
            bindingResult, Model model, HttpSession session, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "admin_account";
        }
        int adminid = Integer.valueOf(String.valueOf(session.getAttribute("otherAdminId")));
        adminDomain.setId(adminid);
        iAdminService.updateById(adminDomain);
        model.addAttribute("success", true);
        model.addAttribute("msg", "成功修改信息");
        String user = null != session.getAttribute("account") ? String.valueOf(session.getAttribute("account")) : "admin";
        StringBuilder stringBuilder = new StringBuilder("管理员[").append(user).append("]").append("更改了[").append(adminDomain.getAccount()).append("_")
                .append(adminDomain.getName()).append("]的信息");
        applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), user, request.getRemoteHost()));
        bindingResult.rejectValue("account", "", "成功修改信息");
        session.removeAttribute("otherAdminId");
        return "redirect:/adminInfo/" + adminid;
    }

    //超级管理员查看其他管理员信息
    @RequestMapping(value = "adminInfo/{id}", method = RequestMethod.GET)
    public String adminVisitInfo(@PathVariable("id") int id, Model model, HttpSession session) {
        OsAdmin osAdmin = iAdminService.findOne(id);
        if (null == osAdmin) {
            model.addAttribute("success", null != osAdmin);
            model.addAttribute("msg", null != osAdmin ? "" : "用户不存在");
            return "redirect:/admin/logout";
        }
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
        session.setAttribute("otherAdminId", osAdmin.getId());
        return "admin_account";
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String loginAdminInfo(Model model, HttpSession session) {
        int adminid = Integer.valueOf(String.valueOf(session.getAttribute("adminid")));
        OsAdmin osAdmin = iAdminService.findOne(adminid);
        if (null == osAdmin) {
            model.addAttribute("success", null != osAdmin);
            model.addAttribute("msg", null != osAdmin ? "" : "用户不存在");
            return "redirect:/admin/logout";
        }
        AdminDomain adminDomain = new AdminDomain();
        adminDomain.setId(osAdmin.getId());
        adminDomain.setAccount(osAdmin.getAccount());
        adminDomain.setAddress(osAdmin.getAddress());
        adminDomain.setSubject(osAdmin.getSubject());
        adminDomain.setSex(osAdmin.getSex());
        adminDomain.setName(osAdmin.getName());
        adminDomain.setBirth(osAdmin.getBirth());
        adminDomain.setRemark(osAdmin.getRemark());
        adminDomain.setMobilePhone(osAdmin.getMobilePhone());
        adminDomain.setOfficePhone(osAdmin.getOfficePhone());
        adminDomain.setReviewer(osAdmin.getReviewer());
        adminDomain.setStatus(osAdmin.getStatus());
        model.addAttribute("adminDomain", adminDomain);
        return "admin_account";
    }


    @RequestMapping(value = "admins", method = RequestMethod.GET)
    public String findAllPage() {
        return "";
    }

    @RequestMapping(value = "admins", method = RequestMethod.POST)
    public Map<String, Object> findAllAdmin(@ModelAttribute AdminDomain adminDomain) {
        Map<String, Object> map = new HashMap<>(5);
        Page<OsAdmin> tmp = iAdminService.findAll(adminDomain);
        List<AdminDomain> osAdminList = mapOsAdminWithRoleName(tmp.getContent());
        map.put("success", true);
        map.put("msg", !tmp.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", osAdminList);
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
        List<AdminDomain> osAdminList = mapOsAdminWithRoleName(tmp.getContent());
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", !tmp.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", osAdminList);
        map.put("iTotalRecords", tmp.getTotalElements());
        map.put("iTotalDisplayRecords", tmp.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/admin/login";
    }

    //获取审稿员下拉框
    @RequestMapping(value = "reviewerList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reviewerList() {
        List<OsAdmin> osAdminList = iAdminService.listReviewer();
        Map<String, Object> map = new HashMap<>(2);
        map.put("success", true);
        map.put("data", osAdminList);
        return map;
    }

    private List<AdminDomain> mapOsAdminWithRoleName(List<OsAdmin> tmp) {
        List<AdminDomain> osAdminList = new ArrayList<>(tmp.size());
        for (OsAdmin osAdmin : tmp) {
            AdminDomain adminDomain = new AdminDomain();
            adminDomain.setId(osAdmin.getId());
            adminDomain.setName(osAdmin.getName());
            adminDomain.setStatus(osAdmin.getStatus());
            adminDomain.setAccount(osAdmin.getAccount());
            adminDomain.setOfficePhone(osAdmin.getOfficePhone());
            adminDomain.setSex(osAdmin.getSex());
            adminDomain.setReviewer(osAdmin.getReviewer());
            StringBuilder stringBuilder = new StringBuilder();
            for (OsRole osRole : osAdmin.getOsRoles()) {
                for (OsPermission osPermission : osRole.getOsPermissions()) {
                    stringBuilder.append(osPermission.getName()).append(";");
                }
            }
            int index = stringBuilder.lastIndexOf(";");
            if (-1 != index) {
                adminDomain.setRole(stringBuilder.substring(0, index));
            } else {
                adminDomain.setRole("暂无权限,请先指定");
            }
            osAdminList.add(adminDomain);
        }
        return osAdminList;
    }
}
