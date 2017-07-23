package com.service;

import com.dao.CreditInfoDao;
import com.dao.impl.CreditInfoDaoImpl;
import com.entity.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Artem on 23.07.2017.
 */
@Service("creditInfoService")
@Transactional
public class CreditInfoService implements CreditInfoDao{

    @Qualifier("creditInfoRepo")
    @Autowired
    private CreditInfoDaoImpl creditInfo;

    @Override
    public List<Data> findAll() {
        return creditInfo.findAll();
    }
}
