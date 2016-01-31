package me.laudukang.persistence.hibernate;

import me.laudukang.persistence.model.OsLog;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/31
 * Time: 16:28
 * Version: 1.0
 */
public class DatabaseInit {
    private EntityManager entityManager;

    public DatabaseInit(final EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public void createLog() {
        System.out.println("entityManager.isOpen()=" + entityManager.isOpen());
        System.out.println("entityManager.getFlushMode()=" + entityManager.getFlushMode());
        for (int i = 1; i < 5; i++) {
            final OsLog osLog = new OsLog();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            osLog.setContent("content_" + sdf.format(new Date()));
            entityManager.merge(osLog);
        }
        entityManager.flush();
        System.out.println(entityManager.getProperties());
    }

}
