package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.dao.IParentDao;
import me.laudukang.persistence.dao.common.IOperations;
import me.laudukang.persistence.service.IParentService;
import me.laudukang.persistence.model.Parent;
import me.laudukang.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService extends AbstractService<Parent>implements IParentService {

    @Autowired
    private IParentDao dao;

    public ParentService() {
        super();
    }

    // API

    @Override
    protected IOperations<Parent> getDao() {
        return dao;
    }

}
