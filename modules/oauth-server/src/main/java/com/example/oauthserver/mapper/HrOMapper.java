package com.example.oauthserver.mapper;

import com.example.oauthserver.entity.Hr;
import com.example.oauthserver.entity.Role;
import com.example.oauthserver.security.HrDetails;
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
