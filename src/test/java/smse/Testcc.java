package smse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.service.SystemService;

//@ContextConfiguration(locations = {"classpath:spring/applicationContext-Beans.xml","classpath:spring/applicationContext-DataSource.xml","classpath:spring/applicationContext-Session.xml"})
//@RunWith(SpringJUnit4ClassRunner.class) 
public class Testcc {
	
//	@Autowired
//	private SystemService systemService;
	
	@Test
	public void tt(){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		System.out.println(md5.encodePassword("root123", null));
	}

}
