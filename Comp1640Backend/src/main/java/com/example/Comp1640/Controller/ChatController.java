package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.WbsChatMessage;
import com.example.Comp1640.Service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("chat.sendMessage")  // Maps messages sent to "chat.sendMessage" WebSocket destination
    public void sendMessageOneToOne(@Payload WbsChatMessage wbsChatMessage) {
        chatMessageService.sendMessageOneToOne(wbsChatMessage);
    }

    

}
