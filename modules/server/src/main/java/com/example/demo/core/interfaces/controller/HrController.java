package com.example.demo.core.interfaces.controller;

import com.example.demo.core.application.service.MyUserService;
import com.example.demo.core.domain.entity.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hr")
public class HrController {
    @Autowired
    MyUserService myUserService;

    @GetMapping("getHrById/{id}")
    public Hr getHrById(@PathVariable Integer id) {
        System.out.println(id);
        return myUserService.getHrById(id);
    }

    @GetMapping("listHrs")
    public List<Hr> listHrs() {
        System.out.println("listHrs");
        return myUserService.listHrs();
    }

    @PostMapping("addHr")
    public Integer addHr() {
        Hr hr = new Hr();
        hr.setName("foo");
        hr.setPhone("123123");
        hr.setPassword("123");
        return myUserService.addHr(hr);
    }

    @PostMapping("addHrSelective")
    public Integer addHrSelective() {
        Hr hr = new Hr();
        hr.setName("bar");
        hr.setPhone("321321");
        return myUserService.addHrSelective(hr);
    }

    @PostMapping("updateHrById")
    public Integer updateHrById() {
        Hr hr = myUserService.getHrById(3);
        hr.setPassword("123");
        return myUserService.updateHrById(hr);
    }

    @PostMapping("deleteHrById")
    public Integer deleteHrById() {
        return myUserService.deleteHrById(13);
    }
}
