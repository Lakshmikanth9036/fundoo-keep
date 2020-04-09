package com.bridgelabz.fundookeep.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {

	public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,String token);

    public void deleteFileFromS3Bucket(String fileName,String token);
    
    public String fetchObjectURL(String token);
}
