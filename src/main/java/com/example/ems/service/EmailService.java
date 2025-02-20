package com.example.ems.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	
	
	/**
	 * @param superAdminEmail
	 * @param userName
	 * @param userId
	 * @throws MessagingException
	 */
	public void sendApprovalRequest(String superAdminEmail, String userName, UUID userId) throws MessagingException {
        String subject = "New User Approval Request";
        String approvalLink = "http://localhost:8080/admin/approve-user/" + userId;

        String emailBody = "<h3>User Approval Request</h3>"
                + "<p>User <b>" + userName + "</b> has registered and requires approval.</p>"
                + "<p>Click the link below to approve:</p>"
                + "<a href='" + approvalLink + "' style='color:blue;'>Approve User</a>";

        sendEmail(superAdminEmail, subject, emailBody);
    }
	
	
	
	
	/**
	 * @param userEmail
	 * @param userName
	 * @throws MessagingException
	 */
	public void sendApproval(String userEmail, String userName) throws MessagingException {
		
        String subject = "User Approved";
        String loginLink = "http://localhost:8080/login";

        String emailBody = "<h3>Request Approved</h3>"
                + "<p>User <b>" + userName + "</b> you have been registered successfully! <br> You can login to account</p>"
          
                + "<a href='" + loginLink + "' style='color:blue;'>Login here</a>";

        sendEmail(userEmail, subject, emailBody);
    }
	
	
	
	
	
	/**
	 * @param to
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 */
	private void sendEmail(String to, String subject, String body) throws MessagingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);
		
		mailSender.send(message);

		System.out.println("Email sent!");

	}
}
