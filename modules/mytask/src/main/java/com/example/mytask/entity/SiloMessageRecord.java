package com.example.mytask.entity;

import java.util.Date;

public class SiloMessageRecord extends SiloMessage{
    private String topic;
    private Date createdAt = new Date();

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
