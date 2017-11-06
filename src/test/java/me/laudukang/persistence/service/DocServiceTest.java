package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAuthor;
import me.laudukang.persistence.model.OsDoc;
import me.laudukang.persistence.util.PrintUtil;
import me.laudukang.spring.config.ApplicationConfig;
import me.laudukang.spring.domain.DocDomain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/7
 * <p>Time: 14:13
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class DocServiceTest {
    @Autowired
    private IDocService iDocService;
    private SimpleDateFormat sdf;
    private PrintUtil printUtil;
    private Pageable pageable;

    @Before
    public void initTest() {
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.printUtil = new PrintUtil();
        this.pageable = new PageRequest(0,
                10, new Sort(Sort.Direction.ASC, "id"));
    }

    @After
    public void endTest() {
        System.out.println("waiting async...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save() {
        OsDoc osDoc = new OsDoc();
        osDoc.setZhTitle("title_" + sdf.format(new Date()));

        OsAuthor osAuthor = new OsAuthor();
        osAuthor.setRemark("remark_" + sdf.format(new Date()));

        osDoc.addOsAuthor(osAuthor);
        iDocService.save(osDoc);
    }

    @Test
    public void updateById() {
        OsDoc osDoc = iDocService.findOne(12);
        List<OsAuthor> tmp = osDoc.getOsAuthors();
        for (OsAuthor t : tmp) {
            t.setOsDoc(null);
        }

        OsAuthor osAuthor = new OsAuthor();
        osAuthor.setCity("gd");
        osAuthor.setRemark("remark_" + sdf.format(new Date()));
        osAuthor.setOsDoc(osDoc);

        OsAuthor osAuthor2 = new OsAuthor();
        osAuthor2.setCity("gd");
        osAuthor2.setRemark("remark_" + sdf.format(new Date()));
        osAuthor2.setOsDoc(osDoc);

        List<OsAuthor> osAuthorList = new ArrayList<>();
        osAuthorList.add(osAuthor);
        osAuthorList.add(osAuthor2);
        osDoc.setOsAuthors(osAuthorList);

        iDocService.update(osDoc, tmp);

    }

    @Test
    public void deleteById() {
        iDocService.deleteById(9);
    }

    @Test
    public void findAllByPage() {
        Calendar fromTime = new GregorianCalendar(2016, 1, 1);
        Calendar toTime = new GregorianCalendar(2016, 3, 19);
        Page<OsDoc> tmp = iDocService.findAll("title", fromTime.getTime(), toTime.getTime(), pageable);
        System.out.println(tmp.getContent().size());
    }

    @Test
    public void findAllByUserId() {
        DocDomain docDomain = new DocDomain();
        docDomain.setId(1);
        docDomain.setSortCol("id");
        docDomain.setSortDir("ASC");
        docDomain.setPage(1);
        docDomain.setPageSize(10);
        docDomain.setZhTitle("12");
        docDomain.setAdminid(1);
        docDomain.setUserid(1);
//        docDomain.setFromTime("2016-03-01");
//        docDomain.setToTime("2016-03-24");
        Page<OsDoc> tmp = iDocService.findAllByUserId(docDomain);
        System.out.println(tmp.getContent().size());
        System.out.println(tmp.getContent().get(0).getZhTitle());
    }

    @Test
    public void findByAdminId() {
        DocDomain docDomain = new DocDomain();
        docDomain.setId(1);
        docDomain.setSortCol("id");
        docDomain.setSortDir("ASC");
        docDomain.setPage(1);
        docDomain.setPageSize(10);
        //docDomain.setZhTitle("title_2015");
        docDomain.setZhKeyword("11;22;33;44;55");
        docDomain.setAdminid(1);
        docDomain.setUserid(1);
        Page<OsDoc> tmp = iDocService.findByAdminId(docDomain);
        System.out.println(tmp.getContent().size());
    }

    @Test
    public void findDistributedDoc() {
        DocDomain docDomain = new DocDomain();
        docDomain.setId(1);
        docDomain.setSortCol("id");
        docDomain.setSortDir("ASC");
        docDomain.setPage(1);
        docDomain.setPageSize(10);
        docDomain.setAdminid(1);
        docDomain.setUserid(1);
        docDomain.setPageSize(10);
        docDomain.setName("杜");
        docDomain.setZhTitle("gg");
        Page<OsDoc> tmp = iDocService.findDistributedDoc(docDomain);
        System.out.println(tmp.getContent().size());
    }
}
