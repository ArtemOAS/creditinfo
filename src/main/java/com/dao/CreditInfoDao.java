package com.dao;

import com.entity.Data;
import org.springframework.data.repository.Repository;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Artem on 15.07.2017.
 */
public interface CreditInfoDao extends Repository<Data, Long> {
    List<Data> findAll();
    ResponseEntity<?> creditInfoForm();
    void addNewData(Data data);
    void updateDataCredit(Data data);
    List<Data> findDataCredit(Data data);
    void updateDataNewPercentSum(Data data);
    void updateDataDiffSum(Data data);

}
