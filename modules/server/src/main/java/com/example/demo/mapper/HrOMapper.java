package com.example.demo.mapper;

import com.example.demo.entity.Hr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HrOMapper {
    Hr getHrById(Integer id);

    List<Hr> listHrs();

    Integer addHr(Hr hr);

    Integer addHrSelective(Hr hr);

    Integer updateHrById(Hr hr);

    Integer deleteHrById(Integer id);
}
