package com.dao;

import com.entity.Data;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Artem on 15.07.2017.
 */
public interface CreditInfoDao {
    List findAll();
    ResponseEntity<?> creditInfoForm();
    void addNewData(Data data);
    void updateDataCredit(Data data);
    Data findDataCredit(Data data);
    Data findFullDataCredit(Data data);
    void updateDataNewPercentSum(Data data);
    void updateDataDiffSum(Data data);

}
