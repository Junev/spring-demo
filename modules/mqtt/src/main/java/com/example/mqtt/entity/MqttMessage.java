package com.example.mqtt.entity;

public class MqttMessage {

    /**
     * msgstr : {"SILOID":"102304","TASKID":"221229030102","SILOTASKID":"","INOROUTBATCHID":"",
     * "INOROUTMATID":"","INOROUTAMOUNT":"0.00","REMAINAMOUNT":"0.00","SILOSTORETIMELONG":"0.00",
     * "ISY":"0","ISM":"0","ISD":"0","ISH":"0","ISN":"0","ISS":"0","IEY":"0","IEM":"0","IED":"0",
     * "IEH":"0","IEN":"0","IES":"0","OSY":"2022.00","OSM":"12.00","OSD":"17.00","OSH":"13.00",
     * "OSN":"4.00","OSS":"28.00","OEY":"2022.00","OEM":"12.00","OED":"17.00","OEH":"14.00",
     * "OEN":"32.00","OES":"50.00","SCALEWEIGHT":"5651.20","SCALEPARAID":"u10202pt0081",
     * "ISUSETIMENOW":"1"}
     * msgid : ksec.pds.common.1-17259
     */

    private String msgstr;
    private String msgid;

    public String getMsgstr() {
        return msgstr;
    }

    public void setMsgstr(String msgstr) {
        this.msgstr = msgstr;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }


}
