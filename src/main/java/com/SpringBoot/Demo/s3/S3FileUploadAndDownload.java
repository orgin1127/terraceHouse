package com.SpringBoot.Demo.s3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3FileUploadAndDownload {
	
	private AmazonS3 S3;
	
	//Initialize S3 Client 
	public S3FileUploadAndDownload(String accessKey, String secretKey) throws Exception {
		AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
		S3 = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(creds))
					.withRegion(Regions.AP_NORTHEAST_2)       // region
					.withForceGlobalBucketAccessEnabled(true) // access
					.build();
	}
	
	//Create Folder in AWS S3 Bucket
	public void createFolder(String bucketName, String folderName) {
        S3.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
    }
	
	//Upload File to AWS S3
	public void fileUpload(MultipartFile file,String memberid,String bucketName) throws FileNotFoundException {
		 try {
			 File f = convertMultiPartToFile(file);
			 String fileName = generateFileName(file);
			 S3.putObject(new PutObjectRequest(bucketName+"/"+fileName, fileName, f));
			 f.delete();
	     } 
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	       
    }
	
	private String generateFileName(MultipartFile multiPart) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String savedFilename = sdf.format(new Date());
        return savedFilename;
    }
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
	
}
