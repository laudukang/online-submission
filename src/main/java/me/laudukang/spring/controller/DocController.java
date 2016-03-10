package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsDoc;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IDocService;
import me.laudukang.spring.domain.DocDomain;
import me.laudukang.util.MapUtil;
import me.laudukang.util.TimeStampPropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/10
 * <p>Time: 21:28
 * <p>Version: 1.0
 */
@Controller
public class DocController {
    @Autowired
    private IDocService docService;

    public final String DOC_NEW_PUBLISH = "待审阅";
    public final String DOC_REVIEWING = "审阅中";
    public final String DOC_REVIEW_RETURN = "退修稿";
    public final String DOC_REVIEW_PASS = "已采编";

    @RequestMapping(value = "newDoc", method = RequestMethod.GET)
    public String newDocPage(Model model) {
        model.addAttribute("osDoc", new OsDoc());
        return "";
    }

    @RequestMapping(value = "newDoc", method = RequestMethod.POST)
    public String newDoc(@ModelAttribute OsDoc osDoc, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("osDoc", osDoc);
            return "redirect:";
        }
        int userid = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
        osDoc.setOsUser(new OsUser(userid));
        osDoc.setPostTime(Timestamp.valueOf(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(new Date())));
        osDoc.setStatusTime(Timestamp.valueOf(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(new Date())));
        osDoc.setStatus(DOC_NEW_PUBLISH);
        docService.save(osDoc);
        return "";
    }

    @RequestMapping(value = "deleteDoc", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        OsDoc tmp = docService.findOne(id);
        if (null != tmp && !DOC_NEW_PUBLISH.equals(tmp.getStatus())) {
            return MapUtil.deleteForbiddenMap;
        }
        docService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "deleteDocSuper", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteSuper(@RequestParam("id") int id) {
        docService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "docs", method = RequestMethod.GET)
    public String docsPage() {
        return "";
    }

    @RequestMapping(value = "docs", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docs(@ModelAttribute DocDomain docDomain) {
        Page<OsDoc> tmp = docService.findAll(docDomain);
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
        binder.registerCustomEditor(Timestamp.class,
                new TimeStampPropertyEditor());
        request.getSession().setAttribute("userid", 1);
    }
}
