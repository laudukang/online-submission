package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IUserService;
import me.laudukang.spring.domain.UserDomain;
import me.laudukang.spring.events.EmailEvent;
import me.laudukang.util.MapUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
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
public class UserController implements ApplicationContextAware {

    @Autowired
    private IUserService iUserService;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("userLoginDomain", new UserDomain());
        return "";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute UserDomain userDomain, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userLoginDomain", userDomain);
            return "redirect:";
        }
        OsUser tmp = iUserService.login(userDomain.getAccount(), userDomain.getPassword());
        if (null != tmp) {
            session.setAttribute("userid", tmp.getId()); //user.id,user.account,user.name
            session.setAttribute("account", tmp.getAccount());
            session.setAttribute("name", null != tmp.getName() ? tmp.getName() : tmp.getAccount());
            return "redirect:";
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "请检查账号或密码是否正确");
        return "redirect:";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePasswordPage() {
        return "updatePassword";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(String password, String newPassword1, String newPassword2, HttpSession session, Model model) {
        boolean check;
        boolean isSame = !isNullOrEmpty(newPassword1) && !isNullOrEmpty(newPassword2) && newPassword1.equals(newPassword2);
        int tmp = 0;
        if (check = !isNullOrEmpty(password)) {
            int id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
            OsUser osUser = iUserService.findOne(id);
            if (null != osUser && osUser.getPassword().equals(password) && isSame) {
                tmp = iUserService.updatePassword(id, newPassword1);
            }
        }
        model.addAttribute("success", check && isSame && 1 == tmp ? true : false);
        model.addAttribute("msg", check && isSame && 1 == tmp ? "成功修改密码" : "原密码输入错误");
        return "redirect:updatePassword";
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String findOne(Model model, HttpSession session) {
        String str;
        int id;
        if (null != session.getAttribute("userid") && !isNullOrEmpty(str = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(str)) {
            id = Integer.valueOf(str);
            OsUser osUser = iUserService.findOne(id);
            model.addAttribute("osUser", osUser);
            return "account";
        }
        return "redirect:/";
    }

//    @RequestMapping(value = "updateUserInfo", method = RequestMethod.GET)
//    public String updateUserInfoPage(Model model, HttpSession session) {
//        model.addAttribute("osUser", new OsUser());
//        return "";
//    }

    @RequestMapping(value = "userInfo", method = RequestMethod.POST)
    public String updateUserInfo(@ModelAttribute OsUser osUser, BindingResult result, Model model, HttpSession session) {
        String str;
        int id;
        boolean isUpdate = false;
        if (null != session.getAttribute("userid") && !isNullOrEmpty(str = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(str)) {
            id = Integer.valueOf(str);
            osUser.setId(id);
            iUserService.updateById(osUser);
            isUpdate = true;
        }
        model.addAttribute("success", isUpdate);
        model.addAttribute("msg", isUpdate ? "更新成功" : "更新失败,用户未登陆");
        return "redirect:userInfo";
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
        if (iUserService.existAccount(osUser.getAccount())) {
            bindingResult.reject("accountExist", "账号已存在");
            return "redirect:";
        }
        osUser.setStatus("normal");
        iUserService.save(osUser);
        model.addAttribute("success", true);
        model.addAttribute("msg", "保存成功");
        return "redirect:";
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iUserService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "resetRequest", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resetRequest(@RequestParam("account") String account, HttpServletRequest request) {
        OsUser osUser = iUserService.findByAccount(account);
        if (null != osUser) {
            osUser.setStatus("resetting");
            iUserService.save(osUser);
            StringBuffer sb = new StringBuffer(
                    "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>")
                    .append(null != osUser.getName() ? osUser.getName() : osUser.getAccount())
                    .append(",您好</br><b>温馨提示</b>：重置密码链接只能使用一次，24小时内有效</br>")
                    .append("<a href=\"")
                    .append(request.getScheme())
                    .append("://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath())
                    .append("/")
                    .append("resetPassword?account=")
                    .append(account)
                    .append("&v=")
                    .append(new Date().getTime())
                    .append("\">")
                    .append("点击这里进入重置密码页面,如非本人操作请忽略")
                    .append("</a></body></html>");
            EmailEvent emailEvent = new EmailEvent(this, account, "找回密码-网络投稿系统服务邮件", sb.toString());
            applicationContext.publishEvent(emailEvent);
            return MapUtil.userPasswordResetRequstSuccessMap();
        }
        return MapUtil.userPasswordResetRequstFailMap();
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.GET)
    public String resetPasswordPage(@RequestParam("v") String v, Model model) {
        if (NumberUtils.isNumber(v)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Integer.valueOf(v) + 24 * 60 * 60 * 1000);
            if (calendar.getTime().before(new Date())) {
                return "redirect:resetPassword";//返回重置密码页面
            }
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "链接失效");
        return "redirect:login";//返回登录页面
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam("account") String account, @RequestParam("password") String password, Model model) {
        if (!isNullOrEmpty(account)) {
            OsUser osUser = iUserService.findByAccount(account);
            if ("resetting".equals(osUser.getStatus())) {
                osUser.setPassword(password);
                iUserService.save(osUser);
                model.addAttribute("success", true);
                model.addAttribute("msg", "成功重置密码");
                return "redirect:login";
            }
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "非法操作");
        return "redirect:/";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        System.out.println("in initBinder");
        //request.getSession().setAttribute("userid", 1);
    }

    @ExceptionHandler(RuntimeException.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception ex) {
        System.out.println("req.getMethod()=" + req.getMethod());
        System.out.println("Exception Message=" + ex.getMessage());
        ex.printStackTrace();
    }

}
