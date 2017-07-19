package com.dao;

import com.entity.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Artem on 15.07.2017.
 */
public interface CreditInfoDao extends Repository<Data, Long> {
    List<Data> findAll();
}
