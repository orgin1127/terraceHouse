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
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.PersonalFile.PersonalFile;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;
import com.SpringBoot.Demo.Service.TerraceRoomService;
import com.SpringBoot.Demo.dto.TerraceRoomSaveRequestDto;
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
	
	private TerraceRoomService terraceRoomService;
	
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
	
	//Upload File to AWS S3 and Convert to PNG Image
	public HashMap<String, Object> fileUpload(MultipartFile file,String memberid
						,String bucketName, String terraceName
						,Long terrace_room_number) throws FileNotFoundException {
		HashMap<String, Object> map = new HashMap<>();
		int putResult = 0;
		
		try {
			File f = convertMultiPartToFile(file);
			String fileName = generateFileName();
			String original_file_name = file.getOriginalFilename();
			String saved_file_path = bucketName+"/"+"tr-user-files/"+memberid+"/"+fileName+"/"+terraceName;
			String saved_file_name = fileName+terraceName+".pdf";
			String shared_file_path = saved_file_path+"/"+fileName+terraceName+"(shared)"+".pdf";
			
			map.put("terrace_room_number", terrace_room_number);
			map.put("original_file_name", original_file_name);
			map.put("saved_file_path", saved_file_path);
			map.put("shared_file_path", shared_file_path);
			map.put("saved_file_name", saved_file_name);
			map.put("shared_file_name", fileName+terraceName+"(shared)"+".pdf");
			
			//upload original pdf to aws s3
			PutObjectResult result = S3.putObject(new PutObjectRequest(saved_file_path, saved_file_name, f));
			S3.copyObject(saved_file_path, saved_file_name, saved_file_path, fileName+terraceName+"(shared)"+".pdf");
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
				S3.putObject(new PutObjectRequest(saved_file_path+"image", imgName+".png", is, meta).withCannedAcl(CannedAccessControlList.PublicRead));
				is.close();
				os.close();
			}
			
			f.delete();
			
			if (result != null) {
				putResult = pages;
			}
			
			map.put("page", putResult);
			
	     } 
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		 return map;
    }
	
	public void uploadSharedPDF(InputStream is, TerraceRoom tr){
		try{
			ObjectMetadata meta = new ObjectMetadata();
			S3.putObject(new PutObjectRequest(tr.getSaved_file_path(), tr.getShared_file_name(), is, meta)
						.withCannedAcl(CannedAccessControlList.PublicRead));
			is.close();
		}
		catch (Exception e) {
		}
		
	}
	
	public void uploadPersonalPDF(InputStream is, TerraceRoom tr){
		try{
			ObjectMetadata meta = new ObjectMetadata();
			String fileName = generateFileName();
			S3.putObject(new PutObjectRequest(tr.getSaved_file_path(),fileName +tr.getTerrace_room_name()+ "(personal).pdf", is, meta)
						.withCannedAcl(CannedAccessControlList.PublicRead));
			is.close();
		}
		catch (Exception e) {
		}
	}
	
	private String generateFileName() {
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
