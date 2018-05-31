package com.test.fileutils;

import org.apache.catalina.Session;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by Artem on 30.05.2018.
 */
@Component
public class TestData {

    public String getFileData(String data){
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream("src/main/resources/data/testData.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appProps.getProperty(data);
    }

}
