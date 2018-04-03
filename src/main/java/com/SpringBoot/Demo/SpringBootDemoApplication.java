package com.SpringBoot.Demo;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ComponentScan
@Configuration
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
	
	@Bean
    public InternalResourceViewResolver setupViewResolver() {
 
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
 
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
