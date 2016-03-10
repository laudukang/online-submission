package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.domain.AdminDomain;
import me.laudukang.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("adminLoginDomain", new AdminDomain());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute AdminDomain adminDomain, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("adminLoginDomain", adminDomain);
            return "redirect:login";
        }
        Object[] osAdmin = adminService.login(adminDomain.getAccount(), adminDomain.getPassword());
        if (null != osAdmin && osAdmin.length > 0) {
            session.setAttribute("adminid", osAdmin[0]);//admin.id,admin.account,admin.name
            session.setAttribute("account", osAdmin[2]);
            session.setAttribute("name", osAdmin[3]);
            return "welcome";
        }
        model.addAttribute("msg", "账号不存在或密码错误");
        return "redirect:login";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePasswordPage() {
        return "";
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

    @RequestMapping(value = "newAdmin", method = RequestMethod.GET)
    public String newAdminPage(Model model) {
        model.addAttribute("osAdmin", new OsAdmin());
        return "";
    }

    @RequestMapping(value = "newAdmin", method = RequestMethod.POST)
    public String newAdmin(@RequestParam("type") String type, @ModelAttribute OsAdmin osAdmin, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("osAdmin", osAdmin);
            return "redirect:newAdmin";
        }
        if (adminService.existAccount(osAdmin.getAccount())) {
            bindingResult.reject("accountExist", "账号已存在");
            return "redirect:newAdmin";
        }
        osAdmin.setStatus(1);
        osAdmin.setReviewer("1".equals(type) ? "1" : "0");
        adminService.save(osAdmin);
        return "";
    }

    @RequestMapping(value = "deleteAdmin", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@RequestParam("id") int id) {
        if (1 == id) {
            return MapUtil.forbiddenOperationMap;
        }
        adminService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "ableAdmin", method = RequestMethod.DELETE)
    public Map<String, Object> disable(@RequestParam("id") int id, @RequestParam("status") int status) {
        if (1 == id) {
            return MapUtil.forbiddenOperationMap;
        }
        adminService.ableAdmin(id, status);
        return MapUtil.ableMap();
    }

    @RequestMapping(value = "updateAdminInfo", method = RequestMethod.GET)
    public String updateAdminPage(Model model) {
        model.addAttribute("osAdmin", new OsAdmin());
        return "";
    }

    @RequestMapping(value = "updateAdminInfo", method = RequestMethod.POST)
    public String updateAdminInfo(@RequestParam("type") String type, @ModelAttribute OsAdmin osAdmin, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("osAdmin", osAdmin);
            return "redirect:";
        }
        osAdmin.setStatus(1);
        osAdmin.setReviewer("1".equals(type) ? "1" : "0");
        adminService.updateById(osAdmin);
        return "";
    }

    @RequestMapping(value = "adminInfo", method = RequestMethod.GET)
    public String findOne(@RequestParam("id") int id, Model model) {
        OsAdmin osAdmin = adminService.findOne(id);
        model.addAttribute("success", null != osAdmin ? true : false);
        model.addAttribute("msg", null != osAdmin ? "" : "用户不存在");
        model.addAttribute("data", null != osAdmin ? osAdmin : "");
        return "";
    }

    @RequestMapping(value = "admins", method = RequestMethod.GET)
    public String findAllPage() {
        return "";
    }

    @RequestMapping(value = "admins", method = RequestMethod.POST)
    public Map<String, Object> findAllAdmin(@ModelAttribute AdminDomain adminDomain) {
        Map<String, Object> map = new HashMap<>(5);
        Page<OsAdmin> tmp = adminService.findAll(adminDomain);
        boolean hasResult = !tmp.getContent().isEmpty();
        map.put("success", hasResult ? true : false);
        map.put("msg", hasResult ? "" : "记录不存在");
        map.put("data", hasResult ? tmp.getContent() : "");
        map.put("iTotalRecords", hasResult ? tmp.getTotalElements() : "");
        map.put("iTotalDisplayRecords", hasResult ? tmp.getNumberOfElements() : "");
        return map;
    }

    @RequestMapping(value = "reviewers", method = RequestMethod.GET)
    public String findAllReviewerPage() {
        return "";
    }

    @RequestMapping(value = "reviewers", method = RequestMethod.POST)
    public Map<String, Object> findAllReviewer(@ModelAttribute AdminDomain adminDomain) {
        Page<OsAdmin> tmp = adminService.findAllReviewer(adminDomain);
        boolean hasResult = !tmp.getContent().isEmpty();
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", hasResult ? true : false);
        map.put("msg", hasResult ? "" : "记录不存在");
        map.put("data", hasResult ? tmp.getContent() : "");
        map.put("iTotalRecords", hasResult ? tmp.getTotalElements() : "");
        map.put("iTotalDisplayRecords", hasResult ? tmp.getNumberOfElements() : "");
        return map;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        request.getSession().setAttribute("adminid", 1);
    }
}
