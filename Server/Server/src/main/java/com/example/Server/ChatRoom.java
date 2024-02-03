package com.example.Server;

public class ChatRoom {

    private String name;
    private String topic;

    ChatRoom(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }
}
