package com.SpringBoot.Demo.s3;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;

public class UploadFileUtil {
	
	public void uploadFile(String uploadPath, String originalName, byte[] byteData) throws Exception {
		S3Util s3 = new S3Util();
		String bucketName = "terracehouse-user-bucket";
		String uploadedFileName = null;
		
	

	}

}
