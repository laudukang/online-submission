package me.laudukang.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/24
 * <p>Time: 10:18
 * <p>Version: 1.0
 */
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(60 * 60);// min * second
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    }
}