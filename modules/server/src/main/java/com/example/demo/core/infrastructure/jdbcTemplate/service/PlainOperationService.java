package com.example.demo.core.infrastructure.jdbcTemplate.service;

import com.example.demo.core.infrastructure.jdbcTemplate.QueryCondition;
import com.example.demo.core.infrastructure.jdbcTemplate.dao.PlainOperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlainOperationService {
    @Autowired
    PlainOperationDao plainOperationDao;

    public List<Map<String, Object>> query(String model,
                                           List<QueryCondition> cons, String orderBy) {
        return plainOperationDao.query(model, cons, orderBy);
    }

    public int addOne(String model, Map<String, Object> values) {
        return plainOperationDao.addOne(model, values);
    }

    public int update(String model, List<QueryCondition> cons, Map<String, Object> values) {
        return plainOperationDao.update(model, cons, values);
    }

    public int delete(String model, List<QueryCondition> cons) {
        return plainOperationDao.delete(model, cons);
    }
}
