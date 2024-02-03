package com.example.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class ChatController {

    private SimpMessagingTemplate template;

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @GetMapping(value = "/chatrooms", produces = "application/json")
    public ChatRoomListResponse index() {
        ChatRoom firstRoom = new ChatRoom("First Room", "first-room");
        ChatRoom secondRoom = new ChatRoom("Second Room", "second-room");
        ChatRoom thirdRoom = new ChatRoom("Third Room", "third-room");

        return new ChatRoomListResponse(List.of(firstRoom, secondRoom, thirdRoom));
    }
    
    @MessageMapping("/chat")
    public void sendMessage(ChatMessage message) {
        String topic = message.getTopic();
        this.template.convertAndSend("/topic/" + topic, message);
    }

    @RequestMapping(path="/chat", method=RequestMethod.POST)
    public void greet(@RequestBody ChatMessage message) {
        String topic = message.getTopic();
        this.template.convertAndSend("/topic/" + topic, message);
    }
}