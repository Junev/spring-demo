package com.example.mytask.mapper;

import com.example.mytask.entity.SiloMessageRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SiloMessageMapper {
    Integer addSiloMessage(SiloMessageRecord s);
}
