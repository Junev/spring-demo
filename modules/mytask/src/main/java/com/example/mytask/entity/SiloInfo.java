package com.example.mytask.entity;

import java.util.Date;

public class SiloInfo {
    // 工单号
    private String taskId;
    // 批次号
    private String batchId;
    //    牌号ID
    private String brandId;
    //    牌号名称
    private String brandName;
    //    模块编码
    private String ModNo;
    //    模块名称
    private String ModNm;

// 生产路径编码
private String pathNo;
// 生产路径名称
    private String pathNm;
    // 控制配方版本号
    private String cr_version;
    // 批次计划重量
    private double  batchWeight;
    // 分类总数
    private double AmountOfCategories;

    // 计划开始时间
    private Date plantStartTime;

}
