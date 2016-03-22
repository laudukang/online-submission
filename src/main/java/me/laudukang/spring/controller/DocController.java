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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    private final String DOC_NEW_PUBLISH = "待审阅";
    private final String DOC_REVIEWING = "审阅中";
    private final String DOC_REVIEW_RETURN = "退修稿";
    private final String DOC_REVIEW_PASS = "已采编";

    @RequestMapping(value = "docInfo", method = RequestMethod.GET)
    public String docInfoPage(@RequestParam("id") int id, Model model) {
        OsDoc osDoc = iDocService.findOne(id);
        model.addAttribute("osDoc", osDoc);
        return "";
    }


    @RequestMapping(value = "newDoc", method = RequestMethod.GET)
    public String newDocPage(Model model) {
        model.addAttribute("docDomain", new DocDomain());
        return "";
    }

    @RequestMapping(value = "newDoc", method = RequestMethod.POST)
    //@RequestParam("uploadFile") MultipartFile uploadFile,
    public String newDoc(@ModelAttribute @Valid DocDomain docDomain, BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getObjectName() + " : "
                        + error.getField() + " " + error.getDefaultMessage());
            }
            return "";
        }

        MultipartFile uploadFile = docDomain.getUploadFile();
        String fileName = uploadFile.getOriginalFilename();
        int index = fileName.lastIndexOf('.');
        String suffix = null;

        if (-1 == index || !(suffix = fileName.substring(fileName.lastIndexOf('.'))).equalsIgnoreCase(".PDF")) {
            bindingResult.addError(new FieldError("docDomain", "uploadFile", "文件类型不匹配"));
        } else {
            int fileSizeLimit;
            try {
                fileSizeLimit = Integer.valueOf(environment.getProperty("file.upload.size"));
            } catch (Exception e) {
                fileSizeLimit = 100;
            }
            if (uploadFile.getSize() > 1024 * 1024 * fileSizeLimit) {
                bindingResult.addError(new FieldError("docDomain", "uploadFile", "上传文件不允许超过" + fileSizeLimit + "MB"));
            }
        }

        if (bindingResult.hasErrors()) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder newFileName = new StringBuilder()
                .append(sdf.format(new Date())).append("_")
                .append(new Random().nextInt(100)).append(suffix);
        File uploadPath = new File(environment.getProperty("file.upload.path"));
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        File saveFile = new File(environment.getProperty("file.upload.path") + File.separator + newFileName);
        try {
            uploadFile.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            bindingResult.addError(new FieldError("docDomain", "uploadFile", "文件上传失败，服务器错误"));
            return "";
        }

        int userid = Integer.valueOf(String.valueOf(session.getAttribute("userid")));

        OsDoc osDoc = new OsDoc();
        osDoc.setSubject(docDomain.getSubject());
        osDoc.setClassification(docDomain.getClassification());
        osDoc.setEnKeyword(docDomain.getEnKeyword());
        osDoc.setEnSummary(docDomain.getEnSummary());
        osDoc.setEnTitle(docDomain.getEnTitle());
        osDoc.setType(docDomain.getType());
        osDoc.setZhTitle(docDomain.getZhTitle());
        osDoc.setZhKeyword(docDomain.getZhKeyword());
        osDoc.setZhSummary(docDomain.getZhSummary());
        osDoc.setPostTime(Timestamp.valueOf(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(new Date())));
        osDoc.setStatusTime(Timestamp.valueOf(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(new Date())));
        osDoc.setStatus(DOC_NEW_PUBLISH);
        osDoc.setPath(newFileName.toString());
        osDoc.setOsUser(new OsUser(userid));

        iDocService.save(osDoc);
        return "";
    }

    @RequestMapping(value = "deleteDoc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteDocUser(@RequestParam("id") int id) {
        OsDoc tmp = iDocService.findOne(id);
        if (null != tmp && !DOC_NEW_PUBLISH.equals(tmp.getStatus())) {
            return MapUtil.getDeleteForbiddenMap();
        }
        iDocService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "deleteDocSuper", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteDocSuper(@RequestParam("id") int id) {
        iDocService.deleteById(id);
        return MapUtil.getDeleteMap();
    }

    @RequestMapping(value = "docs", method = RequestMethod.GET)
    public String docsUserPage() {
        return "docList";
    }

    @RequestMapping(value = "docsReview", method = RequestMethod.GET)
    public String docsReviewPage() {
        return "docList";
    }

    @RequestMapping(value = "docsSuper", method = RequestMethod.GET)
    public String docsSuperPage() {
        return "docList";
    }

    @RequestMapping(value = "docsSuper", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsSuper(@ModelAttribute DocDomain docDomain, BindingResult bindingResult, HttpSession session) {
        if (null == session.getAttribute("adminid")) {
            return MapUtil.getForbiddenOperationMap();
        }
        Page<OsDoc> osDocPage = iDocService.findAllSuper(docDomain);
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", !osDocPage.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", osDocPage.getContent());
        map.put("iTotalRecords", osDocPage.getTotalElements());
        map.put("iTotalDisplayRecords", osDocPage.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "docs", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsUser(@ModelAttribute DocDomain docDomain, BindingResult bindingResult, HttpSession session) {
        int userid = 0;
        if (NumberUtils.isNumber(String.valueOf(session.getAttribute("userid")))) {
            userid = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
        } else {
            return MapUtil.getForbiddenOperationMap();
        }
        docDomain.setUserid(userid);
        Page<OsDoc> osDocPage = iDocService.findAllByUserId(docDomain);
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", !osDocPage.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", osDocPage.getContent());
        map.put("iTotalRecords", osDocPage.getTotalElements());
        map.put("iTotalDisplayRecords", osDocPage.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "docsReview", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsReview(@ModelAttribute DocDomain docDomain, BindingResult bindingResult, HttpSession session) {
        if (NumberUtils.isNumber(String.valueOf(session.getAttribute("adminid")))) {
            docDomain.setAdminid(Integer.valueOf(String.valueOf(session.getAttribute("adminid"))));
        } else {
            return MapUtil.getForbiddenOperationMap();
        }
        Page<OsDoc> osDocPage = iDocService.findByAdminId(docDomain);
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);//!osDocPage.getContent().isEmpty()
        map.put("msg", !osDocPage.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", osDocPage.getContent());
        map.put("iTotalRecords", osDocPage.getTotalElements());
        map.put("iTotalDisplayRecords", osDocPage.getNumberOfElements());
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

    @ExceptionHandler(RuntimeException.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception ex) {
        System.out.println("req.getMethod()=" + req.getMethod());
        System.out.println("Exception Message=" + ex.getMessage());
        ex.printStackTrace();
    }
}
