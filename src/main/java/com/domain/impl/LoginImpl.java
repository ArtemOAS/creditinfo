package com.domain.impl;

import com.domain.Login;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Artem on 31.07.2017.
 */

public class LoginImpl implements Login {
    @Override
    public void  setPropertyFile() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("src/main/resources/application.properties");

            // set the properties value
            prop.setProperty("spring.datasource.username", decode("dmlubmlrX2NyZWRpdA=="));
            prop.setProperty("spring.datasource.password", decode("dmlubmlrX2NyZWRpdA=="));

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String decode(String data) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(data));
    }
}
