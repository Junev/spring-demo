package com.example.demo.controller;

import com.example.demo.entity.Hr;
import com.example.demo.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hr")
public class HrController {
    @Autowired
    HrService hrService;

    @GetMapping("getHrById/{id}")
    public Hr getHrById(@PathVariable Integer id) {
        System.out.println(id);
        return hrService.getHrById(id);
    }

    @GetMapping("listHrs")
    public List<Hr> listHrs() {
        System.out.println("listHrs");
        return hrService.listHrs();
    }

    @PostMapping("addHr")
    public Integer addHr() {
        Hr hr = new Hr();
        hr.setName("foo");
        hr.setPhone("123123");
        return hrService.addHr(hr);
    }

    @PostMapping("addHrSelective")
    public Integer addHrSelective() {
        Hr hr = new Hr();
        hr.setName("bar");
        hr.setPhone("321321");
        return hrService.addHrSelective(hr);
    }

    @PostMapping("updateHrById")
    public Integer updateHrById() {
        Hr hr = new Hr();
        hr.setId(13);
        hr.setName("hello foo");
        return hrService.updateHrById(hr);
    }

    @PostMapping("deleteHrById")
    public Integer deleteHrById() {
        return hrService.deleteHrById(13);
    }
}
