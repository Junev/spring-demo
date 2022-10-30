package com.example.demo.service;

import com.example.demo.entity.Hr;
import com.example.demo.mapper.HrOMapper;
import com.example.demo.security.HrDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "hr_cache")
public class HrService implements UserDetailsService {
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

    public Integer addHr(Hr hr) {
        encodePassword(hr);
        return hrMapper.addHr(hr);
    }

    public Integer addHrSelective(Hr hr) {
        encodePassword(hr);
        return hrMapper.addHrSelective(hr);
    }

    @CachePut(key = "#hr.id")
    public Integer updateHrById(Hr hr) {
        encodePassword(hr);
        return hrMapper.updateHrById(hr);
    }

    @CacheEvict(key = "#id")
    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteHrById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HrDetails hr = hrMapper.loadHrByUserName(username);
        if (hr == null) {
            throw new UsernameNotFoundException("账户不存在!");
        }
        hr.setRoles(hrMapper.getUserRolesByUid(hr.getId()));
        return hr;
    }

    private void encodePassword(Hr hr) {
        if (hr.getPassword() != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
            String encodePassword = bCryptPasswordEncoder.encode(hr.getPassword());
            hr.setPassword(encodePassword);
        }
    }
}
