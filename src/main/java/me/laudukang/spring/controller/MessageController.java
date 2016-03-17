package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.model.OsMessage;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IMessageService;
import me.laudukang.spring.domain.MessageDomain;
import me.laudukang.util.MapUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/17
 * <p>Time: 15:45
 * <p>Version: 1.0
 */
@Controller
public class MessageController {
    @Autowired
    private IMessageService iMessageService;

    @RequestMapping(value = "saveMessage", method = RequestMethod.GET)
    public String savePgae() {
        return "";
    }

    @RequestMapping(value = "saveMessage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(String userid, String adminid, @ModelAttribute OsMessage osMessage, BindingResult bindingResult, HttpSession session) {
        if (!isNullOrEmpty(userid) && NumberUtils.isNumber(userid)) {
        } else if (null != session.getAttribute("userid") && !isNullOrEmpty(userid = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(userid)) {
        }
        OsUser osUser = new OsUser(Integer.valueOf(userid));
        if (!isNullOrEmpty(adminid) && NumberUtils.isNumber(adminid)) {
        } else if (null != session.getAttribute("adminid") && !isNullOrEmpty(adminid = String.valueOf(session.getAttribute("adminid"))) && NumberUtils.isNumber(adminid)) {
        }
        OsAdmin osAdmin = new OsAdmin(Integer.valueOf(adminid));
        osMessage.setOsUser(osUser);
        osMessage.setOsAdmin(osAdmin);
        OsMessage osMessage1 = iMessageService.save(osMessage);
        return MapUtil.getSaveMap();
    }

    @RequestMapping(value = "deleteMessage", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iMessageService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "userMessages", method = RequestMethod.GET)
    public String findMessageByUserId(Model model, HttpSession session) {
        String str;
        if (null != session.getAttribute("userid") && !isNullOrEmpty(str = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(str)) {
            List<MessageDomain> osMessageList = iMessageService.findAllByUserId(Integer.valueOf(str));
            model.addAttribute("osMessageList", osMessageList);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
            model.addAttribute("msg", "非法操作");
        }
        return "";
    }

    @RequestMapping(value = "adminMessages", method = RequestMethod.GET)
    public String findMessageByAdminId(Model model, HttpSession session) {
        String str;
        if (null != session.getAttribute("adminid") && !isNullOrEmpty(str = String.valueOf(session.getAttribute("adminid"))) && NumberUtils.isNumber(str)) {
            List<MessageDomain> osMessageList = iMessageService.findAllByAdminId(Integer.valueOf(str));
            model.addAttribute("osMessageList", osMessageList);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
            model.addAttribute("msg", "非法操作");
        }
        return "";
    }

}
