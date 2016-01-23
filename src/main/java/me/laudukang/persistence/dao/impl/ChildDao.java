package me.laudukang.persistence.dao.impl;

import me.laudukang.persistence.dao.IChildDao;
import me.laudukang.persistence.dao.common.AbstractHibernateDao;
import me.laudukang.persistence.model.Child;
import org.springframework.stereotype.Repository;

@Repository
public class ChildDao extends AbstractHibernateDao<Child> implements IChildDao {

    public ChildDao() {
        super();

        setClazz(Child.class);
    }

    // API

}
