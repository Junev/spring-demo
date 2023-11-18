package com.example.mqtt.boot;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledPostRequest implements CommandLineRunner {
    @Value("${ksecapi.url}")
    private String apiUrl;

    @Override
    public void run(String... args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(this.apiUrl + '/');

                String sid = getSID();
                Integer r = loginPMS(sid);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 40, TimeUnit.MINUTES);
    }

    private Integer loginPMS(String sid) throws JsonProcessingException {
        String json = "{\"t\":\"login\",\"i\":\"" + sid +
                "\",\"d\":{\"eid\":null,\"uid\":\"lj\"," +
                "\"pwd\":\"123\",\"verify_code\":\"\",\"pms_flag\":false}}";
        String res1 = sendPostRequest(this.apiUrl + "/api", json);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(res1);
        return jsonNode.get("s").asInt();
    }

    private String getSID() throws JsonProcessingException {
        String json = "{\"i\": \"\", \"t\": \"get_session_uid\", \"d\":{}}";
        String res1 = sendPostRequest(this.apiUrl + "/api", json);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(res1);
        return jsonNode.get("d").get("sid").asText();
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
