package com.example.demo.jdbcTemplate.controller;

import com.example.demo.controller.entity.MHttpRequest;
import com.example.demo.controller.entity.MHttpResult;
import com.example.demo.jdbcTemplate.QueryCondition;
import com.example.demo.jdbcTemplate.service.PlainOperationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EMSController {
    @Autowired
    PlainOperationService plainOperationService;


    @PostMapping("/mdb")
    public MHttpResult request(@RequestBody MHttpRequest req) {
        Object result;
        String operation = req.getT();
        String tableName = req.getD().get("model").asText();

        // get cons
        ObjectMapper om = new ObjectMapper();
        JsonNode consJ = req.getD().get("cons");
        List<QueryCondition> cons = null;
        if (consJ != null) {
            cons = om.convertValue(consJ, new TypeReference<List<QueryCondition>>() {
            });
        }

        //get values
        ObjectMapper om1 = new ObjectMapper();
        JsonNode valuesJ = req.getD().get("values");
        Map<String, Object> values = null;
        if (valuesJ != null) {
            values = om.convertValue(valuesJ,
                    new TypeReference<Map<String, Object>>() {
                    });
        }

        if (operation.equals("mdb\\get")) {
            JsonNode orderByJ = req.getD().get("orderby");
            String orderBy = null;
            if (orderByJ != null) {
                orderBy = orderByJ.asText();
            }

            result = plainOperationService.query(tableName, cons,
                    orderBy);
            return MHttpResult.success(result);
        } else if (operation.equals("mdb\\add")) {
            result = plainOperationService.addOne(tableName, values);
            return MHttpResult.success(result);
        } else if (operation.equals("mdb\\save")) {
            result = plainOperationService.update(tableName, cons, values);
            return MHttpResult.success(result);
        } else if (operation.equals("mdb\\del")) {
            result = plainOperationService.delete(tableName, cons);
            return MHttpResult.success(result);
        }

        return MHttpResult.success(null);
    }
}
