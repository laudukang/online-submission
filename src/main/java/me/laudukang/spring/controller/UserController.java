package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IUserService;
import me.laudukang.spring.domain.UserLoginDomain;
import me.laudukang.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/8
 * <p>Time: 21:59
 * <p>Version: 1.0
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("userLoginDomain", new UserLoginDomain());
        return "";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute UserLoginDomain userLoginDomain, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userLoginDomain", userLoginDomain);
            return "redirect:";
        }
        Object[] tmp = userService.login(userLoginDomain.getAccount(), userLoginDomain.getPassword());
        if (null != tmp && tmp.length > 0) {
            session.setAttribute("userid", tmp[0]); //user.id,user.account,user.name
            session.setAttribute("account", tmp[1]);
            session.setAttribute("name", tmp[2]);
            return "redirect:";
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "请检查账号或密码是否正确");
        return "redirect:";
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
            OsUser osUser = userService.findOne(id);
            if (null != osUser && osUser.getPassword().equals(password) && isSame) {
                tmp = userService.updatePassword(id, newPassword1);
                //return "";//更新成功
            }
        }
        model.addAttribute("success", check && isSame && 1 == tmp ? true : false);
        model.addAttribute("msg", check && isSame && 1 == tmp ? "成功修改密码" : "修改密码失败");
        return "redirect:";
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String findOne(Model model, HttpSession session) {
        int id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
        OsUser osUser = userService.findOne(id);
        model.addAttribute("osUser", osUser);
        return "";
    }

    @RequestMapping(value = "updateUserInfo", method = RequestMethod.GET)
    public String updateUserInfoPage(Model model, HttpSession session) {
        model.addAttribute("osUser", new OsUser());
        return "";
    }

    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public String updateUserInfo(@ModelAttribute OsUser osUser, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("osUser", osUser);
            return "redirect:";
        }
        userService.updateById(osUser);
        model.addAttribute("success", true);
        model.addAttribute("msg", "更新成功");
        return "";
    }

    @RequestMapping(value = "newUser", method = RequestMethod.GET)
    public String newUserPage(Model model) {
        model.addAttribute("osUser", new OsUser());
        return "";
    }

    @RequestMapping(value = "newUser", method = RequestMethod.POST)
    public String newUser(@ModelAttribute OsUser osUser, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("osUser", osUser);
            return "redirect:";
        }
        userService.save(osUser);
        model.addAttribute("success", true);
        model.addAttribute("msg", "保存成功");
        return "redirect:";
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        userService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        request.getSession().setAttribute("userid", 1);
    }

}
