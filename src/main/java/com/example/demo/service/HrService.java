package com.example.demo.service;

import com.example.demo.entity.Hr;
import com.example.demo.mapper.HrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HrService {
    @Autowired
    HrMapper hrMapper;

    public Hr getHrById(Integer id) {
        return hrMapper.getHrById(id);
    }

    public List<Hr> listHrs() {
        return hrMapper.listHrs();
    }

    public Integer addHr(Hr hr) {
        return hrMapper.addHr(hr);
    }

    public Integer addHrSelective(Hr hr) {
        return hrMapper.addHrSelective(hr);
    }
}
