package me.laudukang.spring.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/2
 * <p>Time: 21:04
 * <p>Version: 1.0
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception exception) {
        return new ModelAndView("404");
    }
}
