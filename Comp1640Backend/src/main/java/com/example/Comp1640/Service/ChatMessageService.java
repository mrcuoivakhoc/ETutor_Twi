package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.WbsChatMessage;
import org.springframework.messaging.handler.annotation.Payload;

public interface ChatMessageService {

    void sendMessageOneToOne(@Payload WbsChatMessage wbsChatMessage);


}
