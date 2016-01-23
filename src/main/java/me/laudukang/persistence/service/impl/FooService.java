package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.dao.IFooDao;
import me.laudukang.persistence.dao.common.IOperations;
import me.laudukang.persistence.service.IFooService;
import me.laudukang.persistence.service.common.AbstractService;
import me.laudukang.persistence.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooService extends AbstractService<Foo> implements IFooService {

    @Autowired
    private IFooDao dao;

    public FooService() {
        super();
    }

    // API

    @Override
    protected IOperations<Foo> getDao() {
        return dao;
    }

}
