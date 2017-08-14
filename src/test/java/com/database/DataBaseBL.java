package com.database;

import com.dao.CreditInfoDao;
import com.entity.Data;
import com.fileutils.RearFileProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

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
    public List<Data> findAll() {
        return entityManager.createNamedQuery("Data.getdAll", Data.class).getResultList();
    }

    @Override
    public ResponseEntity<?> creditInfoForm() {
        return null;
    }

    @Override
    public void addNewData(Data data) {
        entityManager.persist(data);
    }

    @Override
    public void updateDataCredit(Data data) {
        entityManager.merge(data);
    }

    @Override
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
    public void updateDataNewPercentSum(Data data) {

    }

    @Override
    public void updateDataDiffSum(Data data) {

    }
}
