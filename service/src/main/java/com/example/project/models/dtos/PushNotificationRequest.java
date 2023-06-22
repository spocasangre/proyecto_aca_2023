package com.example.project.models.dtos;

public class PushNotificationRequest {
    private String topic;
    private String token;

    public PushNotificationRequest() {
        super();
    }

    public PushNotificationRequest(String topic, String token) {
        super();
        this.topic = topic;
        this.token = token;
    }

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
