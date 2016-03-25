package me.laudukang.spring.controller;

import com.google.common.base.Strings;
import me.laudukang.persistence.service.IMessageService;
import me.laudukang.spring.domain.MessageDomain;
import me.laudukang.util.MapUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    @RequestMapping(value = "saveMessage", method = RequestMethod.GET)
//    public String savePgae() {
//        return "";
//    }

//    @RequestMapping(value = "saveMessage", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> save(@RequestParam("userid") String userid, @ModelAttribute MessageDomain messageDomain, BindingResult bindingResult, HttpSession session) {
//        if (Strings.isNullOrEmpty(userid) || NumberUtils.isNumber(userid)) {
//            return MapUtil.getForbiddenOperationMap();
//        }
//        OsUser osUser = new OsUser(Integer.valueOf(userid));
//
//        String adminid = null != session.getAttribute("adminid") ? String.valueOf(session.getAttribute("adminid")) : "1";
//        OsAdmin osAdmin = new OsAdmin(Integer.valueOf(adminid));
//
//        OsMessage osMessage = new OsMessage();
//        osMessage.setOsUser(osUser);
//        osMessage.setOsAdmin(osAdmin);
//        osMessage.setContent(messageDomain.getContent());
//        osMessage.setTitle(messageDomain.getTitle());
//        OsMessage osMessage1 = iMessageService.save(osMessage);
//        return MapUtil.getSaveMap();
//    }

    @RequestMapping(value = "deleteMessage/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") int id) {
        iMessageService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = {"messages", "admin/messages"}, method = RequestMethod.GET)
    public String findMessageByUserIdPage(Model model, HttpSession session) {
        return "message";
    }

    @RequestMapping(value = "userMessages", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findMessageByUserId(Model model, HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        String str;
        if (null != session.getAttribute("userid") && !Strings.isNullOrEmpty(str = String.valueOf(session.getAttribute("userid"))) && NumberUtils.isNumber(str)) {
            List<MessageDomain> osMessageList = iMessageService.findAllByUserId(Integer.valueOf(str));
            map.put("success", true);
            map.put("data", osMessageList);
        } else {
            map.put("success", true);
            map.put("msg", "非法操作");
        }
        return map;
    }

    @RequestMapping(value = "adminMessages", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findMessageByAdminId(Model model, HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        String str;
        if (null != session.getAttribute("adminid") && !Strings.isNullOrEmpty(str = String.valueOf(session.getAttribute("adminid"))) && NumberUtils.isNumber(str)) {
            List<MessageDomain> osMessageList = iMessageService.findAllByAdminId(Integer.valueOf(str));
            map.put("success", true);
            map.put("data", osMessageList);
        } else {
            map.put("success", true);
            map.put("msg", "非法操作");
        }
        return map;
    }
}
