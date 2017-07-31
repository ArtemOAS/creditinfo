package com.database;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.testng.Assert;

import java.sql.*;

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

    public StringBuilder response(String sqlRequest){
        StringBuilder stringBuilder = null;
        try {
            stmt = connect(
                    "com.mysql.jdbc.Driver",
                    "jjdbc:mysql://vinnik.beget.tech:3306/vinnik_credit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "vinnik_credit",
                    "vinnik_credit"
                    ).createStatement();
            rs = stmt.executeQuery(sqlRequest);
            int count = 0;
            while (rs.next()) {
                stringBuilder
                        .append(
                                rs.getString(
                                        rs.getMetaData().getColumnName(count++))
                        );
            }
            rs.close();
            stmt.close();
            connection.close();
            return stringBuilder;
        } catch (SQLException e) {
            e.printStackTrace();
            return stringBuilder;
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

    public StringBuilder update(String sqlRequest){
        StringBuilder stringBuilder = null;
        try {
            stmt = connect(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://vinnik.beget.tech:3306/vinnik_credit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "vinnik_credit",
                    "vinnik_credit"
            ).createStatement();
            int count = stmt.executeUpdate(sqlRequest);
            Assert.assertEquals(1, count, "update db error");
            stmt.close();
            connection.close();
            return stringBuilder;
        } catch (SQLException e) {
            e.printStackTrace();
            return stringBuilder;
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
