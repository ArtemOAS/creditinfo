package com.test.utils.fileutils.impl;

import com.test.utils.fileutils.FilesTestData;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Artem on 30.05.2018.
 */
@Service
public class FilesTestDataImpl implements FilesTestData {

    @Override
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
