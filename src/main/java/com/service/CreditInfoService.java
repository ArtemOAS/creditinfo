package com.service;

import com.dao.CreditInfoDao;
import com.dao.impl.CreditInfoDaoImpl;
import com.entity.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return null;
    }

    @Override
    public ResponseEntity<?> creditInfoForm() {
        List<Data> dataList = creditInfo.findAll();

        if(dataList!=null){
            return new ResponseEntity<>(dataList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(dataList, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void addNewData(Data data) {

    }

    @Override
    public void updateDataCredit(Data data) {

    }

    @Override
    public List<Data> findDataCredit(Data data) {
        return null;
    }

    @Override
    public void updateDataNewPercentSum(Data data) {

    }

    @Override
    public void updateDataDiffSum(Data data) {

    }
}
