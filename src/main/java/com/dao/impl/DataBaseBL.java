package com.dao.impl;

import com.dao.CreditInfoDao;
import com.entity.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


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
        return entityManager.createNamedQuery("Data.getdAll", Data.class).getResultList();
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
        entityManager.merge(data);
    }

    @Override
    @Transactional
    public List<Data> findDataCredit(Data data) {
        return entityManager.createQuery(
                "SELECT c FROM Data c WHERE " +
                        "c.nameCompany = ?1 and " +
                        "c.sumCredit = ?2 and " +
                        "c.periodCredit = ?3")
                .setParameter(1,data.getNameCompany())
                .setParameter(2,data.getSumCredit())
                .setParameter(3,data.getPeriodCredit())
                .getResultList();
    }

    @Override
    @Transactional
    public void updateDataNewPercentSum(Data data) {
        entityManager.createQuery(
                "UPDATE vinnik_credit.creditinfo " +
                        "SET new_percent_sum =?1 where name_company = ?2 and sum_credit = ?3 and period_credit = ?4;")
                .setParameter(1,data.getNewPercentSum())
                .setParameter(2,data.getNameCompany())
                .setParameter(3,data.getSumCredit())
                .setParameter(4,data.getPeriodCredit());
    }

    @Override
    @Transactional
    public void updateDataDiffSum(Data data) {
        entityManager.createQuery(
                "UPDATE vinnik_credit.creditinfo " +
                        "SET difference_percent_sum = ?1 where name_company = ?2 and sum_credit = ?3 and period_credit = ?4;")
                .setParameter(1,data.getDifferencePercentSum())
                .setParameter(2,data.getNameCompany())
                .setParameter(3,data.getSumCredit())
                .setParameter(4,data.getPeriodCredit());
    }
}
