package com.database;

import com.entity.Data;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem on 19.07.2017.
 */

public class DataBaseBL extends ConnectDataBase{

    @Transactional
    public List<Data> response(String sqlRequest) {
        List<Data> dataList = new ArrayList<>();
        try (Connection connection = this.connection;
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlRequest);
        ) {

            while (rs.next()) {
                dataList.add(new Data(d -> {
                    try {
                        d.setNameCompany(rs.getString("name_company"));
                        d.setSumCredit(rs.getString("sum_credit"));
                        d.setPeriodCredit(rs.getString("period_credit"));
                        d.setOldPercentSum(rs.getString("old_percent_sum"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }));
            }
            return dataList;
        } catch (SQLException e) {
            e.printStackTrace();
            return dataList;
        }
    }

    @Transactional
    public List<Data> responseCompanyData(String sqlRequest) {
        List<Data> dataList = new ArrayList<>();
        try (
                Connection connection = this.connection;
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlRequest);
        ) {
            while (rs.next()) {
                dataList.add(new Data(d -> {
                    try {
                        d.setNameCompany(rs.getString("name_company"));
                        d.setSumCredit(rs.getString("sum_credit"));
                        d.setPeriodCredit(rs.getString("period_credit"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }));
            }
            return dataList;
        } catch (SQLException e) {
            e.printStackTrace();
            return dataList;
        }
    }

    @Transactional
    public void update(String sqlRequest) {
        try (
                Connection connection = this.connection;
                Statement stmt = connection.createStatement();
        ) {

            stmt.executeUpdate(sqlRequest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
