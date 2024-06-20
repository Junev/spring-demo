package com.example.mytask.entity;

import java.util.Date;

public class EmsRunTimeStats {
    private String EQUIPELEMENTID;
    private String EQUIPPROPERTYID;
    private String EMSPROPERTYID;
    private Date RECORDDATE;
    private Long PROPERTYVALUE;
    private Long DELTAVALUE;
    private Date RECORDTIME;
    private Long STOPTIME;
    private Long NOFAULTTIME;

    public EmsRunTimeStats() {
    }

    public String getEQUIPELEMENTID() {
        return EQUIPELEMENTID;
    }

    public void setEQUIPELEMENTID(String EQUIPELEMENTID) {
        this.EQUIPELEMENTID = EQUIPELEMENTID;
    }

    public String getEQUIPPROPERTYID() {
        return EQUIPPROPERTYID;
    }

    public void setEQUIPPROPERTYID(String EQUIPPROPERTYID) {
        this.EQUIPPROPERTYID = EQUIPPROPERTYID;
    }

    public Date getRECORDDATE() {
        return RECORDDATE;
    }

    public void setRECORDDATE(Date RECORDDATE) {
        this.RECORDDATE = RECORDDATE;
    }


    public Date getRECORDTIME() {
        return RECORDTIME;
    }

    public void setRECORDTIME(Date RECORDTIME) {
        this.RECORDTIME = RECORDTIME;
    }

    public Long getPROPERTYVALUE() {
        return PROPERTYVALUE;
    }

    public void setPROPERTYVALUE(Long PROPERTYVALUE) {
        this.PROPERTYVALUE = PROPERTYVALUE;
    }

    public Long getDELTAVALUE() {
        return DELTAVALUE;
    }

    public void setDELTAVALUE(Long DELTAVALUE) {
        this.DELTAVALUE = DELTAVALUE;
    }

    public String getEMSPROPERTYID() {
        return EMSPROPERTYID;
    }

    public void setEMSPROPERTYID(String EMSPROPERTYID) {
        this.EMSPROPERTYID = EMSPROPERTYID;
    }

    public Long getSTOPTIME() {
        return STOPTIME;
    }

    public void setSTOPTIME(Long STOPTIME) {
        this.STOPTIME = STOPTIME;
    }

    public Long getNOFAULTTIME() {
        return NOFAULTTIME;
    }

    public void setNOFAULTTIME(Long NOFAULTTIME) {
        this.NOFAULTTIME = NOFAULTTIME;
    }
}
