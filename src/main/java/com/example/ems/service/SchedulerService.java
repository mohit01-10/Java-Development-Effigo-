package com.example.ems.service;

import com.example.ems.entity.Users;
import com.example.ems.enums.UserStatus;
import com.example.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private UserRepository userRepository;


    /**
     * Scheduled Task: Runs Every Midnight (00:00)
     */
    @Scheduled(cron = "0 44 17 * * ?")// Runs at 12:00 AM every day
//    @Scheduled(fixedRate = 60000)
    public void autoApprovePendingUsers() {
        System.out.println(" [Scheduler] Checking for pending users...");

        List<Users> pendingUsers = userRepository.findByStatus(UserStatus.PENDING);

        if (!pendingUsers.isEmpty()) {
            for (Users user : pendingUsers) {
                user.setStatus(UserStatus.ACTIVE); // Update status
            }
            userRepository.saveAll(pendingUsers); // Save changes to database
            System.out.println(" [Scheduler] Approved " + pendingUsers.size() + " users.");
            
        } else {
            System.out.println("[Scheduler] No pending users found.");
        }
    }
}
