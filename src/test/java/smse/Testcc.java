package smse;

import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zno.smse.service.PersonService;

@ContextConfiguration(locations = {"classpath:spring/applicationContext-Beans.xml","classpath:spring/applicationContext-DataSource.xml","classpath:spring/applicationContext-Session.xml"})
@RunWith(SpringJUnit4ClassRunner.class) 
public class Testcc {
	
	@Autowired
	private PersonService personService;
	
	@Test
	public void tt(){
		personService.getList(null, new RowBounds());
	}

}
