package me.laudukang.persistence.hibernate;

import me.laudukang.spring.config.PersistenceJPAConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/31
 * <p>Time: 16:35
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceJPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class DatabaseInitTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void before() {
        System.out.println("in before");
        entityManager.setFlushMode(FlushModeType.COMMIT);
    }

    @After
    public void after() {
        System.out.println("DatabaseInitTest done");
    }

    @Test
    @Rollback(false)
    public void createLog() {
        System.out.println("in dataTest");
        final DatabaseInit databaseInit = new DatabaseInit(entityManager);
        databaseInit.createLog();
    }
}
