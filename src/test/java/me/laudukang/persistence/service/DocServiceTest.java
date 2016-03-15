package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAuthor;
import me.laudukang.persistence.model.OsDoc;
import me.laudukang.persistence.util.PrintUtil;
import me.laudukang.spring.config.ApplicationConfig;
import me.laudukang.spring.config.AsyncConfig;
import me.laudukang.spring.config.PersistenceJPAConfig;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/7
 * <p>Time: 14:13
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, PersistenceJPAConfig.class, AsyncConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class DocServiceTest {
    @Autowired
    private IDocService docService;
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
        docService.save(osDoc);
    }

    @Test
    public void updateById() {
        OsDoc osDoc = new OsDoc();
        osDoc.setZhTitle("title_" + sdf.format(new Date()));
        osDoc.setEnTitle("Entitle_" + sdf.format(new Date()));
        osDoc.setId(7);

        OsAuthor osAuthor = new OsAuthor();
        osAuthor.setCity("gd gd");
        osAuthor.setId(3);
        osAuthor.setRemark("remark_" + sdf.format(new Date()));

        OsAuthor osAuthor2 = new OsAuthor();
        osAuthor2.setId(4);
        osAuthor2.setCity("gd gd");
        osAuthor2.setRemark("remark_" + sdf.format(new Date()));

        osDoc.addOsAuthor(osAuthor);
        osDoc.addOsAuthor(osAuthor2);
        docService.updateById(osDoc);

    }

    @Test
    public void deleteById() {
        docService.deleteById(9);
    }

    @Test
    public void findAllByPage() {
        Calendar fromTime = new GregorianCalendar(2016, 1, 1, 0, 0, 0);
        Calendar toTime = new GregorianCalendar(2016, 3, 11, 0, 0, 0);
        Page<OsDoc> tmp = docService.findAll("%title%", fromTime.getTime(), toTime.getTime(), pageable);
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
        docDomain.setZhTitle("%title_201%");
        docDomain.setAdminid(1);
        docDomain.setUserid(1);
        Page<OsDoc> tmp = docService.findAllByUserId(docDomain);
        System.out.println(tmp.getContent().size());
    }

    @Test
    public void findByAdminId() {
        DocDomain docDomain = new DocDomain();
        docDomain.setId(1);
        docDomain.setSortCol("id");
        docDomain.setSortDir("ASC");
        docDomain.setPage(1);
        docDomain.setPageSize(10);
        docDomain.setZhTitle("%title_2015%");
        docDomain.setAdminid(1);
        docDomain.setUserid(1);
        Page<OsDoc> tmp = docService.findByAdminId(docDomain);
        System.out.println(tmp.getContent().size());
    }
}
