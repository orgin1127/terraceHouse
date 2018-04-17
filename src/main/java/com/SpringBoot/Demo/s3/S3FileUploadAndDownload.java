package com.SpringBoot.Demo.s3;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.web.multipart.MultipartFile;

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
	public String fileUpload(MultipartFile file,String memberid,String bucketName, String terraceName) throws FileNotFoundException {
		String putResult = "";
		try {
			File f = convertMultiPartToFile(file);
			String fileName = generateFileName(file);
			String path = bucketName+"/"+"tr-user-files/"+memberid+"/"+fileName;
			PutObjectResult result = S3.putObject(new PutObjectRequest(path, fileName+terraceName+".pdf", f));
			
			int pages = 0;
			PDDocument doc = PDDocument.load(f);
			PDFRenderer renderer = new PDFRenderer(doc);
			pages = doc.getNumberOfPages();
			ArrayList<File> imgs = new ArrayList<>();
			for (int i = 0 ; i < pages ; i++) {
				BufferedImage image = renderer.renderImage(i);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(image, "png", os);
				byte[] buffer = os.toByteArray();
				InputStream is = new ByteArrayInputStream(buffer);
				ObjectMetadata meta = new ObjectMetadata();
				meta.setContentLength(buffer.length);
				String imgName = "myImage"+i;
				S3.putObject(new PutObjectRequest(path+"image", imgName+".png", is, meta).withCannedAcl(CannedAccessControlList.PublicRead));
				is.close();
				os.close();
			}
			f.delete();
			if (result != null) {
				putResult = "ok";
			}
	     } 
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		 return putResult;
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
