package com.SpringBoot.Demo;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class SpringBootDemoApplication extends SpringBootServletInitializer{
	
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+"classpath:application.yml,"
			+"/app/config/terraceHouse/real-application.yml";
	
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(SpringBootDemoApplication.class);
	}



	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringBootDemoApplication.class)
		.properties(APPLICATION_LOCATIONS)
		.run(args);
	}

}
