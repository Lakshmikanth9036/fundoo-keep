package com.bridgelabz.fundookeep.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.exception.UserException;
import com.bridgelabz.fundookeep.repository.UserRepository;
import com.bridgelabz.fundookeep.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@PropertySource("classpath:message.properties")
public class AmazonS3ServiceProvider implements AmazonS3Service{
	
	@Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private  AmazonS3 amazonS3Client;
    
    @Autowired
	private JwtUtils jwt;
    
    @Autowired
	private UserRepository repository;
    
    @Autowired
	private Environment env;

    @Async
    public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess, String token) 
    {
    	Long uId = jwt.decodeToken(token);
    	User user = repository.findById(uId).orElseThrow(() -> new UserException(404, env.getProperty("104")));
        String fileName = multipartFile.getOriginalFilename();
        user.setProfilePic(fileName);
      
        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucketName, fileName, file);

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3Client.putObject(putObjectRequest);
            //removing the file created in the server
            file.delete();
            repository.save(user);
        } catch (IOException | AmazonServiceException ex) {
            log.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }
    }

    public S3Object fetchObject(String awsFileName) {
        S3Object s3Object;
        try {
            s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, awsFileName));
        }catch (AmazonServiceException serviceException) {
            throw new RuntimeException("Error while streaming File.");
        } catch (AmazonClientException exception) {
            throw new RuntimeException("Error while streaming File.");
        }
        return s3Object;
    }

    @Async
    public void deleteFileFromS3Bucket(String fileName,String token) 
    {
    	Long uId = jwt.decodeToken(token);
    	User user = repository.findById(uId).orElseThrow(() -> new UserException(404, env.getProperty("104")));
        user.setProfilePic(null);
    	

        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (AmazonServiceException ex) {
        	throw new UserException(500, env.getProperty("500"));
        }
        repository.save(user);
    }
}
