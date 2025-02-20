package com.example.ems.service;

import com.example.ems.entity.FinancialDocument;
import com.example.ems.entity.Users;
import com.example.ems.repository.FinancialDocumentRepository;
import com.example.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FinancialDocumentService {

    @Autowired
    private FinancialDocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    
    
    
  
    /**
     * Upload Document for a User
     * 
     * @param userId
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadDocument(UUID userId, MultipartFile file) throws IOException {
    	
        Users user = userRepository.findByUid(userId);
        if (user == null) {
            return "User not found";
        }

        String fileUrl = s3Service.uploadFile(file);

        FinancialDocument document = new FinancialDocument();
        document.setUser(user);
        document.setDocName(file.getOriginalFilename());
        document.setDocUrl(fileUrl);

        documentRepository.save(document);

        return "File uploaded successfully: " + fileUrl;
    }


    
    
    /**
     * Get User's Documents
     * 
     * @param userId
     * @return
     */
    public List<FinancialDocument> getUserDocuments(UUID userId) {
    	
      
        return documentRepository.findByUser(userRepository.findByUid(userId));
    }
}

