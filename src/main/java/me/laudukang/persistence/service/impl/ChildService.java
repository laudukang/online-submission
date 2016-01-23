package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.dao.common.IOperations;
import me.laudukang.persistence.model.Child;
import me.laudukang.persistence.dao.IChildDao;
import me.laudukang.persistence.service.IChildService;
import me.laudukang.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService extends AbstractService<Child>implements IChildService {

    @Autowired
    private IChildDao dao;

    public ChildService() {
        super();
    }

    // API

    @Override
    protected IOperations<Child> getDao() {
        return dao;
    }

}
