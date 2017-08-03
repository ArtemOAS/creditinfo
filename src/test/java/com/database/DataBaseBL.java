package com.database;

import com.entity.Data;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.testng.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem on 19.07.2017.
 */
public class DataBaseBL {

    private Connection connection = null;
    private Statement stmt;
    private ResultSet rs;

    public Connection connect(String jdbcDriver, String jdbcUrl, String login, String password){
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager
                    .getConnection(jdbcUrl, login, password);
            return connection;
        } catch (SQLException | ClassNotFoundException  e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return connection;
        }
    }

    public List<Data> response(String sqlRequest){
        List<Data> dataList = new ArrayList<>();
        try {
            stmt = connect(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://vinnik.beget.tech:3306/vinnik_credit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "vinnik_credit",
                    "vinnik_credit"
            ).createStatement();
            rs = stmt.executeQuery(sqlRequest);
            int count = 0;
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
            rs.close();
            stmt.close();
            connection.close();
            return dataList;
        } catch (SQLException e) {
            e.printStackTrace();
            return dataList;
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Data> responseCompanyData(String sqlRequest){
        List<Data> dataList = new ArrayList<>();
        try {
            stmt = connect(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://vinnik.beget.tech:3306/vinnik_credit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "vinnik_credit",
                    "vinnik_credit"
            ).createStatement();
            rs = stmt.executeQuery(sqlRequest);
            int count = 0;
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
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(String sqlRequest){
        try {
            stmt = connect(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://vinnik.beget.tech:3306/vinnik_credit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "vinnik_credit",
                    "vinnik_credit"
            ).createStatement();
            stmt.executeUpdate(sqlRequest);
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String decode(String data) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(data));
    }
}
