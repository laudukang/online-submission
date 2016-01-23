package me.laudukang.persistence.dao.impl;

import me.laudukang.persistence.dao.IParentDao;
import me.laudukang.persistence.dao.common.AbstractHibernateDao;
import me.laudukang.persistence.model.Parent;
import org.springframework.stereotype.Repository;

@Repository
public class ParentDao extends AbstractHibernateDao<Parent> implements IParentDao {

    public ParentDao() {
        super();

        setClazz(Parent.class);
    }

    // API

}
