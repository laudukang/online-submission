package me.laudukang.spring.controller;

import com.google.common.io.Files;
import me.laudukang.persistence.model.OsDoc;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.service.IDocService;
import me.laudukang.spring.domain.DocDomain;
import me.laudukang.util.MapUtil;
import me.laudukang.util.TimeStampPropertyEditor;
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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    public String newDoc(@ModelAttribute OsDoc osDoc, BindingResult bindingResult, Model model, HttpSession session) {
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
        return "";
    }

    @RequestMapping(value = "docsSuper", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsSuper(@ModelAttribute DocDomain docDomain) {
        Page<OsDoc> tmp = iDocService.findAllSuper(docDomain);
        boolean hasResult = !tmp.getContent().isEmpty();
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", hasResult ? true : false);
        map.put("msg", hasResult ? "" : "记录不存在");
        map.put("data", hasResult ? tmp.getContent() : "");
        map.put("iTotalRecords", hasResult ? tmp.getTotalElements() : "");
        map.put("iTotalDisplayRecords", hasResult ? tmp.getNumberOfElements() : "");
        return map;
    }

    @RequestMapping(value = "docs", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsUser(@ModelAttribute DocDomain docDomain) {
        Page<OsDoc> tmp = iDocService.findAllByUserId(docDomain);
        boolean hasResult = !tmp.getContent().isEmpty();
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", hasResult ? true : false);
        map.put("msg", hasResult ? "" : "记录不存在");
        map.put("data", hasResult ? tmp.getContent() : "");
        map.put("iTotalRecords", hasResult ? tmp.getTotalElements() : "");
        map.put("iTotalDisplayRecords", hasResult ? tmp.getNumberOfElements() : "");
        return map;
    }

    @RequestMapping(value = "docsReview", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docsReview(@ModelAttribute DocDomain docDomain) {
        Page<OsDoc> tmp = iDocService.findByAdminId(docDomain);
        boolean hasResult = !tmp.getContent().isEmpty();
        Map<String, Object> map = new HashMap<>(5);
        map.put("success", hasResult ? true : false);
        map.put("msg", hasResult ? "" : "记录不存在");
        map.put("data", hasResult ? tmp.getContent() : "");
        map.put("iTotalRecords", hasResult ? tmp.getTotalElements() : "");
        map.put("iTotalDisplayRecords", hasResult ? tmp.getNumberOfElements() : "");
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

    @RequestMapping(value = "zip")
    public ResponseEntity<byte[]> zip(HttpServletResponse response) throws IOException {
        //F:/Pictures/20140816172427.jpg
        String path = "D:/apache/zip/751611201@qq.com/laudukang";
        File file = new File("F:/Pictures/杜甫巴莱.jpg");
        String dfileName = new String("test.zip".getBytes("gb2312"), "iso-8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);

        final List<File> files = new ArrayList<File>();
        SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(file.toFile());
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                files.add(dir.toFile());
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            java.nio.file.Files.walkFileTree(Paths.get(path), finder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(bos);
        // BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);

        //byte[] data = new byte[1024];
        byte[] result = null;
        ZipEntry zipEntry = null;
        for (File tmp : files) {
            System.out.println(tmp.getPath());
            File parent = tmp.getParentFile();
            StringBuilder sb = new StringBuilder();
            while (!"751611201@qq.com".equals(tmp.getName()) && null != parent && !"751611201@qq.com".equals(parent.getName())) {
                if (parent.isDirectory())
                    sb.insert(0, parent.getName() + File.separator);
                parent = parent.getParentFile();
            }
            if (tmp.isDirectory()) continue;
            zipEntry = new ZipEntry(sb.toString() + tmp.getName());
            zipOutputStream.putNextEntry(zipEntry);
            Files.copy(tmp, zipOutputStream);
            zipOutputStream.closeEntry();
        }
        String html = "<a class=\"brand pull-left\" href=\"/\"> <img src=\"http://www.codejava.net/images/banner.png\" alt=\"CodeJava\"> </a>";
        for (int i = 1; i < 5; i++) {
            zipEntry = new ZipEntry("test" + i + ".html");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(html.getBytes(Charset.defaultCharset()));
            zipOutputStream.closeEntry();
        }
        //  bufferedOutputStream.close();
        zipOutputStream.close();
        result = bos.toByteArray();
        bos.close();
        return new ResponseEntity<byte[]>(result, headers, HttpStatus.CREATED);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Timestamp.class,
                new TimeStampPropertyEditor());
        request.getSession().setAttribute("userid", 1);
    }
}
