package com.dao.impl;

import com.dao.CreditInfoDao;
import com.entity.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Artem on 15.07.2017.
 */
@Repository("creditInfoRepo")
public class CreditInfoDaoImpl implements CreditInfoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Data> findAll() {
        return entityManager.createNamedQuery("Data.getdAll", Data.class).getResultList();
    }

    @Override
    public ResponseEntity<?> creditInfoForm() {
        return null;
    }

    @Override
    public void addNewData(Data data) {

    }

    @Override
    public void updateDataCredit(Data data) {

    }

    @Override
    public Data findDataCredit(Data data) {
        return null;
    }

    @Override
    public Data findFullDataCredit(Data data) {
        return null;
    }

    @Override
    public void updateDataNewPercentSum(Data data) {

    }

    @Override
    public void updateDataDiffSum(Data data) {

    }
}
