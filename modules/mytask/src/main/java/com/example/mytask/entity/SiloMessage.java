package com.example.mytask.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiloMessage {
    /**
     * SILOID : 102304
     * TASKID : 221229030102
     * SILOTASKID :
     * INOROUTBATCHID :
     * INOROUTMATID :
     * INOROUTAMOUNT : 0.00
     * REMAINAMOUNT : 0.00
     * SILOSTORETIMELONG : 0.00
     * ISY : 0
     * ISM : 0
     * ISD : 0
     * ISH : 0
     * ISN : 0
     * ISS : 0
     * IEY : 0
     * IEM : 0
     * IED : 0
     * IEH : 0
     * IEN : 0
     * IES : 0
     * OSY : 2022.00
     * OSM : 12.00
     * OSD : 17.00
     * OSH : 13.00
     * OSN : 4.00
     * OSS : 28.00
     * OEY : 2022.00
     * OEM : 12.00
     * OED : 17.00
     * OEH : 14.00
     * OEN : 32.00
     * OES : 50.00
     * SCALEWEIGHT : 5651.20
     * SCALEPARAID : u10202pt0081
     * ISUSETIMENOW : 1
     */

    @JsonProperty("SILOID")
    private String SILOID;
    @JsonProperty("TASKID")
    private String TASKID;
    @JsonProperty("SILOTASKID")
    private String SILOTASKID;
    @JsonProperty("INOROUTBATCHID")
    private String INOROUTBATCHID;
    @JsonProperty("INOROUTMATID")
    private String INOROUTMATID;
    @JsonProperty("INOROUTAMOUNT")
    private String INOROUTAMOUNT;
    @JsonProperty("REMAINAMOUNT")
    private String REMAINAMOUNT;
    @JsonProperty("SILOSTORETIMELONG")
    private String SILOSTORETIMELONG;
    @JsonProperty("ISY")
    private String ISY;
    @JsonProperty("ISM")
    private String ISM;
    @JsonProperty("ISD")
    private String ISD;
    @JsonProperty("ISH")
    private String ISH;
    @JsonProperty("ISN")
    private String ISN;
    @JsonProperty("ISS")
    private String ISS;
    @JsonProperty("IEY")
    private String IEY;
    @JsonProperty("IEM")
    private String IEM;
    @JsonProperty("IED")
    private String IED;
    @JsonProperty("IEH")
    private String IEH;
    @JsonProperty("IEN")
    private String IEN;
    @JsonProperty("IES")
    private String IES;
    @JsonProperty("OSY")
    private String OSY;
    @JsonProperty("OSM")
    private String OSM;
    @JsonProperty("OSD")
    private String OSD;
    @JsonProperty("OSH")
    private String OSH;
    @JsonProperty("OSN")
    private String OSN;
    @JsonProperty("OSS")
    private String OSS;
    @JsonProperty("OEY")
    private String OEY;
    @JsonProperty("OEM")
    private String OEM;
    @JsonProperty("OED")
    private String OED;
    @JsonProperty("OEH")
    private String OEH;
    @JsonProperty("OEN")
    private String OEN;
    @JsonProperty("OES")
    private String OES;
    @JsonProperty("SCALEWEIGHT")
    private String SCALEWEIGHT;
    @JsonProperty("SCALEPARAID")
    private String SCALEPARAID;
    @JsonProperty("ISUSETIMENOW")
    private String ISUSETIMENOW;

    public String getSILOID() {
        return SILOID;
    }

    public void setSILOID(String SILOID) {
        this.SILOID = SILOID;
    }

    public String getTASKID() {
        return TASKID;
    }

    public void setTASKID(String TASKID) {
        this.TASKID = TASKID;
    }

    public String getSILOTASKID() {
        return SILOTASKID;
    }

    public void setSILOTASKID(String SILOTASKID) {
        this.SILOTASKID = SILOTASKID;
    }

    public String getINOROUTBATCHID() {
        return INOROUTBATCHID;
    }

    public void setINOROUTBATCHID(String INOROUTBATCHID) {
        this.INOROUTBATCHID = INOROUTBATCHID;
    }

    public String getINOROUTMATID() {
        return INOROUTMATID;
    }

    public void setINOROUTMATID(String INOROUTMATID) {
        this.INOROUTMATID = INOROUTMATID;
    }

    public String getINOROUTAMOUNT() {
        return INOROUTAMOUNT;
    }

    public void setINOROUTAMOUNT(String INOROUTAMOUNT) {
        this.INOROUTAMOUNT = INOROUTAMOUNT;
    }

    public String getREMAINAMOUNT() {
        return REMAINAMOUNT;
    }

    public void setREMAINAMOUNT(String REMAINAMOUNT) {
        this.REMAINAMOUNT = REMAINAMOUNT;
    }

    public String getSILOSTORETIMELONG() {
        return SILOSTORETIMELONG;
    }

    public void setSILOSTORETIMELONG(String SILOSTORETIMELONG) {
        this.SILOSTORETIMELONG = SILOSTORETIMELONG;
    }

    public String getISY() {
        return ISY;
    }

    public void setISY(String ISY) {
        this.ISY = ISY;
    }

    public String getISM() {
        return ISM;
    }

    public void setISM(String ISM) {
        this.ISM = ISM;
    }

    public String getISD() {
        return ISD;
    }

    public void setISD(String ISD) {
        this.ISD = ISD;
    }

    public String getISH() {
        return ISH;
    }

    public void setISH(String ISH) {
        this.ISH = ISH;
    }

    public String getISN() {
        return ISN;
    }

    public void setISN(String ISN) {
        this.ISN = ISN;
    }

    public String getISS() {
        return ISS;
    }

    public void setISS(String ISS) {
        this.ISS = ISS;
    }

    public String getIEY() {
        return IEY;
    }

    public void setIEY(String IEY) {
        this.IEY = IEY;
    }

    public String getIEM() {
        return IEM;
    }

    public void setIEM(String IEM) {
        this.IEM = IEM;
    }

    public String getIED() {
        return IED;
    }

    public void setIED(String IED) {
        this.IED = IED;
    }

    public String getIEH() {
        return IEH;
    }

    public void setIEH(String IEH) {
        this.IEH = IEH;
    }

    public String getIEN() {
        return IEN;
    }

    public void setIEN(String IEN) {
        this.IEN = IEN;
    }

    public String getIES() {
        return IES;
    }

    public void setIES(String IES) {
        this.IES = IES;
    }

    public String getOSY() {
        return OSY;
    }

    public void setOSY(String OSY) {
        this.OSY = OSY;
    }

    public String getOSM() {
        return OSM;
    }

    public void setOSM(String OSM) {
        this.OSM = OSM;
    }

    public String getOSD() {
        return OSD;
    }

    public void setOSD(String OSD) {
        this.OSD = OSD;
    }

    public String getOSH() {
        return OSH;
    }

    public void setOSH(String OSH) {
        this.OSH = OSH;
    }

    public String getOSN() {
        return OSN;
    }

    public void setOSN(String OSN) {
        this.OSN = OSN;
    }

    public String getOSS() {
        return OSS;
    }

    public void setOSS(String OSS) {
        this.OSS = OSS;
    }

    public String getOEY() {
        return OEY;
    }

    public void setOEY(String OEY) {
        this.OEY = OEY;
    }

    public String getOEM() {
        return OEM;
    }

    public void setOEM(String OEM) {
        this.OEM = OEM;
    }

    public String getOED() {
        return OED;
    }

    public void setOED(String OED) {
        this.OED = OED;
    }

    public String getOEH() {
        return OEH;
    }

    public void setOEH(String OEH) {
        this.OEH = OEH;
    }

    public String getOEN() {
        return OEN;
    }

    public void setOEN(String OEN) {
        this.OEN = OEN;
    }

    public String getOES() {
        return OES;
    }

    public void setOES(String OES) {
        this.OES = OES;
    }

    public String getSCALEWEIGHT() {
        return SCALEWEIGHT;
    }

    public void setSCALEWEIGHT(String SCALEWEIGHT) {
        this.SCALEWEIGHT = SCALEWEIGHT;
    }

    public String getSCALEPARAID() {
        return SCALEPARAID;
    }

    public void setSCALEPARAID(String SCALEPARAID) {
        this.SCALEPARAID = SCALEPARAID;
    }

    public String getISUSETIMENOW() {
        return ISUSETIMENOW;
    }

    public void setISUSETIMENOW(String ISUSETIMENOW) {
        this.ISUSETIMENOW = ISUSETIMENOW;
    }
}
