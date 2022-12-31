package com.example.mqtt.mapper;

import com.example.mqtt.entity.SiloMessageRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SiloMessageMapper {
    Integer addSiloMessage(SiloMessageRecord s);
}
