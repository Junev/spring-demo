package com.example.mytask.config;

import com.example.repository.mapper.PdsEquipelementMapper;
import com.example.repository.model.PdsEquipelement;
import com.example.repository.model.PdsEquipelementExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SiloConfig {
    @Autowired
    PdsEquipelementMapper equipMapper;

    @Bean(name = "siloIds")
    public List<String> siloIds() {
        PdsEquipelementExample ex = new PdsEquipelementExample();
        ex.createCriteria().andEeLevelEqualTo(5L).andEquipmentnameLike("%柜%");
        List<PdsEquipelement> pdsEquipelements = equipMapper.selectByExample(ex);
        List<String> siloIds = pdsEquipelements.stream()
                .map(PdsEquipelement::getEquipmentid)
                .collect(Collectors.toList());
        return siloIds;
    }
}
