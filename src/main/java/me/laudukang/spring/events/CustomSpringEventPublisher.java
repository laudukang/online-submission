package me.laudukang.spring.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/3
 * <p>Time: 20:35
 * <p>Version: 1.0
 */
@Component
public class CustomSpringEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    //public void publishEvent(final String message) {
    //    System.out.println("in CustomSpringEventPublisher");
    //    final LogEvent customSpringEvent = new LogEvent(this, message);
    //    applicationEventPublisher.publishEvent(customSpringEvent);
    //}

    public void publishLogEvent(final String content, final String userOrAdminName, final String ip) {
        final LogEvent customSpringEvent = new LogEvent(this, content, userOrAdminName, ip);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }

}
