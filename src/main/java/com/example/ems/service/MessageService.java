package com.example.ems.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ems.entity.Message;
import com.example.ems.entity.Users;
import com.example.ems.repository.MessageRepository;
import com.example.ems.repository.UserRepository;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    
    

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    
    
    
    /**
     * @param senderId
     * @param receiverId
     * @param messageBody
     * @return
     * @throws IllegalArgumentException
     */
    public Message sendMessage(UUID senderId, UUID receiverId, String messageBody) throws IllegalArgumentException {
    	
    	System.out.println(senderId + "  reciverId  " +receiverId);
        Users sender = userRepository.findByUid(senderId);

        Users receiver = userRepository.findByUid(receiverId);


        Message message = Message.builder()
            .sender(sender)
            .receiver(receiver)
            .messageBody(messageBody)
            .build();

        return messageRepository.save(message);
    }
    
    
    

    /**
     * @return
     */
    public List<Users> getAllAdmins() {
        
    	List<Users> adminUsers = userRepository.findByRole_Role("Admin");
    	
        return  adminUsers;
    }
    
    
    
    /**
     * @param userId
     * @return
     */
    public List<Message> getMessagesForUser(UUID userId) {
        return messageRepository.findByReceiverUid(userId);
    }
    
    

    /**
     * @param userId
     * @return
     */
    public List<Message> getSentMessagesForUser(UUID userId) {
        return messageRepository.findBySenderUid(userId);
    }
    
}