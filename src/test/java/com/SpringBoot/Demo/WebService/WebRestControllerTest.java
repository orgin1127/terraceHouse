package com.SpringBoot.Demo.WebService;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WebRestControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplet;
	
	@Test
	public void ProfileCheck() {
		/*//when
		String profile = this.restTemplet.getForObject("/profile", String.class);
		//then
		assertThat(profile).isEqualTo("local");*/
	}
}
