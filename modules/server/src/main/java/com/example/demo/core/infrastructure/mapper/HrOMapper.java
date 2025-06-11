package com.example.demo.core.infrastructure.mapper;

import com.example.demo.core.domain.entity.Hr;
import com.example.demo.core.domain.entity.Role;
import com.example.demo.core.interfaces.security.HrDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HrOMapper {
    Hr getHrById(Integer id);

    HrDetails loadHrByUserName(String name);

    List<Role> getUserRolesByUid(Integer id);

    List<Hr> listHrs();

    Integer addHr(Hr hr);

    Integer addHrSelective(Hr hr);

    Integer updateHrById(Hr hr);

    Integer deleteHrById(Integer id);
}
