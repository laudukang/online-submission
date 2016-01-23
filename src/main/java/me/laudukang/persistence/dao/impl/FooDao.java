package me.laudukang.persistence.dao.impl;

import me.laudukang.persistence.dao.common.AbstractHibernateDao;
import me.laudukang.persistence.dao.IFooDao;
import me.laudukang.persistence.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
public class FooDao extends AbstractHibernateDao<Foo> implements IFooDao {

    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
