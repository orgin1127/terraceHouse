package com.SpringBoot.Demo;



import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class SpringBootDemoApplication {
	
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+"classpath:application.yml,"
			+"/app/config/terraceHouse/real-application.yml";
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringBootDemoApplication.class)
		.properties(APPLICATION_LOCATIONS)
		.run(args);
	}

}
