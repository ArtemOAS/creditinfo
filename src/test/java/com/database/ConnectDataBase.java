package com.database;

import com.fileutils.RearFileProperty;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

/**
 * Created by Artem on 06.08.2017.
 */

public class ConnectDataBase {

    protected Connection connection;

    public ConnectDataBase() {
        RearFileProperty rearFileProperty = RearFileProperty.getInstance();
        this.connection = this.connect(
                rearFileProperty.driverDB,
                rearFileProperty.urlDB,
                rearFileProperty.userDB,
                rearFileProperty.passwordDB
        );
    }

    @Transactional
    protected Connection connect(String jdbcDriver, String jdbcUrl, String login, String password) {
        try {
           synchronized (Class.forName(jdbcDriver)) {
               return getConnection(jdbcUrl, login, password);
           }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
}
