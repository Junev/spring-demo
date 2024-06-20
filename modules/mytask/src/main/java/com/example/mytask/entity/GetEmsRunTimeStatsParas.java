package com.example.mytask.entity;

import java.util.Date;

public class GetEmsRunTimeStatsParas {
    private String emsPropertyId;
    private Date recordDate;

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getEmsPropertyId() {
        return emsPropertyId;
    }

    public void setEmsPropertyId(String emsPropertyId) {
        this.emsPropertyId = emsPropertyId;
    }
}
