package com.example.demo.controller;

import com.example.repository.mapper.HrMapper;
import com.example.repository.model.HrExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hr")
public class HrGeneratedController {

    @Autowired
    HrMapper hrMapper;

    @GetMapping("countHr")
    public Long CountHr() {
        HrExample hrExample = new HrExample();
        hrExample.createCriteria();
        hrExample.setOrderByClause("");

        return hrMapper.countByExample(hrExample);
    }
}
