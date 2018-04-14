package com.SpringBoot.Demo.s3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class S3FileUploadAndDownload {
	AmazonS3 S3;
	
	public S3FileUploadAndDownload(String accessKey, String secretKey) throws Exception {
		AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
		S3 = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(creds))
					.withRegion(Regions.AP_SOUTHEAST_1)       // region
					.withForceGlobalBucketAccessEnabled(true) // access
					.build();
	}
	
	public void createFolder(String bucketName, String folderName) {
        S3.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
    }
	
	public void fileUpload(String bucketName, File file) throws FileNotFoundException {
        S3.putObject(bucketName, file.getName(), new FileInputStream(file), new ObjectMetadata());
    }
	
	/*public void uploadFile(String bucketName, String fileName, File file ) {
		if ( S3 != null ) {
			try {
				PutObjectRequest putObjectRequest =
						new PutObjectRequest(bucketName, fileName, file);
				// file permission
				putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
				// upload file
				PutObjectResult ret =  S3.putObject(putObjectRequest);
				System.out.println( "ret: " + ret.getETag() );

			} catch ( AmazonServiceException ase) {
				ase.printStackTrace();
			} finally {
				S3 = null;
			}
		}
	}*/
}
