package com.example.demo.core.infrastructure.jdbcTemplate.dao;

import com.example.demo.core.infrastructure.jdbcTemplate.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PlainOperationDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> query(String model, List<QueryCondition> cons,
                                           String orderBy) {
        String whereClause = convertToWhereClause(cons);
        if (orderBy == null) {
            return jdbcTemplate.queryForList("SELECT * FROM " + model + whereClause);
        }
        return jdbcTemplate.queryForList("SELECT * FROM " + model + whereClause + " ORDER BY " + orderBy);
    }

    public int addOne(String model, Map<String, Object> values) {
        List<String> names = new ArrayList<>();
        List<String> toSaveValues = new ArrayList<>();
        values.forEach((key, value) -> {
            names.add(key);
            if (value == null) {
                toSaveValues.add(null);
            } else if (value == "") {
                toSaveValues.add("\"\"");
            } else {
                toSaveValues.add("\"" + value.toString().replace("datetime(", "")
                        .replace(")", "") + "\"");
            }
        });

        return jdbcTemplate.update(
                "INSERT INTO " + model + " " +
                        "(" + String.join(",", names) + ") " +
                        "VALUES " + "" +
                        "(" + String.join(",", toSaveValues) + ")");
    }

    public int update(String model, List<QueryCondition> cons, Map<String, Object> values) {
        String whereClause = convertToWhereClause(cons);
        String setClause = values.keySet().stream()
                .map(c -> {
                    Object value = values.get(c);
                    if (value != null) {
                        value = values.get(c)
                                .toString()
                                .replace("datetime(", "")
                                .replace(")", "");
                    }
                    return c + "=\"" + value +
                            "\"";
                })
                .collect(Collectors.joining(", ", " SET ", ""));
        return jdbcTemplate.update("UPDATE " + model + setClause + " " + whereClause);
    }

    public int delete(String model, List<QueryCondition> cons) {
        String whereClause = convertToWhereClause(cons);
        return jdbcTemplate.update("DELETE FROM " + model + " " + whereClause);
    }

    private String convertToWhereClause(List<QueryCondition> cons) {
        String whereCluse = "";
        if (cons != null && !cons.isEmpty()) {
            List<String> conStrs = cons.stream()
                    .map(c -> {
                        if (c.getCp().equals("is") || c.getCp().equals("is not")) {
                            return c.getCn() + " " + c.getCp() + " " + c.getV1();
                        }
                        return c.getCn() + " " + c.getCp() + " \"" + c.getV1() + "\"";
                    })
                    .collect(Collectors.toList());
            whereCluse = " WHERE " + String.join(" AND ", conStrs);
        }
        return whereCluse;
    }
}
