package com.example.ems.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.dto.MessageRequestDto;
import com.example.ems.entity.Message;
import com.example.ems.entity.Users;
import com.example.ems.service.MessageService;



@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }



    /**
     * @param messageRequest
     * @return
     */
    @PostMapping("/send")
    public Message sendMessage(@RequestBody MessageRequestDto messageRequest) {
    	System.out.println("inside send" + messageRequest.getSenderId());
        UUID senderId = messageRequest.getSenderId();
        UUID receiverId = messageRequest.getReceiverId();
        String messageBody = messageRequest.getMessageBody();
        return messageService.sendMessage(senderId, receiverId, messageBody);
    }
    
    
    
    /**
     * @return
     */
    @GetMapping("/getadmins")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<Users> admins = messageService.getAllAdmins();

        // Convert Users List to a JSON-friendly format
        List<Map<String, Object>> adminList = admins.stream()
        		.map(admin -> {
        	        Map<String, Object> adminMap = new HashMap<>();
        	        adminMap.put("uid", admin.getUid()); 
        	        adminMap.put("name", admin.getName());
        	        return adminMap;
        	    })
        	    .collect(Collectors.toList());        
        
        return ResponseEntity.ok(adminList);

    }
    
    

    /**
     * @param userId
     * @return
     */
    @GetMapping("/received/{userId}")
    public List<Message> getReceivedMessages(@PathVariable UUID userId) {
        return messageService.getMessagesForUser(userId);
    }

    
    
    /**
     * @param userId
     * @return
     */
    @GetMapping("/sent/{userId}")
    public List<Message> getSentMessages(@PathVariable UUID userId) {
        return messageService.getSentMessagesForUser(userId);
    }
    
}