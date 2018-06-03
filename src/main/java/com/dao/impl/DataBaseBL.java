package com.dao.impl;

import com.dao.CreditInfoDao;
import com.entity.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Artem on 19.07.2017.
 */

@Repository("bl")
public class DataBaseBL implements CreditInfoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Data> findAll() {
        List<Data> list = entityManager.createNamedQuery("Data.getdAll", Data.class).getResultList();
        return list.stream().map(arrayResult -> new Data(d -> {
            d.setNameCompany(arrayResult.getNameCompany());
            d.setSumCredit(arrayResult.getSumCredit());
            d.setPeriodCredit(arrayResult.getPeriodCredit());
            d.setOldPercentSum(arrayResult.getOldPercentSum());
            d.setNewPercentSum(arrayResult.getNewPercentSum());
            d.setDifferencePercentSum(arrayResult.getDifferencePercentSum());
        })).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public ResponseEntity<?> creditInfoForm() {
        return null;
    }

    @Override
    @Transactional
    public void addNewData(Data data) {
        entityManager.persist(data);
    }

    @Override
    @Transactional
    public void updateDataCredit(Data data) {
        entityManager.createQuery(
                "UPDATE Data d " +
                        "SET d.newPercentSum =?1, d.oldPercentSum = ?5, d.differencePercentSum = ?6 where d.nameCompany = ?2 and d.sumCredit = ?3 and d.periodCredit = ?4")
                .setParameter(1, data.getNewPercentSum())
                .setParameter(2, data.getNameCompany())
                .setParameter(3, data.getSumCredit())
                .setParameter(4, data.getPeriodCredit())
                .setParameter(5, data.getOldPercentSum())
                .setParameter(6, data.getDifferencePercentSum())
                .executeUpdate();
    }

    @Override
    @Transactional
    public Data findDataCredit(Data data) {
        Data dataResult = entityManager.createQuery(
                "SELECT c FROM Data c WHERE " +
                        "c.nameCompany = ?1 and " +
                        "c.sumCredit = ?2 and " +
                        "c.periodCredit = ?3 and " +
                        "c.oldPercentSum = ?4", Data.class)
                .setParameter(1, data.getNameCompany())
                .setParameter(2, data.getSumCredit())
                .setParameter(3, data.getPeriodCredit())
                .setParameter(4, data.getOldPercentSum())
                .getSingleResult();
        return new Data(d -> {
           d.setNameCompany(dataResult.getNameCompany());
           d.setSumCredit(dataResult.getSumCredit());
           d.setPeriodCredit(dataResult.getPeriodCredit());
           d.setOldPercentSum(dataResult.getOldPercentSum());
        });
    }

    @Override
    @Transactional
    public Data findFullDataCredit(Data data) {
        return (Data) entityManager.createQuery(
                "SELECT c FROM Data c WHERE " +
                        "c.nameCompany = ?1 and " +
                        "c.sumCredit = ?2 and " +
                        "c.periodCredit = ?3 and " +
                        "c.oldPercentSum = ?4")
                .setParameter(1, data.getNameCompany())
                .setParameter(2, data.getSumCredit())
                .setParameter(3, data.getPeriodCredit())
                .setParameter(4, data.getOldPercentSum())
                .getSingleResult();
    }

    @Override
    @Transactional
    public void updateDataNewPercentSum(Data data) {
        entityManager.createQuery(
                "UPDATE Data d " +
                        "SET d.newPercentSum =?1 where d.nameCompany = ?2 and d.sumCredit = ?3 and d.periodCredit = ?4")
                .setParameter(1, data.getNewPercentSum())
                .setParameter(2, data.getNameCompany())
                .setParameter(3, data.getSumCredit())
                .setParameter(4, data.getPeriodCredit()).executeUpdate();
    }

    @Override
    @Transactional
    public void updateDataDiffSum(Data data) {
        entityManager.createQuery(
                "UPDATE Data d " +
                        "SET d.differencePercentSum = ?1 where d.nameCompany = ?2 and d.sumCredit = ?3 and d.periodCredit = ?4")
                .setParameter(1, data.getDifferencePercentSum())
                .setParameter(2, data.getNameCompany())
                .setParameter(3, data.getSumCredit())
                .setParameter(4, data.getPeriodCredit()).executeUpdate();
    }
}
