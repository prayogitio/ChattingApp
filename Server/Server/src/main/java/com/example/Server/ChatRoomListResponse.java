package com.example.Server;

import java.util.List;

public class ChatRoomListResponse {

    private List<ChatRoom> chatRoomList;

    ChatRoomListResponse(List<ChatRoom> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }

    public List<ChatRoom> getChatRoomList() {
        return chatRoomList;
    }
}
