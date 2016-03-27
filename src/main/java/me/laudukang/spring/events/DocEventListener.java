package me.laudukang.spring.events;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.*;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.persistence.service.ICustomEmailService;
import me.laudukang.persistence.service.IDocService;
import me.laudukang.persistence.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/22
 * <p>Time: 14:40
 * <p>Version: 1.0
 */
@Component
public class DocEventListener implements ApplicationListener<DocEvent> {
    @Autowired
    private ICustomEmailService iCustomEmailService;
    @Autowired
    private IDocService iDocService;
    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private IMessageService iMessageService;
    @Autowired
    private SimpleDateFormat simpleDateFormat;

    private final String DOC_NEW_PUBLISH = "待审阅";
    private final String DOC_REVIEWING = "审阅中";
    private final String DOC_REVIEW_RETURN = "退修稿";
    private final String DOC_REVIEW_PASS = "已采编";
    private final String ADMIN_REVIEW_PASS = "拟采编";
    private final String ADMIN_REVIEW_RETURN = "拟退稿";

    @Override
    public void onApplicationEvent(DocEvent event) {
        OsDoc osDoc = iDocService.findOne(event.getDocid());
        OsUser osUser = osDoc.getOsUser();
        OsAdmin osAdmin = iAdminService.findOne(event.getAdminid());

        if (event.getMailType().equalsIgnoreCase("distribute")) {
            StringBuilder suject = new StringBuilder("管理员[")
                    .append(event.getAccount())
                    .append("]邀请您审阅稿件[")
                    .append(osDoc.getZhTitle())
                    .append("]-网络投稿系统服务邮件");

            StringBuilder content = new StringBuilder(
                    "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                    .append(!Strings.isNullOrEmpty(osAdmin.getName()) ? osAdmin.getName() : osAdmin.getAccount())
                    .append(",您好<br>管理员[")
                    .append(event.getAccount())
                    .append("]邀请您审阅稿件:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中文标题:")
                    .append(!Strings.isNullOrEmpty(osDoc.getZhTitle()) ? osDoc.getZhTitle() : "")
                    .append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关键词:")
                    .append(!Strings.isNullOrEmpty(osDoc.getZhKeyword()) ? osDoc.getZhKeyword() : "")
                    .append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中文摘要:")
                    .append(!Strings.isNullOrEmpty(osDoc.getZhSummary()) ? osDoc.getZhSummary() : "")
                    .append("<br><a href=\"")
                    .append(event.getUrl())
                    .append("/admin/login?time=")
                    .append(new Date().getTime())
                    .append("\">")
                    .append("详情信息请登录系统查看</a><br>")
                    .append(simpleDateFormat.format(new Date()))
                    .append("<br>系统自动发送邮件，请勿回复</body></html>");
            iCustomEmailService.send(osAdmin.getAccount(), suject.toString(), content.toString());

            OsMessage osMessage = new OsMessage();
            osMessage.setTitle("您有一份稿件待审阅");
            osMessage.setContent("管理员[" + event.getAccount() + "]邀请您审阅稿件[" + osDoc.getZhTitle() + "]");
            osMessage.setOsAdmin(osAdmin);
            iMessageService.save(osMessage);

            osDoc.setStatus(DOC_REVIEWING);
            iDocService.save(osDoc);

        } else if (event.getMailType().equalsIgnoreCase("reviewResult")) {
            StringBuilder subject = new StringBuilder("稿件[")
                    .append(osDoc.getZhTitle())
                    .append("]有新的审阅结果:")
                    .append(event.getPropose());
            StringBuilder content = new StringBuilder(
                    "<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body>尊敬的")
                    .append(!Strings.isNullOrEmpty(osUser.getName()) ? osUser.getName() : osUser.getAccount())
                    .append(",您好<br>您的稿件[")
                    .append(osDoc.getZhTitle())
                    .append("]有新的审阅结果:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;建议:")
                    .append(event.getPropose())
                    .append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评语:")
                    .append(event.getReviewResult())
                    .append("<br><a href=\"")
                    .append(event.getUrl())
                    .append("/login?time=")
                    .append(new Date().getTime())
                    .append("\">")
                    .append("详情信息请登录系统查看</a><br>")
                    .append(simpleDateFormat.format(new Date()))
                    .append("<br>系统自动发送邮件，请勿回复</body></html>");
            iCustomEmailService.send(osUser.getAccount(), subject.toString(), content.toString());

            OsMessage osMessage = new OsMessage();
            osMessage.setTitle("稿件[" + osDoc.getZhTitle() + "]有了新的动态");
            osMessage.setContent("审稿员[" + (!Strings.isNullOrEmpty(osAdmin.getName()) ? osAdmin.getName() : osAdmin.getAccount())
                    + "]的审阅结果为:" + event.getPropose() + "," + event.getReviewResult());
            osMessage.setOsUser(osUser);
            iMessageService.save(osMessage);

            //更新投稿状态
            int return_count = 0, pass_count = 0;
            List<OsDocAdmin> osDocAdminList = osDoc.getOsDocAdmins();
            for (OsDocAdmin osDocAdmin : osDocAdminList) {
                if (osDocAdmin.getPropose().equals(DOC_REVIEWING)) {
                    break;
                } else if (osDocAdmin.getPropose().equals(DOC_REVIEW_PASS)) {
                    pass_count++;
                }
                return_count++;
            }
            if (pass_count == osDocAdminList.size()) {
                osDoc.setStatus(DOC_REVIEW_PASS);
                iDocService.save(osDoc);
            } else if (return_count == osDocAdminList.size()) {
                osDoc.setStatus(DOC_REVIEW_RETURN);
                iDocService.save(osDoc);
            }
        }
    }
}
