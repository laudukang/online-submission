package me.laudukang.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/19
 * Time: 16:25
 * Version: 1.0
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

        return result;
    }
}
