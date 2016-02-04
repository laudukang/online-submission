package me.laudukang.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/19
 * <p>Time: 16:25
 * <p>Version: 1.0
 */
@Controller
public class HiController {

    @RequestMapping(value = "/")
    public String testHome() {
        System.out.println("in testHome");
        return "index";
    }

    @RequestMapping(value = "/json")
    @ResponseBody
    public Map<String, Object> testJson() {
        System.out.println("in testJson");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    @ExceptionHandler(RuntimeException.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception ex) {
        System.out.println("req.getMethod()=" + req.getMethod());
        System.out.println("Exception Message=" + ex.getMessage());
    }
}
