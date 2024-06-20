package com.example.mytask.service.archiveEquipmentTime.job;


import com.example.mytask.mapper.EmsRunTimeStatsMyMapper;
import org.springframework.beans.factory.annotation.Autowired;

/*
从PDS_EQUIPPROPERTY的PROCESSVALUE归档
 */
//@Component
public class EmsRunTimeJob {
    @Autowired
    EmsRunTimeStatsMyMapper mapper;

//    @Scheduled(cron = "0 00 11 * * *")
//    public void run() {
//        // 昨天的设备运行时间记录
//        GetEmsRunTimeStatsParas p1 = new GetEmsRunTimeStatsParas();
//        p1.setEmsPropertyId("ECP0016");
//        LocalDate yesterday = LocalDate.now().plus(Period.ofDays(-1));
//        ZonedDateTime zyd = yesterday.atStartOfDay(ZoneId.systemDefault());
//        Date y1 = Date.from(zyd.toInstant());
//        p1.setRecordDate(y1);
//        List<EmsRunTimeStats> yesterdayStats = mapper.getYesterdayCount(p1);
//
//        // 累计设备运行时间
//        GetTimeLongParas p2 = new GetTimeLongParas();
//        p2.setPropertyId("ECP0016");
//        List<EquipTimeLong> timeLong = mapper.getTimeLong(p2);
//        timeLong.forEach(c -> {
//            EmsRunTimeStats yesterdayStat = yesterdayStats.stream()
//                    .filter(d -> d.getEQUIPPROPERTYID().equals(c.getPropertyValue()))
//                    .findFirst().orElseGet(() -> {
//                        EmsRunTimeStats n1 = new EmsRunTimeStats();
//                        n1.setEQUIPELEMENTID(c.getEquipmentId());
//                        n1.setEQUIPPROPERTYID(c.getPropertyValue());
//                        n1.setEMSPROPERTYID(c.getPropertyId());
//                        n1.setRECORDDATE(y1);
//                        n1.setPROPERTYVALUE(0L);
//                        n1.setDELTAVALUE(0L);
//                        n1.setRECORDTIME(y1);
//                        return n1;
//                    });
//
//            EmsRunTimeStats newRe = new EmsRunTimeStats();
//            newRe.setEQUIPELEMENTID(yesterdayStat.getEQUIPELEMENTID());
//            newRe.setEQUIPPROPERTYID(yesterdayStat.getEQUIPPROPERTYID());
//            newRe.setEMSPROPERTYID(yesterdayStat.getEMSPROPERTYID());
//            newRe.setRECORDDATE(new Date());
//            newRe.setPROPERTYVALUE(c.getProcessValue());
//            if (c.getProcessValue() != null && yesterdayStat.getPROPERTYVALUE() != null) {
//                newRe.setDELTAVALUE(c.getProcessValue() - yesterdayStat.getPROPERTYVALUE());
//            } else {
//                newRe.setDELTAVALUE(0L);
//            }
//            newRe.setRECORDTIME(new Date());
//            mapper.addEmsRunTimeStats(newRe);
//        });
//
//        System.out.println(LocalDateTime.now().toString() + " 电机运行时间归档EMS_RUN_TIME_STATS完成");
//    }
}
