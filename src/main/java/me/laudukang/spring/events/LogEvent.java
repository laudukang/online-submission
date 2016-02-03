package me.laudukang.spring.events;

import org.springframework.context.ApplicationEvent;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/3
 * <p>Time: 20:37
 * <p>Version: 1.0
 */
public class LogEvent extends ApplicationEvent {

    private String content;
    private String ip;
    private String userOrAdminName;

    public LogEvent(Object source) {
        super(source);
    }


    public LogEvent(final Object source, final String content, final String userOrAdminName, final String ip) {
        super(source);
        this.content = content;
        this.userOrAdminName = userOrAdminName;
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public String getIp() {
        return ip;
    }

    public String getUserOrAdminName() {
        return userOrAdminName;
    }
}
