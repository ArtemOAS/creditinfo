package com.test.fileutils;

/**
 * Created by Artem on 30.05.2018.
 */
public enum FileData {
    MOMEYMAN_URL("moneymanUrl");

    private String value;

    FileData(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
