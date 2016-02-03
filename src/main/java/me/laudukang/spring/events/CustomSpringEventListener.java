package me.laudukang.spring.events;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.persistence.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
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
public class CustomSpringEventListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    private ILogService logService;

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        //System.out.println("in CustomSpringEventListener onApplicationEvent");
        if (event instanceof LogEvent) {
            logService.save(new OsLog(((LogEvent) event).getContent(), ((LogEvent) event).getUserOrAdminName(), ((LogEvent) event).getIp()));
        }
    }
}