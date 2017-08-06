package com.database;

import com.entity.Data;
import com.fileutils.RearFileProperty;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.*;

/**
 * Created by Artem on 19.07.2017.
 */
public class DataBaseBL extends ConnectDataBase{

    private RearFileProperty rearFileProperty = RearFileProperty.getInstance();

    public List<Data> response(String sqlRequest) {
        List<Data> dataList = new ArrayList<>();
        try (Connection connection = this.connect(
                rearFileProperty.driverDB,
                rearFileProperty.urlDB,
                rearFileProperty.userDB,
                rearFileProperty.passwordDB
        );
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

    public List<Data> responseCompanyData(String sqlRequest) {
        List<Data> dataList = new ArrayList<>();
        try (
                Connection connection = this.connect(
                        rearFileProperty.driverDB,
                        rearFileProperty.urlDB,
                        rearFileProperty.userDB,
                        rearFileProperty.passwordDB
                );
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
            rs.close();
            stmt.close();
            connection.close();
            return dataList;
        } catch (SQLException e) {
            e.printStackTrace();
            return dataList;
        }
    }

    public void update(String sqlRequest) {
        try (
                Connection connection = this.connect(
                        rearFileProperty.driverDB,
                        rearFileProperty.urlDB,
                        rearFileProperty.userDB,
                        rearFileProperty.passwordDB
                );
                Statement stmt = connection.createStatement();
        ) {

            stmt.executeUpdate(sqlRequest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
