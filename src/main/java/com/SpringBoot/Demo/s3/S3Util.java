package com.SpringBoot.Demo.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class S3Util {
	
	@Value("${cloud.aws.credentials.accessKey}")
	String access_key;
	
	@Value("${test.properties}")
	String testValue;
	
	@Value("${cloud.aws.credentials.secretKey}")
	String secret_key;
	
	@Value("${cloud.aws.s3.bucket}")
	String bucket;
	
	@Value("${cloud.aws.region.static}")
	String region;
	
}
