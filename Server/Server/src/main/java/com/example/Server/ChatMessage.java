package com.example.Server;

public class ChatMessage {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    private String message;
    private String topic;

    ChatMessage(String message, String topic) {
        this.message = message;
        this.topic = topic;
    }
}
