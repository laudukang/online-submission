package me.laudukang.spring.controller;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsAuthor;
import me.laudukang.persistence.model.OsDoc;
import me.laudukang.persistence.model.OsDocAdmin;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IDocService;
import me.laudukang.persistence.service.IUserService;
import me.laudukang.spring.domain.AuthorDomain;
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
import java.util.*;

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
    private IUserService iUserService;
    @Autowired
    private Environment environment;

    private final String DOC_NEW_PUBLISH = "待审阅";
    private final String DOC_REVIEWING = "审阅中";
    private final String DOC_REVIEW_RETURN = "退修稿";
    private final String DOC_REVIEW_PASS = "已采编";

    @RequestMapping(value = {"docInfo/{id}", "admin/docInfo/{id}"}, method = RequestMethod.GET)
    public String docInfoPage(@PathVariable("id") int id, Model model) {
        OsDoc osDoc = iDocService.findOne(id);
        model.addAttribute("osDoc", osDoc);
        return "docDetail";
    }

    @RequestMapping(value = "admin/reviewDocInfo/{id}", method = RequestMethod.GET)
    public String docInfoReviewPage(@PathVariable("id") int id, Model model) {
        OsDoc osDoc = iDocService.findOne(id);
        model.addAttribute("osDoc", osDoc);
        return "review_docDetail";
    }

    @RequestMapping(value = "newDoc", method = RequestMethod.GET)
    public String newDocPage(Model model, HttpSession session) {
        String str;
        if (null != session.getAttribute("userid") && !Strings.isNullOrEmpty(str = String.valueOf(session.getAttribute("userid")))
                && NumberUtils.isNumber(str)) {
            OsUser osUser = iUserService.findOne(Integer.valueOf(str));
            model.addAttribute("osUser", osUser);
        } else {
            model.addAttribute("osUser", new OsUser());
        }
        model.addAttribute("docDomain", new DocDomain());
        return "submitDoc";
    }

    @RequestMapping(value = "newDoc", method = RequestMethod.POST)
    public String newDoc(@ModelAttribute @Valid DocDomain docDomain, BindingResult bindingResult, Model model, HttpSession session) {

//        if (bindingResult.hasErrors()) {
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                System.out.println(error.getObjectName() + " : "
//                        + error.getField() + " " + error.getDefaultMessage());
//            }
//            return "submitDoc";
//        }

        MultipartFile uploadFile = docDomain.getUploadFile();
        String newFileName = null;
        if (null == uploadFile && uploadFile.getSize() == 0) {
            bindingResult.addError(new FieldError("docDomain", "uploadFile", "未选中任何文件"));
        } else {
            try {
                newFileName = uploadPDF(uploadFile, bindingResult);
            } catch (IOException e) {
                e.printStackTrace();
                return "submitDoc";
            }
        }

        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getObjectName() + " : "
                        + error.getField() + " " + error.getDefaultMessage());
            }
            return "submitDoc";
        }

        int userid = Integer.valueOf(String.valueOf(session.getAttribute("userid")));

        OsDoc osDoc = new OsDoc();
        osDoc.setSubject("");//暂时忽略该字段
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

        for (AuthorDomain authorDomain : docDomain.getAuthorDomainList()) {
            if (authorDomain == null) continue;
            OsAuthor osAuthor = new OsAuthor();
            osAuthor.setAddress(authorDomain.getAddress());
            osAuthor.setBirth(authorDomain.getBirth());
            osAuthor.setCountry(authorDomain.getCountry());
            osAuthor.setProvince(authorDomain.getProvince());
            osAuthor.setCity(authorDomain.getCity());
            osAuthor.setMail(authorDomain.getMail());
            osAuthor.setMobilePhone(authorDomain.getMobilePhone());
            osAuthor.setOfficePhone(authorDomain.getOfficePhone());
            osAuthor.setPostcode(authorDomain.getPostcode());
            osAuthor.setName(authorDomain.getName());
            osAuthor.setSex(authorDomain.getSex());
            osAuthor.setRemark("");//暂时忽略该字段
            osDoc.addOsAuthor(osAuthor);
        }

        iDocService.save(osDoc);
        return "redirect:/docs";
    }

    @RequestMapping(value = "updateDoc", method = RequestMethod.GET)
    public String updateDoc(@RequestParam("id") int id, Model model) {
        OsDoc osDoc = iDocService.findOne(id);
        model.addAttribute("osDoc", osDoc);
        return "updateDoc";
    }

    @RequestMapping(value = "updateDoc", method = RequestMethod.POST)
    public String updateDoc(@ModelAttribute @Valid DocDomain docDomain, BindingResult bindingResult, Model model, HttpSession session) {
        MultipartFile uploadFile = docDomain.getUploadFile();
        String newFileName;
        try {
            newFileName = uploadPDF(uploadFile, bindingResult);
        } catch (IOException e) {
            e.printStackTrace();
            return "updateDoc";
        }
        if (bindingResult.hasErrors()) {
            return "updateDoc";
        }

        OsDoc osDoc = iDocService.findOne(docDomain.getId());
        osDoc.setClassification(docDomain.getClassification());
        osDoc.setEnKeyword(docDomain.getEnKeyword());
        osDoc.setEnSummary(docDomain.getEnSummary());
        osDoc.setEnTitle(docDomain.getEnTitle());
        osDoc.setType(docDomain.getType());
        osDoc.setZhTitle(docDomain.getZhTitle());
        osDoc.setZhKeyword(docDomain.getZhKeyword());
        osDoc.setZhSummary(docDomain.getZhSummary());
        osDoc.setStatus(DOC_NEW_PUBLISH);
        if (uploadFile != null && uploadFile.getSize() != 0) {
            File oldPDF = new File(environment.getProperty("file.upload.path") + File.separator + osDoc.getPath());
            oldPDF.deleteOnExit();
            osDoc.setPath(newFileName);
        }

        List<OsAuthor> authorDeleteList = osDoc.getOsAuthors();
        for (OsAuthor osAuthor : authorDeleteList) {
            osAuthor.setOsDoc(null);
        }

        List<OsAuthor> osAuthorList = new ArrayList<>(docDomain.getAuthorDomainList().size());
        for (AuthorDomain authorDomain : docDomain.getAuthorDomainList()) {
            if (authorDomain == null) continue;
            OsAuthor osAuthor = new OsAuthor();
            osAuthor.setAddress(authorDomain.getAddress());
            osAuthor.setBirth(authorDomain.getBirth());
            osAuthor.setCountry(authorDomain.getCountry());
            osAuthor.setProvince(authorDomain.getProvince());
            osAuthor.setCity(authorDomain.getCity());
            osAuthor.setMail(authorDomain.getMail());
            osAuthor.setMobilePhone(authorDomain.getMobilePhone());
            osAuthor.setOfficePhone(authorDomain.getOfficePhone());
            osAuthor.setPostcode(authorDomain.getPostcode());
            osAuthor.setName(authorDomain.getName());
            osAuthor.setSex(authorDomain.getSex());
            osAuthor.setRemark("");//暂时忽略该字段
            osAuthor.setOsDoc(osDoc);
            osAuthorList.add(osAuthor);
        }
        osDoc.setOsAuthors(osAuthorList);

        iDocService.update(osDoc, authorDeleteList);
        return "redirect:/docs";
    }

    @RequestMapping(value = "deleteDoc/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteDocUser(@PathVariable("id") int id) {
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

    @RequestMapping(value = {"docs"}, method = RequestMethod.GET)
    public String docsUserPage() {
        return "docList";
    }

    @RequestMapping(value = "admin/review", method = RequestMethod.GET)
    public String docsReviewPage() {
        return "review_reviewList";
    }

    @RequestMapping(value = "admin/docs", method = RequestMethod.GET)
    public String docsSuperPage() {
        return "admin_docList";
    }

    @RequestMapping(value = "admin/distributedDocs", method = RequestMethod.GET)
    public String distributedDocsPage() {
        return "admin_sendedList";
    }

    @RequestMapping(value = "admin/distributedDocs", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> distributedDocs(@ModelAttribute DocDomain docDomain, BindingResult bindingResult) {
        Page<OsDoc> osDocPage = iDocService.findDistributedDoc(docDomain);
        List<DocDomain> docDomainList = new ArrayList<>(osDocPage.getContent().size());
        for (OsDoc osDoc : osDocPage.getContent()) {
            DocDomain domain = new DocDomain();
            domain.setId(osDoc.getId());
            domain.setZhTitle(osDoc.getZhTitle());
            domain.setPostTime(osDoc.getPostTime());
            domain.setStatus(osDoc.getStatus());
            domain.setClassification(osDoc.getClassification());
            domain.setType(osDoc.getType());
            StringBuilder stringBuilder = new StringBuilder();
            for (OsDocAdmin osDocAdmin : osDoc.getOsDocAdmins()) {
                stringBuilder.append(!Strings.isNullOrEmpty(osDocAdmin.getOsAdmin().getName())
                        ? osDocAdmin.getOsAdmin().getName() : osDocAdmin.getOsAdmin().getAccount()).append(";");
            }
            int index = stringBuilder.lastIndexOf(";");
            if (-1 != index)
                domain.setName(stringBuilder.substring(0, index));
            docDomainList.add(domain);
        }
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
        map.put("msg", !osDocPage.getContent().isEmpty() ? "" : "记录不存在");
        map.put("data", docDomainList);
        map.put("iTotalRecords", osDocPage.getTotalElements());
        map.put("iTotalDisplayRecords", osDocPage.getNumberOfElements());
        return map;
    }

    @RequestMapping(value = "admin/docs", method = RequestMethod.POST)
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

    @RequestMapping(value = "admin/review", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsReview(@ModelAttribute DocDomain docDomain, BindingResult bindingResult, HttpSession session) {
        if (NumberUtils.isNumber(String.valueOf(session.getAttribute("adminid")))) {
            docDomain.setAdminid(Integer.valueOf(String.valueOf(session.getAttribute("adminid"))));
        } else {
            return MapUtil.getForbiddenOperationMap();
        }
        Page<OsDoc> osDocPage = iDocService.findByAdminId(docDomain);
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", true);
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
                if (Strings.isNullOrEmpty(osDoc.getZhTitle()))
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

    private String uploadPDF(MultipartFile uploadFile, BindingResult bindingResult) throws IOException {
        StringBuilder newFileName = new StringBuilder("");
        if (null != uploadFile && uploadFile.getSize() != 0) {
            String fileName = uploadFile.getOriginalFilename();
            String suffix = null;
            int index = fileName.lastIndexOf('.');
            if (-1 == index || !(suffix = fileName.substring(fileName.lastIndexOf('.'))).equalsIgnoreCase(".PDF")) {
                bindingResult.addError(new FieldError("docDomain", "uploadFile", "文件类型不匹配"));
                return newFileName.toString();
            } else {
                int fileSizeLimit;
                try {
                    fileSizeLimit = Integer.valueOf(environment.getProperty("file.upload.size"));
                } catch (Exception e) {
                    fileSizeLimit = 100;
                }
                if (uploadFile.getSize() > 1024 * 1024 * fileSizeLimit) {
                    bindingResult.addError(new FieldError("docDomain", "uploadFile", "上传文件不允许超过" + fileSizeLimit + "MB"));
                    return newFileName.toString();
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            newFileName.append(sdf.format(new Date()))
                    .append("_")
                    .append(new Random().nextInt(100))
                    .append(suffix);
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
                throw e;
            }
        }
        return newFileName.toString();
    }

    @ExceptionHandler(RuntimeException.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception ex) {
        System.out.println("req.getMethod()=" + req.getMethod());
        System.out.println("Exception Message=" + ex.getMessage());
        ex.printStackTrace();
    }
}
