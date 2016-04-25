package me.laudukang.spring.controller;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IUserService;
import me.laudukang.spring.domain.LoginDomain;
import me.laudukang.spring.domain.PasswordDomain;
import me.laudukang.spring.domain.UserDomain;
import me.laudukang.spring.events.EmailEvent;
import me.laudukang.spring.events.LogEvent;
import me.laudukang.util.MapUtil;
import org.apache.commons.lang3.math.NumberUtils;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    SimpleDateFormat simpleDateFormat;
    @Autowired
    DateFormat dateFormat;
    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("loginDomain", new LoginDomain());
        return "login";
    }

    @RequestMapping(value = {"home", "admin/home"}, method = RequestMethod.GET)
    public String welcomePage() {
        return "welcome";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute @Valid LoginDomain loginDomain, BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        StringBuilder stringBuilder = new StringBuilder("用户[").append(loginDomain.getAccount());
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(loginDomain.getAccount(), loginDomain.getPassword()));

            stringBuilder.append("]登录成功");
            applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(),
                    String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("name")), request.getRemoteHost()));
            return "redirect:/home";
        } catch (AuthenticationException ex) {
            stringBuilder.append("]登录失败，密码不正确");
            applicationContext.publishEvent(new LogEvent(this, stringBuilder.toString(), loginDomain.getAccount(), request.getRemoteHost()));
        }

        bindingResult.rejectValue("account", "", "账号或密码不正确");
        model.addAttribute("success", false);
        model.addAttribute("msg", "账号或密码不正确");
        return "login";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePasswordPage(Model model) {
        model.addAttribute("passwordDomain", new PasswordDomain());
        return "updatePassword";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute @Valid PasswordDomain passwordDomain, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            return "updatePassword";
        }
        boolean isSame = passwordDomain.getNewPassword1().equals(passwordDomain.getNewPassword2());
        if (!isSame) {
            bindingResult.rejectValue("newPassword1", "", "新密码前后输入不一致");
            return "updatePassword";
        }
        int tmp = 0;
        int id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
        OsUser osUser = iUserService.findOne(id);
        if (null != osUser && osUser.getPassword().equals(passwordDomain.getPassword()) && isSame) {
            tmp = iUserService.updatePassword(id, passwordDomain.getNewPassword1());
        }
        bindingResult.rejectValue("password", "", 1 == tmp ? "成功修改密码" : "原密码密码输入不对");
        model.addAttribute("success", 1 == tmp);
        model.addAttribute("msg", 1 == tmp ? "成功修改密码" : "原密码输入错误");
        return "redirect:/updatePassword";
    }

    @RequestMapping(value = "admin/userInfo/{id}", method = RequestMethod.GET)
    public String adminVisitUserInfo(@PathVariable("id") int id, Model model, HttpSession session) {
        OsUser osUser = iUserService.findOne(id);
        if (null != osUser) {
            session.setAttribute("userid", osUser.getId());//用于管理员更新评语用户信息
            UserDomain userDomain = new UserDomain();
            userDomain.setId(osUser.getId());
            userDomain.setAccount(osUser.getAccount());
            userDomain.setId(osUser.getId());
            userDomain.setRemark(osUser.getRemark());
            userDomain.setStatus(osUser.getStatus());
            userDomain.setName(osUser.getName());
            userDomain.setAddress(osUser.getAddress());
            userDomain.setMobilePhone(osUser.getMobilePhone());
            userDomain.setOfficePhone(osUser.getOfficePhone());
            userDomain.setBirth(osUser.getBirth());
            userDomain.setCountry(osUser.getCountry());
            userDomain.setProvince(osUser.getProvince());
            userDomain.setCity(osUser.getCity());
            userDomain.setPostcode(osUser.getPostcode());
            userDomain.setSex(osUser.getSex());
            model.addAttribute("userDomain", userDomain);
            return "account";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String loginUserInfo(Model model, HttpSession session) {
        String str;
        int id;
        if (null != session.getAttribute("userid") && !Strings.isNullOrEmpty(str = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(str)) {
            id = Integer.valueOf(str);
            OsUser osUser = iUserService.findOne(id);
            UserDomain userDomain = new UserDomain();
            userDomain.setId(osUser.getId());
            userDomain.setAccount(osUser.getAccount());
            userDomain.setId(osUser.getId());
            userDomain.setRemark(osUser.getRemark());
            userDomain.setStatus(osUser.getStatus());
            userDomain.setName(osUser.getName());
            userDomain.setAddress(osUser.getAddress());
            userDomain.setMobilePhone(osUser.getMobilePhone());
            userDomain.setOfficePhone(osUser.getOfficePhone());
            userDomain.setBirth(osUser.getBirth());
            userDomain.setCountry(osUser.getCountry());
            userDomain.setProvince(osUser.getProvince());
            userDomain.setCity(osUser.getCity());
            userDomain.setPostcode(osUser.getPostcode());
            userDomain.setSex(osUser.getSex());
            model.addAttribute("userDomain", userDomain);
            return "account";
        }
        return "redirect:/login";
    }

//    @RequestMapping(value = "updateUserInfo", method = RequestMethod.GET)
//    public String updateUserInfoPage(Model model, HttpSession session) {
//        model.addAttribute("osUser", new OsUser());
//        return "";
//    }

    @RequestMapping(value = {"userInfo", "admin/userInfo"}, method = RequestMethod.POST)
    public String updateUserInfo(@ModelAttribute @Valid UserDomain userDomain, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getObjectName() + " : "
                        + error.getField() + " " + error.getDefaultMessage());
            }
            return "account";
        }
        if (Strings.isNullOrEmpty(userDomain.getName())) {
            bindingResult.rejectValue("name", "", "姓名不能为空");
            return "account";
        }
        String str;
        int id;
        boolean isUpdate = false;
        if (null != session.getAttribute("userid") && !Strings.isNullOrEmpty(str = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(str)) {
            id = Integer.valueOf(str);
            userDomain.setId(id);
            iUserService.updateById(userDomain);
            isUpdate = true;
            if ("1".equals(session.getAttribute("loginType"))) {
                session.removeAttribute("userid");
            } else {
                session.setAttribute("name", !Strings.isNullOrEmpty(userDomain.getName()) ? userDomain.getName() : String.valueOf(session.getAttribute("account")));
            }
        }
        model.addAttribute("success", isUpdate);
        model.addAttribute("msg", isUpdate ? "更新成功" : "更新失败,用户未授权");
        return "redirect:/userInfo";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String newUserPage(Model model) {
        model.addAttribute("userDomain", new UserDomain());
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute @Valid UserDomain userDomain, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (Strings.isNullOrEmpty(userDomain.getAccount())) {
            bindingResult.rejectValue("account", "", "账号不能为空");
            return "register";
        }
        if (!isEmail(userDomain.getAccount())) {
            bindingResult.rejectValue("account", "", "邮箱格式不正确");
            return "register";
        }
        if (Strings.isNullOrEmpty(userDomain.getPassword())) {
            bindingResult.rejectValue("password", "", "密码不能为空");
            return "register";
        }
        if (iUserService.existAccount(userDomain.getAccount())) {
            bindingResult.rejectValue("account", "", "改邮箱账号已经存在");
            return "register";
        }
        OsUser osUser = new OsUser();
        osUser.setAccount(userDomain.getAccount());
        osUser.setPassword(userDomain.getPassword());
        osUser.setAddress("");
        osUser.setSex("男");
        osUser.setName("");
        osUser.setPostcode("");
        osUser.setCountry("");
        osUser.setProvince("");
        osUser.setCity("");
        osUser.setBirth(new Date());
        osUser.setRemark("");
        osUser.setOfficePhone("");
        osUser.setMobilePhone("");
        osUser.setStatus("正常");
        iUserService.save(osUser);
        model.addAttribute("success", true);
        model.addAttribute("msg", "保存成功");

        //发送注册邮件
        StringBuilder sb = new StringBuilder(
                "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                .append(userDomain.getAccount())
                .append(",您好<br><br>感谢使用网络投稿系统，")
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
                .append(dateFormat.format(new Date()))
                .append("<br>------------------------------<br>系统自动发送邮件，请勿回复</body></html>");
        EmailEvent emailEvent = new EmailEvent(this, userDomain.getAccount(), "注册成功-网络投稿系统服务邮件", sb.toString());
        applicationContext.publishEvent(emailEvent);

        return "redirect:/login";
    }

    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public String usersPage() {
        return "admin_userList";
    }

    @RequestMapping(value = "admin/users", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> users(@ModelAttribute UserDomain userDomain, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>(5);
        Page<OsUser> osUserPage = iUserService.findAll(userDomain);
        map.put("success", !osUserPage.getContent().isEmpty());
        map.put("msg", !osUserPage.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", osUserPage.getContent());
        map.put("iTotalRecords", osUserPage.getTotalElements());
        map.put("iTotalDisplayRecords", osUserPage.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "admin/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iUserService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "resetRequest", method = RequestMethod.GET)
    public String resetRequestPage(Model model) {
        model.addAttribute("userDomain", new UserDomain());
        return "forgivePassword";
    }

    @RequestMapping(value = "resetRequest", method = RequestMethod.POST)
    public String resetRequest(@ModelAttribute UserDomain userDomain, BindingResult bindingResult, HttpServletRequest request, Model model) {
        String account = userDomain.getAccount();
        OsUser osUser = iUserService.findByAccount(account);
        if (null != osUser) {
            osUser.setStatus("密码重置申请中");
            iUserService.save(osUser);
            StringBuilder sb = new StringBuilder(
                    "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                    .append(!Strings.isNullOrEmpty(osUser.getName()) ? osUser.getName() : osUser.getAccount())
                    .append(",您好<br><br>您于")
                    .append(simpleDateFormat.format(new Date()))
                    .append("提交了密码重置申请,如非本人操作请忽略该邮件,")
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
                    .append("&time=")
                    .append(new Date().getTime())
                    .append("\">")
                    .append("点击这里进入重置密码页面</a><br>")
                    .append("<br><b>温馨提示</b>：重置密码链接只能使用一次，24小时内有效<br><br>")
                    .append(dateFormat.format(new Date()))
                    .append("<br><br>------------------------------<br>系统自动发送邮件，请勿回复</body></html>");

            EmailEvent emailEvent = new EmailEvent(this, account, "重置密码申请-网络投稿系统服务邮件", sb.toString());
            applicationContext.publishEvent(emailEvent);
            bindingResult.rejectValue("account", "", "邮件已发送");
            return "forgivePassword";
        }
        bindingResult.rejectValue("account", "", "该邮箱未按注册，请确认");
        return "forgivePassword";
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.GET)
    public String resetPasswordPage(@RequestParam("account") String account, @RequestParam("time") String time, HttpSession session, Model model) {
        if (NumberUtils.isNumber(time)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(time) + 24 * 60 * 60 * 1000);
            if (calendar.getTime().after(new Date()) && !Strings.isNullOrEmpty(account)) {
                session.setAttribute("accountTmp", account);
                return "resetPassword";//返回重置密码页面
            }
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "链接失效");
        return "redirect:/login";//返回登录页面
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam("password") String password, Model model, HttpSession session) {
        String account = null != session.getAttribute("accountTmp") ? String.valueOf(session.getAttribute("accountTmp")) : "";
        if (!Strings.isNullOrEmpty(account)) {
            OsUser osUser = iUserService.findByAccount(account);
            if (null != osUser && "密码重置申请中".equals(osUser.getStatus())) {
                osUser.setPassword(password);
                osUser.setStatus("正常");
                iUserService.save(osUser);
                model.addAttribute("success", true);
                model.addAttribute("msg", "成功重置密码");
                session.removeAttribute("accountTmp");
                return "redirect:login";
            }
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "非法操作");
        return "resetPassword";
    }

//    @RequestMapping(value = "logout")
//    public String logout() {
//        SecurityUtils.getSubject().logout();
//        return "redirect:/login";
//    }

    // email规则
    private static final String EMAIL = "^[a-zA-Z0-9]+([_.]?[a-zA-Z0-9])*@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,3}$";

    private boolean isEmail(String email) {
        return !Strings.isNullOrEmpty(email) && email.matches(EMAIL);
    }
}
