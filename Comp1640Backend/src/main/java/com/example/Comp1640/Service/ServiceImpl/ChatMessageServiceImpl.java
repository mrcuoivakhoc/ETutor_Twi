package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.WbsChatMessage;
import com.example.Comp1640.Entity.ChatMessage;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.ChatMessageRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.ChatMessageService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserService userService;

    public ChatMessageServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendMessageOneToOne(WbsChatMessage wbsChatMessage) {

        Long senderId = userService.findIdByUser(wbsChatMessage.getSender());
        User sender = userService.findUserById(senderId).orElse(null);

        Long recipientId = userService.findIdByUser(wbsChatMessage.getRecipient());
        User recipient = userService.findUserById(recipientId).orElse(null);


        ChatMessage chatMessage = new ChatMessage(sender, recipient, wbsChatMessage.getContent());
        chatMessageRepository.save(chatMessage);

        messagingTemplate.convertAndSendToUser(recipient.getUsername(), "/queue/messages", wbsChatMessage);


    }
}
