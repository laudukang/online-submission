package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.persistence.service.ILogService;
import me.laudukang.spring.domain.LogDomain;
import me.laudukang.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/17
 * <p>Time: 10:49
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("admin")
public class LogController {
    @Autowired
    private ILogService iLogService;

    @RequestMapping(value = "deleteLog", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") int id) {
        iLogService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "logs", method = RequestMethod.GET)
    public String logsPage() {
        return "";
    }

    @RequestMapping(value = "logs", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logs(@ModelAttribute LogDomain logDomain, BindingResult bindingResult) {
        Page<OsLog> osLogPage = iLogService.findAll(logDomain);
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", !osLogPage.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", osLogPage.getContent());
        map.put("iTotalRecords", osLogPage.getTotalElements());
        map.put("iTotalDisplayRecords", osLogPage.getNumberOfElements());
        return map;
    }

}
