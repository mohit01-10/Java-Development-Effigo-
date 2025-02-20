package com.example.ems.repository;

import com.example.ems.entity.Message;
import com.example.ems.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
	
	 List<Message> findBySenderUid(UUID senderId);  
	 
	 List<Message> findByReceiverUid(UUID receiverId);
	 
	 void deleteBySender(Users sender);
	   
	 void deleteByReceiver(Users receiver);
	   

}


