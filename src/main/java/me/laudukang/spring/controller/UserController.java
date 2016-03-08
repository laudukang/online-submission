package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

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
            }
        }
        model.addAttribute("success", check && 1 == tmp ? true : false);
        model.addAttribute("msg", check && 1 == tmp ? "成功修改密码" : "修改密码失败");
        return "";
    }
}
