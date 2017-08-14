package com.fileutils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Artem on 06.08.2017.
 */
public class RearFileProperty {
    private static final Logger LOGGER = Logger.getLogger(RearFileProperty.class);
    private static String applicationSpringPropertyURL = "src/main/resources/application.properties";

    private static volatile RearFileProperty rearFilePropertyInstance;

    private String getDataFile(String pathFile, String nameProperty){
        Properties property = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(pathFile)) {

            property.load(fileInputStream);
            return property.getProperty(nameProperty);

        } catch (IOException e) {
            LOGGER.info("ОШИБКА: Файл свойств отсуствует!");
            return null;
        }
    }


    public String driverDB;
    public String urlDB;
    public String userDB;
    public String passwordDB;

    private RearFileProperty (){
        driverDB = getDataFile(applicationSpringPropertyURL, "spring.datasource.driver-class-name");
        urlDB = getDataFile(applicationSpringPropertyURL, "spring.datasource.url");
        userDB = getDataFile(applicationSpringPropertyURL, "spring.datasource.username");
        passwordDB = getDataFile(applicationSpringPropertyURL, "spring.datasource.password");
    }


    public static RearFileProperty getInstance() {
        RearFileProperty localInstance = rearFilePropertyInstance;
        if (localInstance == null) {
            localInstance = rearFilePropertyInstance;
            if (localInstance == null) {
                rearFilePropertyInstance = localInstance = new RearFileProperty();
            }
        }
        return localInstance;
    }
}

