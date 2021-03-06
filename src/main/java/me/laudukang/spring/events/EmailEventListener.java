package me.laudukang.spring.events;

import me.laudukang.persistence.service.ICustomEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/12
 * <p>Time: 15:42
 * <p>Version: 1.0
 */
@Component
public class EmailEventListener implements ApplicationListener<EmailEvent> {
    @Autowired
    private ICustomEmailService customEmailService;

    @Override
    public void onApplicationEvent(EmailEvent event) {
        customEmailService.send(event.getToAddress(), event.getSubject(), event.getContent());
    }
}
