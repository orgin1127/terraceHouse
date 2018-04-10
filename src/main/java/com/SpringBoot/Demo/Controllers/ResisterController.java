package com.SpringBoot.Demo.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.SpringBoot.Demo.WebRestController.MailHandler;
import com.SpringBoot.Demo.WebRestController.TempKey;

@RestController
public class ResisterController {
	
	private static Logger logger = LoggerFactory.getLogger(ResisterController.class);
	@Autowired
    private JavaMailSender mailSender;
	
	
	@PostMapping("/register")
	public String registerMember() {
		logger.debug("register 컨트롤러 작동");
		
		String key = new TempKey().getKey(20, false);
		String result = "";
		try{
			
			
				result = "0";
				logger.debug("saveEntity null");
			
				logger.debug("save Entity null 아님");
				result = "1";
				MailHandler sendMail = new MailHandler(mailSender);
				sendMail.setSubject("TerraceHouse 회원 가입 메일 인증");
				sendMail.setText(new StringBuffer().append("<h1>메일인증</h1>")
						.append("<a href='http://terraceshouses.com/emailConfirm?key=")
						.append(key)
						.append("' target='_blenk'>이메일 인증 확인</a>")
						.toString());
		
				sendMail.send();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
