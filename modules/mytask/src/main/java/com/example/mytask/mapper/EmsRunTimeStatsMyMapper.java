package com.example.mytask.mapper;

import com.example.mytask.entity.EmsRunTimeStats;
import com.example.mytask.entity.EquipTimeLong;
import com.example.mytask.entity.GetEmsRunTimeStatsParas;
import com.example.mytask.entity.GetTimeLongParas;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmsRunTimeStatsMyMapper {
    List<EquipTimeLong> getTimeLong(GetTimeLongParas p);

    List<EmsRunTimeStats> getYesterdayCount(GetEmsRunTimeStatsParas p);

    Integer addEmsRunTimeStats(EmsRunTimeStats e);
}
