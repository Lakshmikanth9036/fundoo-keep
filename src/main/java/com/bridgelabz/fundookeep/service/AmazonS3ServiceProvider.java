package com.bridgelabz.fundookeep.service;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AmazonS3ServiceProvider {
	
	@Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private  AmazonS3 amazonS3Client;

    public URL storeObjectInS3(MultipartFile file, String fileName, String contentType) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(file.getSize());
        //TODO add cache control
        try {
            amazonS3Client.putObject(bucketName, fileName,file.getInputStream(), objectMetadata);
        } catch(AmazonClientException |IOException exception) {
            throw new RuntimeException("Error while uploading file.");
        }
        return amazonS3Client.getUrl(bucketName, fileName);
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

    public void deleteObject(String key) {
        try {
            amazonS3Client.deleteObject(bucketName, key);
        }catch (AmazonServiceException serviceException) {
            log.error(serviceException.getErrorMessage());
        } catch (AmazonClientException exception) {
            log.error("Error while deleting File.");
        }
    }

}
