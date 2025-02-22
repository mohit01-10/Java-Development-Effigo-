package com.example.ems.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;
    
    

    /**
     * Creating S3 Connection
     * 
     * @param accessKey
     * @param secretKey
     * @param region
     */
    public S3Service(@Value("${aws.accessKeyId}") String accessKey,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.region}") String region) {
    	
    	region = region.trim();
    	
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        
        testS3Connection();
    }
    
    

    /**
     *  Upload File to S3
     *  
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
    	
    	 long MAX_FILE_SIZE = 1 * 1024 * 1024; 

    	    if (file.getSize() > MAX_FILE_SIZE) {
    	        throw new IllegalArgumentException("File size exceeds the maximum limit of 5MB");
    	    }
    	
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename(); // Generate Unique Name
        
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName; 
    }
    
    
    
    /**
     * Testing
     */
    public void testS3Connection() {
        try {
            s3Client.listBuckets();
            System.out.println("AWS S3 Connection Successful!");
        } catch (Exception e) {
            System.err.println("AWS S3 Connection Failed: " + e.getMessage());
        }
    }

}

