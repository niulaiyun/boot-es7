/*package com.nly;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.nly.pojo.Employee;
import com.nly.service.ITestService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsApplicationTest {
	  @Autowired
	  ITestService testService;
	
	  @Test
	  public  void contextLoads() {
		  Employee em=new Employee();
		  em.setAge("17");
		  em.setFirstName("xxx");
		  em= testService.save(em);
		  System.out.println(JSON.toJSONString(em));
	    }
}
*/