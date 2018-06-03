package com.test.utils.dbutils.impl;

import com.dao.CreditInfoDao;
import com.entity.Data;
import com.test.utils.dbutils.WriteToDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Artem on 03.06.2018.
 */
@Service
public class WriteToDBImpl implements WriteToDB {

    @Qualifier("bl")
    @Autowired
    private CreditInfoDao dataBaseBL;

    public void writeDataToDB(Data data) {
        List<Data> dataList = dataBaseBL.findAll();

        Optional<Data> dataRes = dataList.stream().filter(d ->
                d.getNameCompany().equals(data.getNameCompany()) &&
                        d.getSumCredit().equals(data.getSumCredit()) &&
                        d.getPeriodCredit().equals(data.getPeriodCredit())).findFirst();

        if (dataList.isEmpty() || !dataRes.isPresent()) {
            dataBaseBL.addNewData(data);
        } else {
            Data dataResult = dataList.stream().filter(d ->
                    !d.getOldPercentSum().equals(data.getOldPercentSum()) &&
                            d.getNameCompany().equals(data.getNameCompany()) &&
                            d.getSumCredit().equals(data.getSumCredit()) &&
                            d.getPeriodCredit().equals(data.getPeriodCredit())
            ).findFirst().orElse(null);
            if (dataResult != null) {
                dataBaseBL.updateDataCredit(new Data(d -> {
                    d.setDifferencePercentSum(differencePercentSum(dataResult.getOldPercentSum(), data.getOldPercentSum()));
                    d.setNameCompany(data.getNameCompany());
                    d.setSumCredit(data.getSumCredit());
                    d.setPeriodCredit(data.getPeriodCredit());
                    d.setNewPercentSum(data.getOldPercentSum());
                    d.setOldPercentSum(dataResult.getOldPercentSum());
                }));
            } else {
                dataBaseBL.updateDataNewPercentSum(
                        new Data(d -> {
                            d.setNewPercentSum(data.getOldPercentSum());
                            d.setNameCompany(data.getNameCompany());
                            d.setSumCredit(data.getSumCredit());
                            d.setPeriodCredit(data.getPeriodCredit());
                        })
                );
            }
        }
    }

    private String differencePercentSum(String oldPercentSumExpected, String oldPercentSumActual) {
        int differencePercentsum = 0;
        if (Integer.parseInt(oldPercentSumExpected)
                > Integer.parseInt(oldPercentSumActual)) {
            differencePercentsum =
                    Integer.parseInt(oldPercentSumExpected)
                            - Integer.parseInt(oldPercentSumActual);
        } else if (Integer.parseInt(oldPercentSumExpected)
                < Integer.parseInt(oldPercentSumActual)) {
            differencePercentsum =
                    Integer.parseInt(oldPercentSumActual)
                            - Integer.parseInt(oldPercentSumExpected);
        }
        return String.valueOf(differencePercentsum);
    }
}
