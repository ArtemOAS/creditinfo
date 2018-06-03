package com.test.utils.dbutils;

import com.entity.Data;
import org.springframework.stereotype.Service;

/**
 * Created by Artem on 03.06.2018.
 */
@Service
public interface WriteToDB {
    void writeDataToDB(Data data);
}
