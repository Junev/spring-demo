package com.example.mytask.service.archiveSILO;

import com.example.mytask.entity.PropertyValue;
import com.example.mytask.entity.SiloTask;
import com.example.mytask.service.OpcValueListener;
import com.example.repository.mapper.SiloinfoMapper;
import com.example.repository.model.PdsEquipproperty;
import com.example.repository.model.Siloinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OpcValueChangeListener implements OpcValueListener {
    @Autowired
    private List<String> siloIds;

    @Autowired
    private SiloinfoMapper siloinfoMapper;

    private List<PdsEquipproperty> preEps;

    private HashMap<String, SiloTask> preTasks = new HashMap<>();

    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa",
            Locale.ENGLISH);

    public OpcValueChangeListener(List<String> siloIds) {
        this.siloIds = siloIds;
    }

    @Override
    public void update(Map<String, PdsEquipproperty> epsValueMap) {
//        System.out.println("epsValue = " + epsValue);
//        System.out.println("epsValue1_" + epsValue.get(213).getValue());
//        System.out.println("siloIds = " + siloIds);

        List<PdsEquipproperty> epsValue = new ArrayList<>(epsValueMap.values());

        siloIds.forEach(c -> SiloInOrOut(epsValue, c));

        preEps = null;
        preEps = epsValue;
    }

    private void SiloInOrOut(List<PdsEquipproperty> epsValue, String siloId) {
        PropertyValue getInWeight = getEpValueByEpID(epsValue, siloId + "021");
        if (getInWeight.getPreValue() != null && getInWeight.getValue() != null) {
            float preValue = (float) getInWeight.getPreValue();
            float value = (float) getInWeight.getValue();
            if (preValue < value) {
                recordFirst(siloId, 1, epsValue);
            }
        }

        PropertyValue remainWeight = getEpValueByEpID(epsValue, siloId + "024");
        if (remainWeight.getPreValue() != null && remainWeight.getValue() != null) {
            float preValue = (float) remainWeight.getPreValue();
            float value = (float) remainWeight.getValue();
            if (preValue > value) {
                recordFirst(siloId, 2, epsValue);
            }
        }
    }

    private void recordFirst(String siloId, int inorout, List<PdsEquipproperty> epsValues) {
        String mapKey = siloId + "_" + inorout;
        SiloTask task = new SiloTask();
        task.setInorout(inorout);
        task.setTaskId("");
        task.setSiloId(siloId);
        if (!preTasks.containsKey(mapKey)) {
            preTasks.put(mapKey, task);
            System.out.println("Not contains " + mapKey);
            saveInOrOutRecord(siloId, inorout, epsValues);
        } else {
            SiloTask preTask = preTasks.get(mapKey);
            if (!preTask.equals(task)) {
                preTasks.put(mapKey, task);
                System.out.println("记录：" + mapKey + " ");
                saveInOrOutRecord(siloId, inorout, epsValues);
            }
        }
    }

    private void saveInOrOutRecord(String siloId, int inorout,
                                   List<PdsEquipproperty> epsValues) {
        List<PdsEquipproperty> siloRecord = epsValues.stream()
                .filter(c -> c.getPropertyid().startsWith(siloId))
                .collect(Collectors.toList());
        Siloinfo siloinfo = new Siloinfo();
        siloinfo.setInOrOut(inorout);
        siloinfo.setTaskId((String) getSiloInfoByEpSuffix(siloRecord, "01"));
        siloinfo.setBatchId((String) getSiloInfoByEpSuffix(siloRecord, "02"));
        siloinfo.setBrandId((String) getSiloInfoByEpSuffix(siloRecord, "03"));
        siloinfo.setBrandName((String) getSiloInfoByEpSuffix(siloRecord, "04"));
        siloinfo.setModNo((String) getSiloInfoByEpSuffix(siloRecord, "05"));
        siloinfo.setModNm((String) getSiloInfoByEpSuffix(siloRecord, "06"));
        siloinfo.setPathNo((String) getSiloInfoByEpSuffix(siloRecord, "07"));
        siloinfo.setPathNm((String) getSiloInfoByEpSuffix(siloRecord, "08"));
        siloinfo.setCrVersion((String) getSiloInfoByEpSuffix(siloRecord, "09"));
        siloinfo.setBatchWeight((Float) getSiloInfoByEpSuffix(siloRecord, "10"));
        siloinfo.setAmountOfCategories((Float) getSiloInfoByEpSuffix(siloRecord, "11"));
        Object planStartTimeStr = getSiloInfoByEpSuffix(siloRecord, "12");
        Object planEndTimeStr = getSiloInfoByEpSuffix(siloRecord, "13");
        try {
            Date planStartTime = sdf.parse((String) planStartTimeStr);
            siloinfo.setPlanStartTime(planStartTime);
            Date planEndTime = sdf.parse((String) planEndTimeStr);
            siloinfo.setPlanEndTime(planEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        siloinfo.setSequenceNm((Float) getSiloInfoByEpSuffix(siloRecord, "14"));
        siloinfo.setStatus((Float) getSiloInfoByEpSuffix(siloRecord, "15"));
        siloinfo.setClassId((Float) getSiloInfoByEpSuffix(siloRecord, "16"));
        siloinfo.setClassType((Float) getSiloInfoByEpSuffix(siloRecord, "17"));
        siloinfo.setSiloId((Float) getSiloInfoByEpSuffix(siloRecord, "18"));
        siloinfo.setGroupNum(((Short) getSiloInfoByEpSuffix(siloRecord, "19")).intValue());
        siloinfo.setTypeOfLoaded((Float) getSiloInfoByEpSuffix(siloRecord, "20"));
        siloinfo.setLoadedWeight((Float) getSiloInfoByEpSuffix(siloRecord, "21"));
        siloinfo.setDryTobaccoWeight((Float) getSiloInfoByEpSuffix(siloRecord, "22"));
        siloinfo.setAvMoisture((Float) getSiloInfoByEpSuffix(siloRecord, "23"));
        siloinfo.setRemainWeight((Float) getSiloInfoByEpSuffix(siloRecord, "24"));
        siloinfo.setCategories((Float) getSiloInfoByEpSuffix(siloRecord, "25"));
        siloinfo.setRpStoretime((Float) getSiloInfoByEpSuffix(siloRecord, "26"));
        siloinfo.setPvStoretime((Float) getSiloInfoByEpSuffix(siloRecord, "27"));
        siloinfo.setInspection(((Short) getSiloInfoByEpSuffix(siloRecord, "28")).intValue());
        siloinfo.setTobStatus(((Short) getSiloInfoByEpSuffix(siloRecord, "29")).intValue());
        siloinfo.setProcessStatus(((Short) getSiloInfoByEpSuffix(siloRecord, "30")).intValue());
        siloinfo.setInOutStatus(((Short) getSiloInfoByEpSuffix(siloRecord, "31")).intValue());
        siloinfo.setResidenceTimeDay(((Short) getSiloInfoByEpSuffix(siloRecord, "32")).intValue());
        siloinfo.setResidenceTimeHour(((Short) getSiloInfoByEpSuffix(siloRecord, "33")).intValue());
        siloinfo.setResidenceTimeMinute(((Short) getSiloInfoByEpSuffix(siloRecord,
                "34")).intValue());
        siloinfo.setResidenceTimeSeconds(((Short) getSiloInfoByEpSuffix(siloRecord,
                "35")).intValue());
        if ((short) getSiloInfoByEpSuffix(siloRecord, "36") != 0) {
            LocalDateTime loadingStart = LocalDateTime.of(
                    (short) getSiloInfoByEpSuffix(siloRecord, "36"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "37"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "38"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "39"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "40"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "41")
            );
            Date loadingStartTime = Date.from(loadingStart.atZone(ZoneId.systemDefault())
                    .toInstant());
            siloinfo.setLoadingStartTime(loadingStartTime);
        }

        if ((short) getSiloInfoByEpSuffix(siloRecord, "42") != 0) {
            LocalDateTime loadingEnd = LocalDateTime.of(
                    (short) getSiloInfoByEpSuffix(siloRecord, "42"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "43"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "44"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "45"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "46"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "47")
            );
            Date loadingEndTime = Date.from(loadingEnd.atZone(ZoneId.systemDefault()).toInstant());
            siloinfo.setLoadingEndTime(loadingEndTime);
        }

        if ((short) getSiloInfoByEpSuffix(siloRecord, "48") != 0) {
            LocalDateTime dischargeStart = LocalDateTime.of(
                    (short) getSiloInfoByEpSuffix(siloRecord, "48"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "49"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "50"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "51"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "52"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "53")
            );
            Date dischargeStartTime =
                    Date.from(dischargeStart.atZone(ZoneId.systemDefault()).toInstant());
            siloinfo.setLoadingEndTime(dischargeStartTime);
        }

        if ((short) getSiloInfoByEpSuffix(siloRecord, "54") != 0) {
            LocalDateTime dischargeEnd = LocalDateTime.of(
                    (short) getSiloInfoByEpSuffix(siloRecord, "54"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "55"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "56"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "57"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "58"),
                    (short) getSiloInfoByEpSuffix(siloRecord, "59")
            );
            Date dischargeEndTime =
                    Date.from(dischargeEnd.atZone(ZoneId.systemDefault()).toInstant());
            siloinfo.setLoadingEndTime(dischargeEndTime);
        }

        siloinfoMapper.insert(siloinfo);

    }

    private Object getSiloInfoByEpSuffix(List<PdsEquipproperty> siloRecord, String suffix) {
        for (PdsEquipproperty p : siloRecord) {
            if (p.getPropertyid().endsWith(suffix)) {
                return p.getValue();
            }
        }
        return null;
    }

    private PropertyValue getEpValueByEpID(List<PdsEquipproperty> epsValue, String propertyId) {
        PropertyValue res = new PropertyValue();
        for (PdsEquipproperty p : epsValue) {
            if (p.getPropertyid().equals(propertyId)) {
                res.setPropertyId(propertyId);
                res.setValue(p.getValue());
                int i = epsValue.indexOf(p);
                Object preEpValue = null;
                if (preEps != null) {
                    preEpValue = preEps.get(i).getValue();
                }
                res.setPreValue(preEpValue);
                break;
            }
        }
        return res;
    }


}
