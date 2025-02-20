package com.example.ems.repository;

import com.example.ems.entity.LoginHistory;
import com.example.ems.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;


@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, UUID> {
    
	
	 @Query(value = "SELECT u.uid AS user_id, u.email AS user_email, u.name AS user_name, lh.login_time " +
             "FROM myemsch.login_history lh " +
             "JOIN myemsch.users u ON lh.uid = u.uid " +
             "ORDER BY lh.login_time DESC",
			     countQuery = "SELECT COUNT(*) FROM myemsch.login_history", // For pagination count
			     nativeQuery = true)
	Page<Map<String, Object>> findLoginHistory(Pageable pageable);
	 
    void deleteByUser(Users user);
}


