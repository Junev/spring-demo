package com.example.demo.mapper;

import com.example.demo.entity.Hr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HrMapper {
    Hr getHrById(Integer id);

    List<Hr> listHrs();

    Integer addHr(Hr hr);

    Integer addHrSelective(Hr hr);
//
//    int updateHrById(Hr hr);
//
//    int deleteHrById(Integer id);
}
