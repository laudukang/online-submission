package me.laudukang.spring.interceptor;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/15
 * <p>Time: 22:02
 * <p>Version: 1.0
 */
public class DocUploadInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile uploadFile = multipartHttpServletRequest.getFile("uploadFile");
        String fileName = uploadFile.getOriginalFilename();
        int index = fileName.lastIndexOf('.');
        if (-1 == index) return false;
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        if (!suffix.toUpperCase().equalsIgnoreCase(".PDF")) return false;
        return super.preHandle(request, response, handler);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
