package com.example.mytask.config;

import com.example.repository.mapper.PdsEquippropertyMapper;
import com.example.repository.model.PdsEquipproperty;
import com.example.repository.model.PdsEquippropertyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Configuration
public class TimeByConditionConfig {
    @Autowired
    private PdsEquippropertyMapper mapper;

    @Bean
    public ReentrantLock runTimeLock() {
        return new ReentrantLock();
    }

    @Bean
    public List<PdsEquipproperty> timeEps() {
        PdsEquippropertyExample ex = new PdsEquippropertyExample();
        ex.createCriteria().andPropertyidLike("S%");
        return new Vector<>(mapper.selectByExample(ex));
    }

    @Bean
    public List<String> timeEpsIds(List<PdsEquipproperty> timeEps) {
        return timeEps.stream().map(PdsEquipproperty::getPropertyid).collect(
                Collectors.toList());
    }
}
