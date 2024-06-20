package com.example.mytask.service.archiveEquipmentTime.job;


import com.example.mytask.entity.EmsRunTimeStats;
import com.example.mytask.mapper.EmsRunTimeStatsMyMapper;
import com.example.repository.mapper.VPrdCelltaskstoprecordMapper;
import com.example.repository.model.PdsEquipproperty;
import com.example.repository.model.VPrdCelltaskstoprecord;
import com.example.repository.model.VPrdCelltaskstoprecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/*
从TimeByConditionListener归档
 */
@Component
public class GreatEmsRunTimeJob {
    @Autowired
    private ReentrantLock runTimeLock;

    @Autowired
    private List<PdsEquipproperty> timeEps;

    @Autowired
    EmsRunTimeStatsMyMapper mapper;

    @Autowired
    VPrdCelltaskstoprecordMapper sMapper;

    private ZoneId zid = TimeZone.getDefault().toZoneId();

    private static HashMap<String, List<String>> config;

    static {
        config = new HashMap<String, List<String>>();
        config.put("S0001", Arrays.asList("2001", "2002", "2003"));
        config.put("S0002", Arrays.asList("2004"));
        config.put("S0003", Arrays.asList("2001", "2002", "2003", "2004"));
        config.put("S0004", Arrays.asList("3001", "3002", "3003", "3004", "9001", "9002"));
        config.put("S0005", Arrays.asList("6001", "6002", "6003", "6004"));
        config.put("S0006", Arrays.asList("3002"));
        config.put("S0007", Arrays.asList("3004"));
    }

    @Scheduled(cron = "0 55 23 * * *")
    public void run() {
        boolean isLocked = runTimeLock.tryLock();
        if (isLocked) {
            try {
                //        System.out.println("timeEps = " + timeEps);
                Map<String, Long> longMap = timeEps.stream()
                        .collect(Collectors.toMap(PdsEquipproperty::getPropertyid,
                                this::getStopTime));

                timeEps.forEach(c -> {
                    EmsRunTimeStats newRe = new EmsRunTimeStats();
                    newRe.setEQUIPELEMENTID(c.getPropertyid());
                    newRe.setEQUIPPROPERTYID(c.getPropertyid());
                    newRe.setEMSPROPERTYID("ECP0016");
                    newRe.setRECORDDATE(new Date());
                    AtomicLong l = (AtomicLong) c.getValue();
                    Long v = 0L;
                    if (l != null) {
                        v = l.longValue();
                    }
                    newRe.setPROPERTYVALUE(v);
                    newRe.setDELTAVALUE(v);
                    newRe.setRECORDTIME(new Date());
                    Long stopTime = longMap.getOrDefault(c.getPropertyid(), 0L);
                    newRe.setSTOPTIME(stopTime);
                    Long noFaultTime;
                    if (v >= stopTime) {
                        noFaultTime = v - stopTime;
                    } else {
                        noFaultTime = 0L;
                    }
                    newRe.setNOFAULTTIME(noFaultTime);
                    mapper.addEmsRunTimeStats(newRe);
                    if (l != null) {
                        l.set(0);
                    }
//            c.setValue(null);
                });

                System.out.println(LocalDateTime.now().toString() + " Great " +
                        "job! 电机运行时间归档EMS_RUN_TIME_STATS完成");
            } finally {
                runTimeLock.unlock();
            }
        }
    }

    private Long getStopTime(PdsEquipproperty p) {
        String propertyid = p.getPropertyid();
        List<String> cells = config.getOrDefault(propertyid, new ArrayList<>());
        if (cells.isEmpty()) {
            return 0L;
        }
        VPrdCelltaskstoprecordExample ex = new VPrdCelltaskstoprecordExample();
        LocalDate ld = LocalDate.now();
        LocalDateTime ldt1 = ld.atStartOfDay();
        Date startTime = Date.from(ldt1.atZone(zid).toInstant());
        LocalDateTime ldt2 = ld.plusDays(1L).atStartOfDay();
        Date endTime = Date.from(ldt2.atZone(zid).toInstant());

        ex.createCriteria().andStopstarttimeBetween(startTime, endTime).andCellidIn(cells);
        List<VPrdCelltaskstoprecord> psrecords = sMapper.selectByExample(ex);
        return psrecords.stream().mapToLong(c -> {
            Long a1 = c.getTimelong().longValue();
            if (a1 == null) {
                a1 = 0L;
            }
            return a1;
        }).sum();
    }
}
