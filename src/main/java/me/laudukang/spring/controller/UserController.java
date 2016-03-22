package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IUserService;
import me.laudukang.spring.domain.LoginDomain;
import me.laudukang.spring.domain.PasswordDomain;
import me.laudukang.spring.domain.UserDomain;
import me.laudukang.spring.events.EmailEvent;
import me.laudukang.util.MapUtil;
import org.apache.commons.lang3.math.NumberUtils;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
        model.addAttribute("loginDomain", new LoginDomain());
        return "";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute @Valid LoginDomain loginDomain, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        OsUser tmp = iUserService.login(loginDomain.getAccount(), loginDomain.getPassword());
        if (null != tmp) {
            session.setAttribute("userid", tmp.getId()); //user.id,user.account,user.name
            session.setAttribute("account", tmp.getAccount());
            session.setAttribute("name", null != tmp.getName() ? tmp.getName() : tmp.getAccount());
            return "redirect:";
        }
        bindingResult.rejectValue("account", "", "账号或密码不正确");
        model.addAttribute("success", false);
        model.addAttribute("msg", "账号或密码不正确");
        return "";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePasswordPage(Model model) {
        model.addAttribute("passwordDomain", new PasswordDomain());
        return "index";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute @Valid PasswordDomain passwordDomain, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        boolean isSame = passwordDomain.getNewPassword1().equals(passwordDomain.getNewPassword2());
        if (!isSame) {
            bindingResult.rejectValue("newPassword1", "", "新密码前后输入不一致");
            return "";
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
        return "";
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String findOne(Model model, HttpSession session) {
        String str;
        int id;
        if (null != session.getAttribute("userid") && !isNullOrEmpty(str = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(str)) {
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

    @RequestMapping(value = "userInfo", method = RequestMethod.POST)
    public String updateUserInfo(@ModelAttribute @Valid UserDomain userDomain, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        String str;
        int id;
        boolean isUpdate = false;
        if (null != session.getAttribute("userid") && !isNullOrEmpty(str = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(str)) {
            id = Integer.valueOf(str);
            userDomain.setId(id);
            iUserService.updateById(userDomain);
            isUpdate = true;
        }
        model.addAttribute("success", isUpdate);
        model.addAttribute("msg", isUpdate ? "更新成功" : "更新失败,用户未登陆");
        bindingResult.rejectValue("account", "", "成功修改信息");
        return "redirect:userInfo";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String newUserPage(Model model) {
        model.addAttribute("userDomain", new UserDomain());
        return "";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String newUser(@ModelAttribute @Valid UserDomain userDomain, BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "";
        }
        if (iUserService.existAccount(userDomain.getAccount())) {
            bindingResult.rejectValue("account", "", "改邮箱账号已经存在");
            return "";
        }
        OsUser osUser = new OsUser();
        osUser.setAccount(userDomain.getAccount());
        osUser.setAddress(userDomain.getAddress());
        osUser.setSex(userDomain.getSex());
        osUser.setName(userDomain.getName());
        osUser.setPostcode(userDomain.getPostcode());
        osUser.setCountry(userDomain.getCountry());
        osUser.setProvince(userDomain.getProvince());
        osUser.setCity(userDomain.getCity());
        osUser.setPassword(userDomain.getPassword());
        osUser.setBirth(userDomain.getBirth());
        osUser.setRemark(userDomain.getRemark());
        osUser.setOfficePhone(userDomain.getOfficePhone());
        osUser.setMobilePhone(userDomain.getMobilePhone());
        osUser.setStatus("normal");

        iUserService.save(osUser);
        model.addAttribute("success", true);
        model.addAttribute("msg", "保存成功");

        //发送注册邮件
        StringBuffer sb = new StringBuffer(
                "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                .append(userDomain.getAccount())
                .append(",您好</br>感谢使用网络投稿系统</br>")
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
        EmailEvent emailEvent = new EmailEvent(this, userDomain.getAccount(), "注册成功-网络投稿系统服务邮件", sb.toString());
        applicationContext.publishEvent(emailEvent);

        return "redirect:login";
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String usersPage() {
        return "";
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
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

    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iUserService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "resetRequest", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resetRequest(@RequestParam("account") String account, HttpServletRequest request) {
        OsUser osUser = iUserService.findByAccount(account);
        if (null != osUser) {
            osUser.setStatus("resetting");
            iUserService.save(osUser);
            StringBuffer sb = new StringBuffer(
                    "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                    .append(!isNullOrEmpty(osUser.getName()) ? osUser.getName() : osUser.getAccount())
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
                    .append("&time=")
                    .append(new Date().getTime())
                    .append("\">")
                    .append("点击这里进入重置密码页面,如非本人操作请忽略")
                    .append("</a></body></html>");

            EmailEvent emailEvent = new EmailEvent(this, account, "重置密码申请-网络投稿系统服务邮件", sb.toString());
            applicationContext.publishEvent(emailEvent);
            return MapUtil.getUserPasswordResetRequstSuccessMap();
        }
        return MapUtil.getUserPasswordResetRequstFailMap();
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.GET)
    public String resetPasswordPage(@RequestParam("account") String account, @RequestParam("time") String time, HttpSession session, Model model) {
        if (NumberUtils.isNumber(time)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Integer.valueOf(time) + 24 * 60 * 60 * 1000);
            if (calendar.getTime().before(new Date()) && !isNullOrEmpty(account)) {
                session.setAttribute("accountTmp", account);
                return "resetPassword";//返回重置密码页面
            }
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "链接失效");
        return "redirect:login";//返回登录页面
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam("password") String password, Model model, HttpSession session) {
        String account = null != session.getAttribute("accountTmp") ? String.valueOf(session.getAttribute("accountTmp")) : "";
        if (!isNullOrEmpty(account)) {
            OsUser osUser = iUserService.findByAccount(account);
            if ("resetting".equals(osUser.getStatus())) {
                osUser.setPassword(password);
                osUser.setStatus("normal");
                iUserService.save(osUser);
                model.addAttribute("success", true);
                model.addAttribute("msg", "成功重置密码");
                session.removeAttribute("accountTmp");
                return "redirect:login";
            }
        }
        model.addAttribute("success", false);
        model.addAttribute("msg", "非法操作");
        return "redirect:login";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @ExceptionHandler(RuntimeException.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception ex) {
        System.out.println("req.getMethod()=" + req.getMethod());
        System.out.println("Exception Message=" + ex.getMessage());
        ex.printStackTrace();
    }

}
