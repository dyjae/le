package com.jae.weixin.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration   ({"classpath:applicationContext.xml","classpath:spring-servlet.xml"}) 
public class WXGetTokenServiceTest {

	/*@Autowired
	WxGetTokenService  tokenService;

	@Test
	public void test() {
		String token = tokenService.getToken();
		System.out.println(token);
	}*/

}
