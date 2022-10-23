package com.example.demo.service;

import com.example.demo.entity.Hr;
import com.example.demo.mapper.HrOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "hr_cache")
public class HrService {
    @Autowired
    HrOMapper hrMapper;

    @Cacheable(key = "#id")
    public Hr getHrById(Integer id) {
        return hrMapper.getHrById(id);
    }

    public List<Hr> listHrs()
    {
        return hrMapper.listHrs();
    }

    public Integer addHr(Hr hr) { return hrMapper.addHr(hr); }

    public Integer addHrSelective(Hr hr) {
        return hrMapper.addHrSelective(hr);
    }

    @CachePut(key = "#hr.id")
    public Integer updateHrById(Hr hr) {
        return hrMapper.updateHrById(hr);
    }

    @CacheEvict(key = "#id")
    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteHrById(id);
    }
}
