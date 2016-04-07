package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsDocAdmin;
import me.laudukang.persistence.model.OsDocAdminPK;
import me.laudukang.persistence.service.IDocAdminService;
import me.laudukang.spring.events.DocEvent;
import me.laudukang.util.MapUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/22
 * <p>Time: 14:21
 * <p>Version: 1.0
 */
@Controller
public class DocAdminController implements ApplicationContextAware {
    @Autowired
    private IDocAdminService iDocAdminService;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private final String DOC_NEW_PUBLISH = "待审阅";
    private final String DOC_REVIEWING = "审阅中";
    private final String DOC_REVIEW_RETURN = "退修稿";
    private final String DOC_REVIEW_PASS = "已采编";

    @RequestMapping(value = "admin/distribute", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveDistribute(@RequestParam("docid") int docid, @RequestParam("reviewerid[]") int[] reviewerid,
                                              HttpServletRequest request, HttpSession session) {
        //发送稿件分发邮件
        StringBuilder url = new StringBuilder(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        String account = null != session.getAttribute("name") ? String.valueOf(session.getAttribute("name")) : "ADMIN";

        List<OsDocAdmin> osDocAdminList = new ArrayList<>(reviewerid.length);
        for (int adminid : reviewerid) {
            OsDocAdminPK osDocAdminPK = new OsDocAdminPK(docid, adminid);
            OsDocAdmin osDocAdmin = new OsDocAdmin();
            osDocAdmin.setId(osDocAdminPK);
            osDocAdmin.setPropose(DOC_REVIEWING);
            osDocAdmin.setReviewResult(DOC_REVIEWING);
            osDocAdminList.add(osDocAdmin);

            applicationContext.publishEvent(new DocEvent(this, docid, adminid, "", "", account, "distribute", url.toString()));
        }
        iDocAdminService.save(osDocAdminList);
        return MapUtil.getSaveMap();
    }

    @RequestMapping(value = "admin/saveReview", method = RequestMethod.POST)
    public String saveReview(@RequestParam("id") int docid, @RequestParam("reviewResult") String reviewResult,
                             @RequestParam("propose") String propose, HttpSession session, HttpServletRequest request, Model model) {
        int adminid = Integer.valueOf(String.valueOf(session.getAttribute("adminid")));
        OsDocAdmin osDocAdmin = iDocAdminService.findOneByDocAdmin(docid, adminid);
        osDocAdmin.setReviewResult(reviewResult);
        osDocAdmin.setPropose(propose);
        iDocAdminService.save(osDocAdmin);
        //发送审阅结果邮件到用户
        StringBuilder url = new StringBuilder(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        String account = null != session.getAttribute("name") ? String.valueOf(session.getAttribute("name")) : "ADMIN";
        applicationContext.publishEvent(new DocEvent(this, docid, adminid, propose, reviewResult, account, "reviewResult", url.toString()));
        return "redirect:/admin/reviewDocInfo/" + docid;
    }
}
