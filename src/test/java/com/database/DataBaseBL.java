package com.database;

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
                    "jdbc:mysql://localhost:3306/creditinfo",
                    "root",
                    "admin"
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
}
