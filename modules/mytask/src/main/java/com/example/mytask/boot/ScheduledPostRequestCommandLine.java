package com.example.mytask.boot;

import com.example.repository.mapper.PdsEquippropertyMapper;
import com.example.repository.mapper.PrdBatchMapper;
import com.example.repository.mapper.PrdUnitcmdMapper;
import com.example.repository.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//@Component
public class ScheduledPostRequestCommandLine implements CommandLineRunner {
    @Value("${ksecapi.url}")
    private String apiUrl;

    @Autowired
    private PrdUnitcmdMapper unitcmdMapper;

    @Autowired
    private PrdBatchMapper batchMapper;

    @Autowired
    private PdsEquippropertyMapper peMapper;

    @Override
    public void run(String... args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(this.apiUrl + '/');

                String sid = getSID();
                Integer r = loginPMS(sid);

                if (r != 0) {
                    System.out.println("登录失败");
                    return;
                }

                ZoneId zoneId = TimeZone.getDefault().toZoneId();
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime lastHour = now.plusHours(-240L);
                System.out.println("------------------------------------处理缺失数据-------------------------------");
                Instant endInstant = now.atZone(zoneId).toInstant();
                Instant startInstant = lastHour.atZone(zoneId).toInstant();
                java.util.Date endDate = Date.from(endInstant);
                java.util.Date startDate = Date.from(startInstant);
                PrdUnitcmdExample ex = new PrdUnitcmdExample();
                ex.createCriteria()
                        .andExestarttimeGreaterThan(startDate)
                        .andExestarttimeLessThan(endDate);
                List<PrdUnitcmd> cmds = unitcmdMapper.selectByExample(ex);
                cmds.forEach(c -> {
                    Integer prodDocRes = prodDoc(sid, c.getTaskid());

                });
                System.out.println(LocalDateTime.now().toString() + " 重算产耗完成");

//                cmds.stream().filter(c -> c.getSchedstatus() == 1).forEach(k -> {
//                    Integer quaRes = quaRestatistic(sid, k.getCmdid());
//                });
//                System.out.println(LocalDateTime.now().toString() + " 重算质量数据完成");

                List<String> taskIds = cmds.stream()
                        .map(PrdUnitcmd::getTaskid)
                        .collect(Collectors.toList());
                siloStoreTime(sid, taskIds);
                System.out.println(LocalDateTime.now().toString() + " 贮柜存料时间计算完成");

                PrdBatchExample eb = new PrdBatchExample();
                LocalDateTime lastMonth = now.plusDays(-24L);
                eb.createCriteria()
                        .andExestarttimeGreaterThan(Date.from(lastMonth.atZone(zoneId)
                                .toInstant()));
                List<PrdBatch> batches = batchMapper.selectByExample(eb);
                batches.forEach(c -> {
                    taskIntervalTime(sid, c.getBatchid());
                });
                System.out.println(LocalDateTime.now().toString() + " 计算工单间隔时间完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 40, TimeUnit.MINUTES);
    }

    private Integer siloStoreTime(String sid, List<String> taskIds) {
        String s1 = toJSON(taskIds, "[]");

        String json = "{\"t\":\"modulefun\\\\silo_store_time\",\"i\":\"" + sid + "\"," +
                "\"d\":{\"task_ids\":" + s1 + "}}";
        return handleRequestResult(json);
    }

    private Integer taskIntervalTime(String sid, String batchId) {
        String json = "{\"t\":\"modulefun\\\\task_interval_time\",\"i\":\"" + sid + "\"," +
                "\"d\":{\"batch_id\":\"" + batchId + "\"}}";
        return handleRequestResult(json);
    }


    private Integer prodDoc(String sid, String taskId) {
        String json = "{\"t\":\"prdtask\\\\celltask_prddoc\",\"i\":\"" + sid + "\"," +
                "\"d\":{\"TASKID\":\"" + taskId + "\"}}";
        return handleRequestResult(json);
    }

    private Integer quaRestatistic(String sid, String cmdId) {
        PrdUnitcmdExample ex1 = new PrdUnitcmdExample();
        ex1.createCriteria().andCmdidEqualTo(cmdId);
        List<PrdUnitcmd> prdUnitcmds = unitcmdMapper.selectByExample(ex1);
        String equipmentId = "";
        if (prdUnitcmds.size() > 0) {
            equipmentId = prdUnitcmds.get(0).getUnitid();
        }
        if (equipmentId.equals("")) {
            return 0;
        }
        PdsEquippropertyExample ex = new PdsEquippropertyExample();
        ex.createCriteria().andEquipmentidEqualTo(equipmentId).andPropertygroupBetween(7L, 8L);
        List<PdsEquipproperty> prdUnitcmdParas = peMapper.selectByExample(ex);
        List<String> ids = prdUnitcmdParas.stream()
                .map(PdsEquipproperty::getPropertyid)
                .collect(Collectors.toList());
        String s1 = toJSON(ids, "[]");


        String json2 = "{\"t\":\"qua\\\\get\",\"i\":\"" + sid + "\"," +
                "\"d\":{\"t\":\"tsd\",\"p\":{\"BatchID\":null,\"TaskID\":null," +
                "\"CmdID\":\"" + cmdId + "\",\"DataCleaning\":true,\"GetWithQuaResult\":true," +
                "\"ReStatic\":true,\"SaveQuaResult\":true,\"t\":0,\"OutResult\":true," +
                "\"ids\":" + s1 + "}}}";
        return handleRequestResult(json2);
    }

    private Integer quaSet(String sid, String cmdId) {
        String json =
                "{\"t\":\"qua\\\\set\",\"i\":\"" + sid + "\",\"p\":{\"t\":2,\"CmdID\":\"" + cmdId +
                        "\"}}";
        return handleRequestResult(json);
    }

    private String toJSON(Object obj, String defaultValue) {
        ObjectMapper obm1 = new ObjectMapper();
        String s1 = "";
        try {
            s1 = obm1.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s1;
    }

    private Integer handleRequestResult(String json) {
        try {
            JsonNode jsonNode = sendAPIRequest(json);
            return jsonNode.get("s").asInt();
        } catch (NullPointerException e) {
            return -1;
        }

    }

    private JsonNode sendAPIRequest(String json) {
        String res1 = sendPostRequest(this.apiUrl + "/api", json);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(res1);
        } catch (JsonProcessingException e) {
            System.out.println("解析JSON错误");
//            e.printStackTrace();
        }
        return jsonNode;
    }

    private Integer loginPMS(String sid) {
        String json = "{\"t\":\"login\",\"i\":\"" + sid +
                "\",\"d\":{\"eid\":null,\"uid\":\"lj\"," +
                "\"pwd\":\"123\",\"verify_code\":\"\",\"pms_flag\":false}}";
        return handleRequestResult(json);
    }

    private String getSID() {
        String json = "{\"i\": \"\", \"t\": \"get_session_uid\", \"d\":{}}";
        JsonNode jsonNode = sendAPIRequest(json);
        if (jsonNode != null) {
            return jsonNode.get("d").get("sid").asText();
        }
        return "";
    }

    private static String sendPostRequest(String url, String requestBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(new StringEntity(requestBody, "UTF-8"));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity);
            } else {
                return "HTTP error response: " + statusCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred during the request: " + e.getMessage();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
