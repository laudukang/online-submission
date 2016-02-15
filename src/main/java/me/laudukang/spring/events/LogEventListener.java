package me.laudukang.spring.events;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.persistence.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/3
 * <p>Time: 20:38
 * <p>Version: 1.0
 */
@Component
public class LogEventListener implements ApplicationListener<LogEvent> {

    @Autowired
    private ILogService logService;


    @Override
    public void onApplicationEvent(final LogEvent event) {
        System.out.println("in CustomSpringEventListener onApplicationEvent");
        //if (event instanceof LogEvent) {
        //logService.save(new OsLog(((LogEvent) event).getContent(), ((LogEvent) event).getUserOrAdminName(), ((LogEvent) event).getIp()));
        OsLog osLog = new OsLog();
        osLog.setContent(event.getContent());
        osLog.setUserOrAdminName(event.getUserOrAdminName());
        osLog.setIp(event.getIp());
        logService.save(osLog);
        //logService.save(new OsLog(event.getContent(), event.getUserOrAdminName(), event.getIp()));
        //}
    }
}