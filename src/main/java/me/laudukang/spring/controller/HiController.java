package me.laudukang.spring.controller;

import me.laudukang.persistence.model.Bar;
import me.laudukang.persistence.model.Child;
import me.laudukang.persistence.model.Parent;
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
        result.put("success", true);
        result.put("messgae", "hi lau");

        Bar bar = new Bar();
        bar.setId(1);
        bar.setName("lau");
        result.put("bar", bar);

        Child child = new Child();
        child.setId(2);
        Parent parent = new Parent();

        parent.setId(2);
        //parent.setChild(child);
        child.setParent(parent);
        //result.put("child",child);
        result.put("parent", parent);
        return result;
    }
}
