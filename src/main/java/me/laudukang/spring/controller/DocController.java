package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsDoc;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IDocService;
import me.laudukang.spring.domain.DocDomain;
import me.laudukang.util.MapUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/10
 * <p>Time: 21:28
 * <p>Version: 1.0
 */
@Controller
public class DocController {
    @Autowired
    private IDocService iDocService;
    @Autowired
    private Environment environment;

    public final String DOC_NEW_PUBLISH = "待审阅";
    public final String DOC_REVIEWING = "审阅中";
    public final String DOC_REVIEW_RETURN = "退修稿";
    public final String DOC_REVIEW_PASS = "已采编";

    @RequestMapping(value = "newDoc", method = RequestMethod.GET)
    public String newDocPage(Model model) {
        model.addAttribute("osDoc", new OsDoc());
        return "";
    }

    @RequestMapping(value = "newDoc", method = RequestMethod.POST)
    public String newDoc(@ModelAttribute OsDoc osDoc, BindingResult bindingResult, MultipartFile uploadFile, Model model, HttpSession session) {
        if (uploadFile.isEmpty()) {
            bindingResult.addError(new FieldError("osDoc", "uploadFile",
                    "未选中任何文件"));
        }

        String fileName = uploadFile.getOriginalFilename();
        int index = fileName.lastIndexOf('.');
        String suffix = null;

        if (-1 == index || !(suffix = fileName.substring(fileName.lastIndexOf('.'))).toUpperCase().equalsIgnoreCase(".PDF")) {
            bindingResult.addError(new FieldError("osDoc", "uploadFile", "文件类型不匹配"));
        }
        if (uploadFile.getSize() > 1024 * 1024 * 100) {
            bindingResult.addError(new FieldError("osDoc", "uploadFile", "文件大小超过100M"));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder newFileName = new StringBuilder()
                .append(sdf.format(new Date())).append("_")
                .append(new Random().nextInt(100)).append(suffix);
        File uploadPath = new File(environment.getProperty("file.upload.path"));
        if (!uploadPath.exists()) {
            uploadPath.mkdir();
        }
        File saveFile = new File(environment.getProperty("file.upload.path") + File.separator + newFileName);
        try {
            uploadFile.transferTo(saveFile);
        } catch (IOException e) {
            bindingResult.addError(new FieldError("osDoc", "uploadFile", "文件上传失败，未知错误"));
            e.printStackTrace();
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("osDoc", osDoc);
            return "redirect:";
        }

        int userid = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
        osDoc.setOsUser(new OsUser(userid));
        osDoc.setPostTime(Timestamp.valueOf(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(new Date())));
        osDoc.setStatusTime(Timestamp.valueOf(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(new Date())));
        osDoc.setStatus(DOC_NEW_PUBLISH);
        osDoc.setPath(newFileName.toString());
        iDocService.save(osDoc);
        return "";
    }

    @RequestMapping(value = "deleteDoc", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteDocUser(@RequestParam("id") int id) {
        OsDoc tmp = iDocService.findOne(id);
        if (null != tmp && !DOC_NEW_PUBLISH.equals(tmp.getStatus())) {
            return MapUtil.deleteForbiddenMap();
        }
        iDocService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "deleteDocSuper", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteDocSuper(@RequestParam("id") int id) {
        iDocService.deleteById(id);
        return MapUtil.deleteMap();
    }

    // TODO: 2016/3/11 三个稿件列表页面
    @RequestMapping(value = "docs", method = RequestMethod.GET)
    public String docsPage() {
        return "docList";
    }

    @RequestMapping(value = "docsSuper", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsSuper(@ModelAttribute DocDomain docDomain, BindingResult bindingResult) {
        Page<OsDoc> tmp = iDocService.findAllSuper(docDomain);
        boolean hasResult = !tmp.getContent().isEmpty();
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", hasResult ? true : false);
        map.put("msg", hasResult ? "" : "记录不存在");
        map.put("data", hasResult ? tmp.getContent() : null);
        map.put("iTotalRecords", hasResult ? tmp.getTotalElements() : 0);
        map.put("iTotalDisplayRecords", hasResult ? tmp.getNumberOfElements() : 0);
        return map;
    }

    @RequestMapping(value = "docs", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsUser(@ModelAttribute DocDomain docDomain, BindingResult bindingResult, HttpSession session) {
        int userid = 0;
        if (NumberUtils.isNumber(String.valueOf(session.getAttribute("userid")))) {
            userid = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
        }
        docDomain.setUserid(userid);
        Page<OsDoc> tmp = iDocService.findAllByUserId(docDomain);
        boolean hasResult = !tmp.getContent().isEmpty();
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", hasResult ? true : false);
        map.put("msg", hasResult ? "" : "记录不存在");
        map.put("data", tmp.getContent());
        map.put("iTotalRecords", tmp.getTotalElements());
        map.put("iTotalDisplayRecords", tmp.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "docsReview", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsReview(@ModelAttribute DocDomain docDomain, BindingResult bindingResult) {
        Page<OsDoc> tmp = iDocService.findByAdminId(docDomain);
        boolean hasResult = !tmp.getContent().isEmpty();
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", hasResult ? true : false);
        map.put("msg", hasResult ? "" : "记录不存在");
        map.put("data", hasResult ? tmp.getContent() : null);
        map.put("iTotalRecords", hasResult ? tmp.getTotalElements() : 0);
        map.put("iTotalDisplayRecords", hasResult ? tmp.getNumberOfElements() : 0);
        return map;
    }

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam("id") String id) {
        OsDoc osDoc = null;
        HttpHeaders headers = new HttpHeaders();
        if (NumberUtils.isNumber(id)) {
            osDoc = iDocService.findOne(Integer.valueOf(id));
        }
        try {
            if (null != osDoc) {
                String docPath = environment.getProperty("file.upload.path") + File.separator + osDoc.getPath();
                File file = new File(docPath);
                if (!file.exists()) throw new IOException();
                String downloadFileName;
                if (isNullOrEmpty(osDoc.getZhTitle()))
                    downloadFileName = new String(osDoc.getPath().getBytes("gb2312"), "iso-8859-1");
                else downloadFileName = new String((osDoc.getZhTitle() + ".pdf").getBytes("gb2312"), "iso-8859-1");
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", downloadFileName);
                return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
            }
        } catch (IOException ex) {
        }
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>("文件不存在".getBytes(), headers, HttpStatus.BAD_REQUEST);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
//        binder.registerCustomEditor(Timestamp.class,
//                new TimeStampPropertyEditor());
        System.out.println("in initBinder");
    }

    @ExceptionHandler(RuntimeException.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception ex) {
        System.out.println("req.getMethod()=" + req.getMethod());
        System.out.println("Exception Message=" + ex.getMessage());
        ex.printStackTrace();
    }
}
